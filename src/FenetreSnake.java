import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class FenetreSnake extends JFrame implements KeyListener{

	public static final int HAUTEUR_FENETRE = 700;
	public static final int LARGEUR_FENETRE = 800;
	public static int OFFSET_TITLEBAR =28;
	public static final int SNAKE_CENTER_Y = Map.NB_CASE_H_SHOW/2;
	
	public RenderingThread renderingThread;
	public UpdateThread updateThread;
	public Graphics buffer;
	public BufferStrategy strategy;
	
	private Map map;
	private Snake snake;
	
	
	public FenetreSnake(){
		
		this.map = new Map();
		this.snake = new Snake(this);
		
		//Fenetre
		this.setTitle("Snake-Like");
		this.setSize(FenetreSnake.LARGEUR_FENETRE,FenetreSnake.HAUTEUR_FENETRE);
		setResizable(false); // On empeche le redimensionnement de la fenetre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On quitte l'application lorsque l'on clique sur la croix
		this.setIgnoreRepaint(true);
		this.setVisible(true);
		
		//Contexte d'affichage
		this.createBufferStrategy(2);
		this.strategy = getBufferStrategy();
		this.buffer = strategy.getDrawGraphics();
		this.addKeyListener(this);
		
		
		//RenderingThread
		this.renderingThread = new RenderingThread(this);
		renderingThread.start();
		
		//UpdateThread
		this.updateThread = new UpdateThread(this);
		this.updateThread.start();
		
	}
	
	public void render(){
		
		this.buffer.clearRect(0	, 0, 	FenetreSnake.LARGEUR_FENETRE, FenetreSnake.HAUTEUR_FENETRE);
		this.buffer.setColor(Color.BLACK);
		
		this.drawMap();
		
		this.buffer.setColor(Color.ORANGE);
		
		this.drawSnake();
		
		this.buffer.setColor(Color.GREEN);
		
		this.drawObjets();
		this.strategy.show();
	}
	
	public void update(){
		this.snake.move();
	}
	
	////// DRAW OBJETS
	public void drawObjets(){
		int x,y,width;
		
		width=Case.LARGEUR_CASE;
		
		// DRAW OBJET
		for(int j = this.map.getStartY();j<this.map.getEndY() ; j++)
			for(int i = 0;i<Map.getNbCaseL() ; i++){
				x = this.getMap().getLesCases()[i][j].getX();
				y = this.getMap().getLesCases()[i][j].getY();
				//si la case a un objet on l'affiche sur une taille de 9 cases (a modifier) mdr
				if(this.getMap().getLesCases()[i][j].getObjet() != null)
					this.buffer.drawImage(this.getMap().getLesCases()[i][j].getObjet(), x, y, Case.LARGEUR_CASE*3, Case.LARGEUR_CASE*3, null);
			}
	}
	
	// DRAW MAP
	public void drawMap(){
		int x,y,width;
		
		width=Case.LARGEUR_CASE;
		
		//DRAW SOL
		for(int j = this.map.getStartY();j<this.map.getEndY() ; j++)
			for(int i = 0;i<Map.getNbCaseL() ; i++){		
				x = this.getMap().getLesCases()[i][j].getX();
				y = this.getMap().getLesCases()[i][j].getY();
				
				this.buffer.drawImage(this.getMap().getLesCases()[i][j].getSol(), x, y, Case.LARGEUR_CASE, Case.LARGEUR_CASE, null);
			}		
	}
	
	public void drawSnake(){
		
		// draw tete
		this.buffer.drawImage(this.snake.getBody()[0].getImgTete()[1], this.snake.getBody()[0].getActualCase().getX(), this.snake.getBody()[0].getActualCase().getY(), Case.LARGEUR_CASE, Case.LARGEUR_CASE, null);
		// draw corps
		for(int i = 1;i<this.snake.getSnakeLength();i++){
			this.buffer.drawImage(this.snake.getBody()[i].getImgBody()[1], this.snake.getBody()[i].getActualCase().getX(), this.snake.getBody()[i].getActualCase().getY(), Case.LARGEUR_CASE, Case.LARGEUR_CASE, null);
		}
	}
	
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
					this.getMap().getLesCases()[i][j].setY(Case.LARGEUR_CASE*j + FenetreSnake.OFFSET_TITLEBAR - this.map.getStartY()*Case.LARGEUR_CASE);
				
				}
		}
		
		
	}
	
	public static void main(String[] args){
		FenetreSnake f = new FenetreSnake();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			this.snake.wayUpdate("Left");
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			this.snake.wayUpdate("Right");
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			this.snake.wayUpdate("Up");
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			this.snake.wayUpdate("Down");
		}		
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			JOptionPane.showMessageDialog(this, this.snake.getBody()[0].getActualCase().getX()+" "+this.snake.getBody()[0].getActualCase().getY());
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	
}
