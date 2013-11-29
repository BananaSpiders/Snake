package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controler.Main;
import Controler.UpdateThread;
import Model.Map;

/*
 *  Class qui contient la fenetre principale du jeu
 */
public class FrameSnake extends JFrame implements KeyListener,ActionListener{	
	/*
	 *  ATTRIBUTS
	 */
	private Main owner;
	public static final int FRAME_WIDTH = 400;
	public static final int FRAME_HEIGHT = 640;
	public static final int FRAME_HEIGHT_TITLEBAR = 28;
	public static final int FRAME_MARGE_RIGHT = 40;
	public static final int FRAME_MARGE_LEFT = 40;
	public static final int FRAME_MARGE_TOP = 40;
	public static final int FRAME_MARGE_BOTTOM = 40;
	
	// Les marges de notre fenetres que lon pourra editer
    private JPanel panel_marge_top;
    private JPanel panel_marge_bottom;
    private JPanel panel_marge_left;
    private JPanel panel_marge_right;
    
	private Map map;
	private JButton butMenu;
	/*
	 *  CONSTRUCTEUR
	 */
	public FrameSnake(Main owner){
		super("Snake de la mort");
		this.addKeyListener(this);
		this.owner = owner;
		this.setResizable(false);
	}

	/*
	 *  EVENTS
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==81){
			this.owner.getSnake().wayUpdate("Left");
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==68){
			this.owner.getSnake().wayUpdate("Right");
		}
		if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==90){
			this.owner.getSnake().wayUpdate("Up");
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==83){
			this.owner.getSnake().wayUpdate("Down");
		}		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.butMenu){
			FrameMenu frame = new FrameMenu();
			frame.setVisible(true);
			
			this.owner.updateThread.stopRunning();
			this.owner.stopRunning();
			this.dispose();
		}				
	}
	
	////////////////
	// METHODES
	/////////////
	/**
	 * Initialise nos marges
	 * 
	 */
	public void initMarges(Map map){
		this.map = map;
		
		this.panel_marge_bottom = new JPanel();
		this.panel_marge_top = new JPanel();
		this.panel_marge_left = new JPanel();
		this.panel_marge_right = new JPanel();
		
		
		
		this.panel_marge_bottom.setBounds(0, FrameSnake.FRAME_HEIGHT+FrameSnake.FRAME_MARGE_TOP, FrameSnake.GET_TOTAL_WIDTH() , FrameSnake.FRAME_MARGE_BOTTOM);
		this.panel_marge_top.setBounds(0,0, FrameSnake.GET_TOTAL_WIDTH() , FrameSnake.FRAME_MARGE_TOP);
		this.panel_marge_left.setBounds(0,FrameSnake.FRAME_MARGE_TOP, FrameSnake.FRAME_MARGE_LEFT , FrameSnake.FRAME_HEIGHT);
		this.panel_marge_right.setBounds(FrameSnake.GET_TOTAL_WIDTH() - FrameSnake.FRAME_MARGE_RIGHT,FrameSnake.FRAME_MARGE_TOP, FrameSnake.FRAME_MARGE_RIGHT , FrameSnake.FRAME_HEIGHT);
		
		this.initBottom();
		this.initTop();
		this.initLeft();
		this.initRight();
		
		this.add(this.panel_marge_top);
		this.add(this.panel_marge_bottom);
		this.add(this.panel_marge_left);
		this.add(this.panel_marge_right);
	}

