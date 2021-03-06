package Controler;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Model.Bonus;
import Model.Case;
import Model.Map;
import Model.Objet;
import Model.Snake;
import Parser.ParserSax;
import View.FrameMenu;
import View.FrameSnake;

/**
 *  Class principale du projet
 */
public class Main extends Thread {
	
	/**
	 *  ATTRIBUTS
	 */
    // Static
    public static final int SNAKE_CENTER_Y = Map.NB_CASE_H_SHOW/2;
    
    // Contexte
    private Canvas canvas;
    private BufferStrategy strategy;
    private BufferedImage background;
    private Graphics2D backgroundGraphics;
    private Graphics2D graphics;
    private GraphicsConfiguration config =
    		GraphicsEnvironment.getLocalGraphicsEnvironment()
    			.getDefaultScreenDevice()
    			.getDefaultConfiguration();
    
    
    // TIMER
    private Timer timerChrono;
    // Models
    private Map map;
	private Snake snake;
	private FrameSnake frame;
	
	// Controlers
	public UpdateThread updateThread;
	
	// Variables
	private int scale = 1;
    private boolean isRunning = true;
    private String mapPlay;
    private int nbBonbonRouge;
    private int nbBonbonRougeAttrape;
    private int tempsChrono;
    
	
    /**
     *  CONSTRUCTEUR, initialisations
     */
    public Main(String mapToLoad) {
    	// init
    	this.nbBonbonRouge = 0; // le parser s'occupera de donner le bon nombre 
    	this.nbBonbonRougeAttrape = 0;
    	this.tempsChrono = 45;
    	this.mapPlay = mapToLoad;
    	
    	//On charge la map (en utilisant le parser)
    	this.loadMap(mapPlay);
    	
    	System.out.println("Nombre de bonbons rouges : "+this.nbBonbonRouge);
    	
		this.snake = new Snake(this);
		
		//UpdateThread
		this.updateThread = new UpdateThread(this);
		this.updateThread.start();		
		
    	// JFrame
    	frame = new FrameSnake(this);
    	frame.addWindowListener(new FrameClose());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().setPreferredSize(new Dimension( FrameSnake.GET_TOTAL_WIDTH() , FrameSnake.GET_TOTAL_HEIGHT() ));
    	frame.pack();
    	frame.setVisible(true);
    	frame.setLayout(null);

    	
    	// Canvas
    	canvas = new Canvas(config);
    	canvas.setSize((FrameSnake.FRAME_WIDTH), (FrameSnake.FRAME_HEIGHT) );
    	canvas.setBounds(FrameSnake.FRAME_MARGE_LEFT,FrameSnake.FRAME_MARGE_TOP,FrameSnake.FRAME_WIDTH,FrameSnake.FRAME_HEIGHT);
    	frame.add(canvas, 0);
    	
    	// Marges Panels
    	this.frame.initMarges(this.map);
    	this.refreshNbBonusAttrape();
    	

    	// Background & Buffer
    	background = create(FrameSnake.FRAME_WIDTH, FrameSnake.FRAME_HEIGHT, false);
    	canvas.createBufferStrategy(2);
    	do {
    		strategy = canvas.getBufferStrategy();
    	} while (strategy == null);
    	
    	
    	this.frame.startChrono();
    	start();
    	
    	
    	
    }

