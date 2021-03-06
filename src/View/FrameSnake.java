package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
    private JPanel panel_end;
    
	private Map map;
	private JButton butMenu;
	private JButton butEndSuivant;
	private JButton butEndMenu;
	
	private JLabel labChrono;
	private JLabel labBonbonRouge;
	
	
	
	// Timer
	private TimerChrono timeTaskChrono;
	
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
		
		if(e.getSource() == this.butMenu || e.getSource() == this.butEndMenu){
			FrameMenu frame = new FrameMenu();
			frame.setVisible(true);
			
			this.owner.updateThread.stopRunning();
			this.owner.stopRunning();
			this.dispose();
		}
		
		if(e.getSource() == this.butEndSuivant){
			// on passe la map suivante
			this.owner.nextMap();
			
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
		this.panel_marge_bottom.setLayout(null);
		JLabel jbg = new JLabel(new ImageIcon(this.owner.getMap().getMesImg().get("margeCoteBas")));
		jbg.setBounds(0,0,this.panel_marge_bottom.getWidth(),this.panel_marge_bottom.getHeight());
		this.panel_marge_bottom.add(jbg);
	}
	// TOP
	public void initTop(){
		this.panel_marge_top.setBackground(new Color(250,220,50));
		this.panel_marge_top.setLayout(null);
		
		int wMenu = 30;
		this.butMenu = new JButton();
		this.butMenu.setFocusable(false);
		butMenu.setIcon(new ImageIcon(this.map.getMesImg().get("butMenu")));
		butMenu.setPreferredSize(new Dimension(wMenu,wMenu));
		this.butMenu.addActionListener(this);
		 
		butMenu.setBounds(FrameSnake.GET_TOTAL_WIDTH() - wMenu - 5, 5, wMenu, wMenu);
		
		
		this.labChrono = new JLabel();
		this.labChrono.setForeground(Color.WHITE);
		this.labChrono.setFocusable(false);
		this.labChrono.setBounds(300, 0, 200, 50);
		
		this.labBonbonRouge = new JLabel();
		this.labBonbonRouge.setForeground(Color.WHITE);
		this.labBonbonRouge.setFocusable(false);
		this.labBonbonRouge.setBounds(150, 0, 100, 50);
		
		JLabel jbg = new JLabel(new ImageIcon(this.owner.getMap().getMesImg().get("margeCoteHaut")));
		jbg.setBounds(0,0,this.panel_marge_top.getWidth(),this.panel_marge_top.getHeight());
		
	
		this.panel_marge_top.add(butMenu);
		this.panel_marge_top.add(this.labChrono);
		this.panel_marge_top.add(this.labBonbonRouge);
		this.panel_marge_top.add(jbg);
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
	
	public void startChrono(){
		//Timer Chronometre
		Timer timerChrono = new Timer();
		this.timeTaskChrono = new TimerChrono(this.labChrono);
		timerChrono.scheduleAtFixedRate(this.timeTaskChrono, 0, 1000);
	}
	
	
	
	/** 
	 * TIMER
	 */
	/**
	 * Timer Chronometre
	 */
	public class TimerChrono extends TimerTask{
		
		private JLabel jlab;
		public int seconde;
		public int minute;
		
		public TimerChrono(JLabel jlab){
			this.seconde = 0;
			this.minute = 0;
			this.jlab = jlab;
		}
		
		public void run(){
			this.jlab.setText(this.minute+"min(s) "+this.seconde+"sec(s)");
			this.seconde ++;
			if(this.seconde>=60){
				this.seconde = 0;
				this.minute++;
			}
		}
	}
	
	/*
	 *  Affiche le panel End
	 */
	public void showEndPanel(){
		// on enleve les marges
		this.panel_marge_bottom.setVisible(false);
		this.panel_marge_left.setVisible(false);
		this.panel_marge_right.setVisible(false);
		this.panel_marge_top.setVisible(false);
		
		// Definition du JPanel
		this.panel_end = new JPanel();
		this.panel_end.setBounds(0, 0, FrameSnake.GET_TOTAL_WIDTH(), FrameSnake.GET_TOTAL_HEIGHT());
		this.panel_end.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(this.panel_end);
		this.panel_end.setLayout(null);
		
		
		// labels
		JLabel lblBravoVous = new JLabel("F\u00E9licitation !");
		lblBravoVous.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblBravoVous.setBounds(153, 115, 212, 92);
		this.panel_end.add(lblBravoVous);
		
		JLabel lblVousAvezFini = new JLabel("Vous avez fini le niveau !");
		lblVousAvezFini.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVousAvezFini.setBounds(136, 218, 194, 47);
		this.panel_end.add(lblVousAvezFini);
		
		
		// boutons
		this.butEndMenu = new JButton("");
		this.butEndMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		this.butEndMenu.setIcon(new ImageIcon(this.owner.getMap().getMesImg().get("butPanelEndMenu")));
		this.butEndMenu.setBounds(62, 453, 140, 50);
		this.butEndMenu.addActionListener(this);
		this.panel_end.add(this.butEndMenu);
		
		this.butEndSuivant = new JButton("");
		this.butEndSuivant.setIcon(new ImageIcon(this.owner.getMap().getMesImg().get("butPanelEndSuivant")));
		this.butEndSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		this.butEndSuivant.setBounds(268, 453, 140, 50);
		this.butEndSuivant.addActionListener(this);
		this.panel_end.add(this.butEndSuivant);
		
		
		// ETOILES
		// on initialise nos label qui feront office d'etoile ( 1er etoile = partie reussi / 2ieme etoile = tout les bonus rouge recuperer / 3ieme = reussi dans le laps de temps cf this.tempsChrono)
    	JLabel[] labEtoile = new JLabel[3];
    	// on recupere le chrono en seconde
    	int nbSecondeChrono = (this.getTimeTaskChrono().minute * 60) + (this.getTimeTaskChrono().seconde);
    	// pour les 3 etoilees on regader si le joueur a valide ou non et en fonction on affiche la bonne etoile
    	for(int i=0; i<3 ; i++){
    		if(i==0 || (i==1 && this.owner.getNbBonbonRouge() == this.owner.getNbBonbonRougeAttrape()) || (i==2 && nbSecondeChrono <= this.owner.getTempsChrono()))
    			labEtoile[i] = new JLabel(new ImageIcon(this.map.getMesImg().get("endEtoile")));
    		else
    			labEtoile[i] = new JLabel(new ImageIcon(this.map.getMesImg().get("endEtoileVide")));
	    	labEtoile[i].setForeground(Color.WHITE);
	    	labEtoile[i].setBounds(155 + (i*55), 330, 50, 50);
	    	this.panel_end.add(labEtoile[i]);
    	}
    	
		// Background
		JLabel label = new JLabel("");
		label.setBounds(0, 0, FrameSnake.GET_TOTAL_WIDTH(), FrameSnake.GET_TOTAL_HEIGHT());
		label.setIcon(new ImageIcon(this.owner.getMap().getMesImg().get("bgPanelEnd")));
		this.panel_end.add(label);
		
		// refresh
		this.panel_end.setVisible(false);
		this.panel_end.setVisible(true);
		
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

	public JLabel getLabChrono() {
		return labChrono;
	}

	public void setLabChrono(JLabel labChrono) {
		this.labChrono = labChrono;
	}

	public JLabel getLabBonbonRouge() {
		return labBonbonRouge;
	}

	public void setLabBonbonRouge(JLabel labBonbonRouge) {
		this.labBonbonRouge = labBonbonRouge;
	}

	public JPanel getPanel_end() {
		return panel_end;
	}

	public void setPanel_end(JPanel panel_end) {
		this.panel_end = panel_end;
	}

	public TimerChrono getTimeTaskChrono() {
		return timeTaskChrono;
	}

	public void setTimeTaskChrono(TimerChrono timeTaskChrono) {
		this.timeTaskChrono = timeTaskChrono;
	}



	

	
}

