package View;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Controler.Main;

/*
 *  Class qui contient la fenetre principale du jeu
 */
public class FrameSnake extends JFrame implements KeyListener{

	/*
	 *  ATTRIBUTS
	 */
	private Main owner;
	public static final int FRAME_WIDTH = 400;
	public static final int FRAME_HEIGHT = 400;
	public static final int FRAME_HEIGHT_TITLEBAR = 28;
	
	/*
	 *  CONSTRUCTEUR
	 */
	public FrameSnake(Main owner){
		super("Snake de la mort");
		this.addKeyListener(this);
		this.owner = owner;
	}

	/*
	 *  EVENTS
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			this.owner.getSnake().wayUpdate("Left");
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			this.owner.getSnake().wayUpdate("Right");
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			this.owner.getSnake().wayUpdate("Up");
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			this.owner.getSnake().wayUpdate("Down");
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
}