    /**
     *  METHODES
     */
    /**
     *  Principal Method
     */
    public void run() {
    	backgroundGraphics = (Graphics2D) background.getGraphics();
    	long fpsWait = (long) (1.0 / 30 * 1000);
    	main: while (isRunning) {
    		long renderStart = System.nanoTime();
    		//updateGame();

    		// Update Graphics
    		do {
    			Graphics2D bg = getBuffer();
    			if (!isRunning) {
    				break main;
    			}
    			renderGame(backgroundGraphics); // this calls your draw method
    			// thingy
    			if (scale != 1) {
    				bg.drawImage(background, 0, 0, FrameSnake.FRAME_WIDTH * scale, FrameSnake.FRAME_HEIGHT
    						* scale, 0, 0, FrameSnake.FRAME_WIDTH, FrameSnake.FRAME_HEIGHT, null);
    			} else {
    				bg.drawImage(background, 0, 0, null);
    			}
    			bg.dispose();
    		} while (!updateScreen());

    		// Better do some FPS limiting here
    		long renderTime = (System.nanoTime() - renderStart) / 1000000;
    		try {
    			Thread.sleep(Math.max(0, fpsWait - renderTime));
    		} catch (InterruptedException e) {
    			Thread.interrupted();
    			break;
    		}
    		renderTime = (System.nanoTime() - renderStart) / 1000000;

    	}

    	frame.dispose();
    }
    
    /**
     *  Graphic Method
     */
    
    public void refreshNbBonusAttrape(){
    	this.frame.getLabBonbonRouge().setText(this.nbBonbonRougeAttrape+"/"+this.getNbBonbonRouge()+" bonus");
    }
    
    // Ferme la fenetre
    private class FrameClose extends WindowAdapter {
    	@Override
    	public void windowClosing(final WindowEvent e) {
    		isRunning = false;
    	}
    }
    public void stopRunning(){
    	this.isRunning = false;
    }
    
    // create a hardware accelerated image
    public final BufferedImage create(final int width, final int height,
    		final boolean alpha) {
    	return config.createCompatibleImage(FrameSnake.FRAME_WIDTH, FrameSnake.FRAME_HEIGHT, alpha
    			? Transparency.TRANSLUCENT : Transparency.OPAQUE);
    }

    // Screen and buffer stuff
    private Graphics2D getBuffer() {
    	if (graphics == null) {
    		try {
    			graphics = (Graphics2D) strategy.getDrawGraphics();
    		} catch (IllegalStateException e) {
    			return null;
    		}
    	}
    	return graphics;
    }
    // Met a jour l'ecran
    private boolean updateScreen() {
    	graphics.dispose();
    	graphics = null;
    	try {
    		strategy.show();
    		Toolkit.getDefaultToolkit().sync();
    		return (!strategy.contentsLost());

    	} catch (NullPointerException e) {
    		return true;

    	} catch (IllegalStateException e) {
    		return true;
    	}
    }

    

    /**
     *  LOGIQUE DU JEU
     */
    public void updateGame() {
    	//on bouge si on ne fonce pas dans un objet bloquant
    	// on recupere la case qui arrive
    	Case c = this.snake.getNextCase();
    	// on recupere l'objet de cette case
    	Objet obj = c.getObjet();
    	
    	// si il n'est pas null on regarde si il est bloquant, si il est null, on peut bouger car pas d'objet
    	if(obj != null){
    		
    		
    		// si c'est un bonus
    		if(obj instanceof Bonus){
    			((Bonus)obj).touche(this.map,this.snake,this);
    		}else{
    			// c'est un objet, si il est bloquant
	    		if(!obj.isBloque()){
	    			this.snake.move();
	    			
	    		}else{
	    			// BLOQUE -> MEURT !!!!!
	    			this.retourMenu();
	    		}
    		}
    		
    	}
    	else{
    		this.snake.move();
    	}
    }
    
    /*
     *  END
     */
    public void end(){
    	
    	// on stop le chrono
    	this.getFrame().getTimeTaskChrono().cancel();
	
    	// on cache le canvas
    	this.canvas.setVisible(false);
    	
    	
    	//// on affiche a la place le panel "END" /////
    	this.getFrame().showEndPanel();
    	
    	// on dit quil a gg (ca fait une pause :)
		//JOptionPane.showMessageDialog(this.getFrame(), "gg", "gagnee ! On passe au niveau suivant !!", JOptionPane.INFORMATION_MESSAGE);
		
		
    }
    
