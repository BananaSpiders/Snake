import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;


public class FenetreSnake extends JFrame implements KeyListener{

	public static final int HAUTEUR_FENETRE = 600;
	public static final int LARGEUR_FENETRE = 800;
	public static final int LARGEUR_CASE = 20;
	
	public int nbCasesL;
	public int nbCasesH;
	
	public RenderingThread renderingThread;
	public Graphics buffer;
	public BufferStrategy strategy;
	
	private Map map;
	private Snake snake;
	
	
	public FenetreSnake(){
		this.nbCasesH = HAUTEUR_FENETRE/LARGEUR_CASE;
		this.nbCasesL = LARGEUR_FENETRE/LARGEUR_CASE;
		
		this.map = new Map(nbCasesH, nbCasesL);
		this.snake = new Snake(this);
		
		//Fenetre
		this.setTitle("Snake-Like");
		this.setSize(800,600);
		setResizable(false); // On empeche le redimensionnement de la fenetre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On quitte l'application lorsque l'on clique sur la croix
		this.setIgnoreRepaint(true);
		this.setVisible(true);
		
		//Contexte d'affichage
		this.createBufferStrategy(2);
		this.strategy = getBufferStrategy();
		this.buffer = strategy.getDrawGraphics();
		this.addKeyListener(this);
		
		//SNAKE
		for(int i = 0;i<5;i++)
			this.snake.getBody()[i] = new SnakePart(this.map.getLesCases()[i+10][10]);
		
		//Rendering Thread
		this.renderingThread = new RenderingThread(this);
		renderingThread.start();
		

		
	}
	
	public void render(){
		this.buffer.clearRect(0	, 0, 	800, 600);
		
		this.buffer.setColor(Color.BLACK);
		
		this.drawMap();
		
		this.buffer.setColor(Color.RED);
		
		
		this.drawSnake();
		
		
		this.strategy.show();
		
		this.snake.move();
	}
	
	public void drawMap(){
		for(int j = 0;j<nbCasesH;j++)
			for(int i = 0;i<nbCasesL;i++){
				buffer.drawRect(map.getLesCases()[i][j].getX(), map.getLesCases()[i][j].getY(), map.getLesCases()[i][j].getX()+FenetreSnake.LARGEUR_CASE, map.getLesCases()[i][j].getY()+FenetreSnake.LARGEUR_CASE);
			}
	}
	
	public void drawSnake(){
		
		int nb = 0;
		
		nb = this.snake.getBody()[0].getActualCase().getX();
		
		for(int i = 0;i<this.snake.getSnakeLength();i++){
			buffer.fillOval(this.snake.getBody()[i].getActualCase().getX(), this.snake.getBody()[i].getActualCase().getY(), FenetreSnake.LARGEUR_CASE, FenetreSnake.LARGEUR_CASE);
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getNbCasesL() {
		return nbCasesL;
	}

	public void setNbCasesL(int nbCasesL) {
		this.nbCasesL = nbCasesL;
	}

	public int getNbCasesH() {
		return nbCasesH;
	}

	public void setNbCasesH(int nbCasesH) {
		this.nbCasesH = nbCasesH;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	
}
