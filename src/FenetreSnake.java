import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class FenetreSnake extends JFrame implements KeyListener{

	public static final int HAUTEUR_FENETRE = 700;
	public static final int LARGEUR_FENETRE = 400;
	public static int OFFSET_TITLEBAR =28;
	public static final int SNAKE_CENTER_Y = 15;
	
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
		
		this.buffer.setColor(Color.RED);
		
		this.drawSnake();
		
		this.buffer.setColor(Color.GREEN);
		
		
		this.strategy.show();
	}
	
	public void update(){
		this.snake.move();
	}
	
	public void drawMap(){
		
		int x,y,width;
		
		width=Case.LARGEUR_CASE;
		
		for(int j = this.map.getStartY();j<this.map.getEndY() ; j++)
			for(int i = 0;i<Map.getNbCaseL() ; i++){
				
				x = this.getMap().getLesCases()[i][j].getX();
				y = this.getMap().getLesCases()[i][j].getY();
				
				if(j%2 == 0)
					this.buffer.setColor(Color.RED);
				else
					this.buffer.setColor(Color.BLUE);
				
				
				this.buffer.drawRect(x, y, width, width);
			}
	}
	
	public void drawSnake(){
		
		for(int i = 0;i<this.snake.getSnakeLength();i++){
			buffer.fillOval(this.snake.getBody()[i].getActualCase().getX(), this.snake.getBody()[i].getActualCase().getY(), Case.LARGEUR_CASE, Case.LARGEUR_CASE);
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
			System.out.println(""+updateCasePositions);
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