    /*
     *  Switch a la carte suivante si cest posssible
     */
    public void nextMap(){
    	// on recupere le numero de la map actuelle
    			Pattern p = Pattern.compile("(.)*_([0-9])*");
    			Matcher mFile = p.matcher(this.getMapPlay());
    			
    			int numerroMap = 0;
    			try{
    				numerroMap = Integer.parseInt(mFile.replaceAll("$2"));
    			}catch(Exception e){
    				this.retourMenu();
    			}
    			
    			System.out.println("=>>>>"+numerroMap);
    			
    			// on regarde si il reste des maps
    			String[] mapRestantes = FrameMenu.selectMapsDisponibles();
    			if(numerroMap<mapRestantes.length){
    				Main m = new Main("level_"+(numerroMap+1));
    				m.getFrame().setVisible(false);
    				m.getFrame().setVisible(true);
    				this.setRunning(false);
    				
    			}else{
    				JOptionPane.showMessageDialog(this.getFrame(), "BRAVO !", "Vous avez fini tous les niveaux !  !!", JOptionPane.INFORMATION_MESSAGE);
    				this.retourMenu();
    			}
    }
    
    
    /*
     *  RETOURNE AU MENU
     */
    public void retourMenu(){
    	this.isRunning = false;
		this.frame.getButMenu().doClick();
    }

    /**
     *  DESSINS
     */
    public void renderGame(Graphics2D g) {
    	/*g.setColor(Color.BLACK);
    	g.fillRect(0, 0, FrameSnake.FRAME_WIDTH , FrameSnake.FRAME_HEIGHT);*/

		this.drawMap(g);
		
		g.setColor(Color.ORANGE);
		
		this.drawSnake(g);
		
		this.drawObjets(g);
		
		this.strategy.show();
    }
    
    /**
     *  DRAW METHOD
     */
	////// DRAW OBJETS
	public void drawObjets(Graphics2D g){
		int x,y,width;
		
		width=Case.LARGEUR_CASE;
		
		// DRAW OBJET
		for(int j = this.map.getStartY();j<this.map.getEndY() ; j++)
			for(int i = 0;i<Map.getNbCaseL() ; i++){
				x = this.getMap().getLesCases()[i][j].getX();
				y = this.getMap().getLesCases()[i][j].getY();
				//si la case a un objet on l'affiche sur une taille de 9 cases (a modifier) mdr
				if(this.getMap().getLesCases()[i][j].getObjet() != null)
					g.drawImage(this.getMap().getLesCases()[i][j].getObjet().getImage(), x, y, Case.LARGEUR_CASE*this.getMap().getLesCases()[i][j].getObjet().getNbCaseL(), Case.LARGEUR_CASE*this.getMap().getLesCases()[i][j].getObjet().getNbCaseH(), null);
			}
	}
	
	
	
	// DRAW MAP
	public void drawMap(Graphics2D g){
		int x,y,width;
		
		width=Case.LARGEUR_CASE;
		
		//DRAW SOL
		for(int j = this.map.getStartY();j<this.map.getEndY() ; j++)
			for(int i = 0;i<Map.getNbCaseL() ; i++){		
				x = this.getMap().getLesCases()[i][j].getX();
				y = this.getMap().getLesCases()[i][j].getY();
				
				g.drawImage(this.getMap().getLesCases()[i][j].getSol(), x, y, Case.LARGEUR_CASE, Case.LARGEUR_CASE, null);
			}		
	}
	
	
	
	// DRAW SNAKE
	public void drawSnake(Graphics2D g){
		
		// draw tete
		g.drawImage(this.snake.getBody()[0].getImgTete()[1], this.snake.getBody()[0].getActualCase().getX(), this.snake.getBody()[0].getActualCase().getY(), Case.LARGEUR_CASE, Case.LARGEUR_CASE, null);
		// draw corps
		for(int i = 1;i<this.snake.getSnakeLength();i++){
			g.drawImage(this.snake.getBody()[i].getImgBody()[1], this.snake.getBody()[i].getActualCase().getX(), this.snake.getBody()[i].getActualCase().getY(), Case.LARGEUR_CASE, Case.LARGEUR_CASE, null);
		}
	}
	