	/**
	 *  Initialisation des marges
	 * 
	 */
	// BOTTOM
	public void initBottom(){
		this.panel_marge_bottom.setBackground(Color.BLACK);
	}
	// TOP
	public void initTop(){
		this.panel_marge_top.setBackground(Color.BLACK);
		this.panel_marge_top.setLayout(null);
		
		int wMenu = 30;
		this.butMenu = new JButton();
		this.butMenu.setFocusable(false);
		butMenu.setIcon(new ImageIcon(this.map.getMesImg().get("butMenu")));
		butMenu.setPreferredSize(new Dimension(wMenu,wMenu));
		this.butMenu.addActionListener(this);
		 
		butMenu.setBounds(FrameSnake.GET_TOTAL_WIDTH() - wMenu - 5, 5, wMenu, wMenu);
	
		this.panel_marge_top.add(butMenu);
	}
	// LEFT
	public void initLeft(){
		//this.panel_marge_left.setBackground(Color.BLACK);
		
		this.panel_marge_left.setLayout(null);
		JLabel jl = new JLabel("");
		jl.setIcon(new ImageIcon(this.map.getMesImg().get("margeCoteGauche")));
		jl.setPreferredSize(new Dimension(FrameSnake.FRAME_MARGE_LEFT,FrameSnake.FRAME_HEIGHT));
		jl.setBounds(0,0,FrameSnake.FRAME_MARGE_LEFT,FrameSnake.FRAME_HEIGHT);
		
		this.panel_marge_left.add(jl);
	}
	// RIGHT
	public void initRight(){
		//this.panel_marge_right.setBackground(Color.WHITE);
		
		this.panel_marge_right.setLayout(null);
		JLabel jl = new JLabel("");
		jl.setIcon(new ImageIcon(this.map.getMesImg().get("margeCoteDroite")));
		jl.setPreferredSize(new Dimension(FrameSnake.FRAME_MARGE_RIGHT,FrameSnake.FRAME_HEIGHT));
		jl.setBounds(0,0,FrameSnake.FRAME_MARGE_RIGHT,FrameSnake.FRAME_HEIGHT);
		
		this.panel_marge_right.add(jl);
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static int GET_TOTAL_WIDTH(){
		return FrameSnake.FRAME_MARGE_LEFT + FrameSnake.FRAME_WIDTH + FrameSnake.FRAME_MARGE_RIGHT;
	}
	public static int GET_TOTAL_HEIGHT(){
		return FrameSnake.FRAME_MARGE_TOP + FrameSnake.FRAME_HEIGHT + FrameSnake.FRAME_MARGE_BOTTOM;
	}



	public void setOwner(Main owner) {
		this.owner = owner;
	}

	public JPanel getPanel_marge_top() {
		return panel_marge_top;
	}

	public void setPanel_marge_top(JPanel panel_marge_top) {
		this.panel_marge_top = panel_marge_top;
	}

	public JPanel getPanel_marge_bottom() {
		return panel_marge_bottom;
	}

	public void setPanel_marge_bottom(JPanel panel_marge_bottom) {
		this.panel_marge_bottom = panel_marge_bottom;
	}

	public JPanel getPanel_marge_left() {
		return panel_marge_left;
	}

	public void setPanel_marge_left(JPanel panel_marge_left) {
		this.panel_marge_left = panel_marge_left;
	}

	public JPanel getPanel_marge_right() {
		return panel_marge_right;
	}

	public void setPanel_marge_right(JPanel panel_marge_right) {
		this.panel_marge_right = panel_marge_right;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public JButton getButMenu() {
		return butMenu;
	}

	public void setButMenu(JButton butMenu) {
		this.butMenu = butMenu;
	}

	public static int getFrameWidth() {
		return FRAME_WIDTH;
	}

	public static int getFrameHeight() {
		return FRAME_HEIGHT;
	}

	public static int getFrameHeightTitlebar() {
		return FRAME_HEIGHT_TITLEBAR;
	}

	public static int getFrameMargeRight() {
		return FRAME_MARGE_RIGHT;
	}

	public static int getFrameMargeLeft() {
		return FRAME_MARGE_LEFT;
	}

	public static int getFrameMargeTop() {
		return FRAME_MARGE_TOP;
	}

	public static int getFrameMargeBottom() {
		return FRAME_MARGE_BOTTOM;
	}

	
}