	/**
	 *  MAJ VIEW METHOD
	 */
	public void updateView(String way){
		
		boolean updateCasePositions = false;
		
		if(way.equals("Up")){
			if(this.getMap().getStartY()>0){
				
				updateCasePositions = true;
				this.map.setStartY(this.map.getStartY()-1);
				this.map.setEndY(this.map.getEndY()-1);
			}
			
		}
		else if(way.equals("Down")){
			if(this.getMap().getEndY()<Map.getNbCaseH()){
				updateCasePositions = true;
				this.map.setStartY(this.map.getStartY()+1);
				this.map.setEndY(this.map.getEndY()+1);
			}
			
		}
		
		if(updateCasePositions){
			for(int j = this.map.getStartY();j<this.map.getEndY() ; j++)
				for(int i = 0;i<Map.getNbCaseL() ; i++){
					this.getMap().getLesCases()[i][j].setX(Case.LARGEUR_CASE*i);
					this.getMap().getLesCases()[i][j].setY(Case.LARGEUR_CASE*j -  this.map.getStartY()*Case.LARGEUR_CASE);
				
				}
		}
		
		
	}
	
	/**
	 *  Load la map
	 */
	public void loadMap(String nomMap){
		ParserSax ps = new ParserSax(""+nomMap,this);
		
		if(ps.isMyMapReady()){
			System.out.println("La map : "+nomMap+" a bien ete chargee.");
			this.map = ps.myMap;
		}
		else
			System.out.println("Erreur lors du chargement de la map : "+ps.myMap);
	}
	
	
	

    /**
     *  GETTER ET SETTER
     */
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public BufferStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(BufferStrategy strategy) {
		this.strategy = strategy;
	}

	public BufferedImage getBackground() {
		return background;
	}

	public void setBackground(BufferedImage background) {
		this.background = background;
	}

	public Graphics2D getBackgroundGraphics() {
		return backgroundGraphics;
	}

	public void setBackgroundGraphics(Graphics2D backgroundGraphics) {
		this.backgroundGraphics = backgroundGraphics;
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	public FrameSnake getFrame() {
		return frame;
	}

	public void setFrame(FrameSnake frame) {
		this.frame = frame;
	}


	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public GraphicsConfiguration getConfig() {
		return config;
	}

	public void setConfig(GraphicsConfiguration config) {
		this.config = config;
	}


	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public static int getSnakeCenterY() {
		return SNAKE_CENTER_Y;
	}

	public UpdateThread getUpdateThread() {
		return updateThread;
	}

	public void setUpdateThread(UpdateThread updateThread) {
		this.updateThread = updateThread;
	}

	public String getMapPlay() {
		return mapPlay;
	}

	public void setMapPlay(String mapPlay) {
		this.mapPlay = mapPlay;
	}

	public Timer getTimerChrono() {
		return timerChrono;
	}

	public void setTimerChrono(Timer timerChrono) {
		this.timerChrono = timerChrono;
	}

	public int getNbBonbonRouge() {
		return nbBonbonRouge;
	}

	public void setNbBonbonRouge(int nbBonbonRouge) {
		this.nbBonbonRouge = nbBonbonRouge;
	}

	public int getNbBonbonRougeAttrape() {
		return nbBonbonRougeAttrape;
	}

	public void setNbBonbonRougeAttrape(int nbBonbonRougeAttrape) {
		this.nbBonbonRougeAttrape = nbBonbonRougeAttrape;
	}

	public int getTempsChrono() {
		return tempsChrono;
	}

	public void setTempsChrono(int tempsChrono) {
		this.tempsChrono = tempsChrono;
	}
}