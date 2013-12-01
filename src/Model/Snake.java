package Model;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import View.FrameMenu;
import Controler.Main;


/** 
 * Class principale du snake, elle contient toutes les parties de celui-ci 
 */

public class Snake {
	/**
	 * 	ATTRIBUTS
	 */
	// static
	private static final int TAILLE_MAX = 15;
	// variables
	private int snakeLength;
	private static final float snakeSpeedDefaut = 0.9f;
	private String direction;
	// models
	private Main owner;
	//tableau
	private SnakePart[] body;
	private static float snakeSpeed = 0.9f;
	
	
	/**
	 * CONSTRUCTEURS
	 */
	public Snake(Main owner){
		// initialise les variables
		this.owner = owner;
		this.snakeLength = 5;
		this.direction = "Up";
		// on cree les parties
		this.body = new SnakePart[Snake.TAILLE_MAX];
		// on initialise les parties
		for(int i = 0;i<this.snakeLength;i++)
			this.body[i] = new SnakePart(this.owner.getMap().getLesCases()[10][this.owner.getMap().getEndY()-1]);
	}
	
	/*
	 *  METHODES
	 */
	
	/*
	 * Ajoute une partie de plus au snake, retourne false si le snake a atteind son maximum
	 */
	public boolean addOnePart(){
		if(this.snakeLength == Snake.TAILLE_MAX)
			return false;
		
		this.body[this.snakeLength] = new SnakePart(this.body[this.snakeLength-1].getActualCase());
		
		this.snakeLength++;
		return true;
	}
	
	/*
	 *  Move method
	 */
	public void move(){
		
		Case tmp = null;
		
		for(int i = snakeLength-1;i>0;i--)
			this.body[i].setActualCase(this.body[i-1].getActualCase());
		
		tmp = this.body[0].getActualCase();
		//System.out.println("Snake position : x :"+(tmp.getX()/Case.LARGEUR_CASE)+" Y : "+((tmp.getY())/Case.LARGEUR_CASE + this.owner.getMap().getStartY())+" -- "+this.owner.getMap().getEndY());
		switch(this.direction){
		case "Left":
			if(tmp.getX()/Case.LARGEUR_CASE > 0 ){
				this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)-1][(tmp.getY())/Case.LARGEUR_CASE + this.owner.getMap().getStartY()]);
			}
			break;
		case "Right":
			if(tmp.getX()/Case.LARGEUR_CASE < Map.getNbCaseL()-1){
				this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)+1][(tmp.getY())/Case.LARGEUR_CASE + this.owner.getMap().getStartY()]);
			}
			break;
		case "Up":
			if(((tmp.getY())/Case.LARGEUR_CASE) + this.owner.getMap().getStartY() > 0){
				this.body[0].setActualCase(this.owner.getMap().getLesCases()[tmp.getX()/Case.LARGEUR_CASE][((tmp.getY())/Case.LARGEUR_CASE)-1 + this.owner.getMap().getStartY()]);
				
				// si la carte est tout en haut ET (que le le bas affiche de la map est inferrieur au nombre de case OU que le joueur est au dessus du centre)
				// alors on fait le rendu de la map (le deplacement)
				if((this.owner.getMap().getStartY()>0)&&(this.owner.getMap().getEndY()<Map.getNbCaseH() || (this.body[0].getActualCase().getY())/Case.LARGEUR_CASE < Main.SNAKE_CENTER_Y))
					this.owner.updateView("Up");
			}
			break;
		case "Down":
			if(((tmp.getY())/Case.LARGEUR_CASE) + this.owner.getMap().getStartY() < Map.getNbCaseH()-1){
				this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)][((tmp.getY())/Case.LARGEUR_CASE)+1 + this.owner.getMap().getStartY()]);
				
				// si la carte est tout en BAS ET (que le haut la map est supperrieur a 0 OU que le joueur est en dessous du centre)
				// alors on fait le rendu de la map (le deplacement)
				if((this.owner.getMap().getEndY()<Map.getNbCaseH())&&(this.owner.getMap().getStartY()>0 || (this.body[0].getActualCase().getY())/Case.LARGEUR_CASE > Main.SNAKE_CENTER_Y))
					this.owner.updateView("Down");
			}
			break;
		default:
			System.out.println("Pas de move..");
			break;
		}
		
		
		// Si on est a larrivee, on GAGNE !
		if(this.body[0].getActualCase().getJ() == 0){
			JOptionPane.showMessageDialog(this.owner.getFrame(), "gg", "gagnee ! On passe au niveau suivant !!", JOptionPane.INFORMATION_MESSAGE);
			
			
			// on recupere le numero de la map actuelle
			Pattern p = Pattern.compile("(.)*_([0-9])*");
			Matcher mFile = p.matcher(this.owner.getMapPlay());
			
			int numerroMap = 0;
			try{
				numerroMap = Integer.parseInt(mFile.replaceAll("$2"));
			}catch(Exception e){
				this.owner.retourMenu();
			}
			
			//System.out.println(numerroMap);
			
			// on regarde si il reste des maps
			String[] mapRestantes = FrameMenu.selectMapsDisponibles();
			if(numerroMap<mapRestantes.length){
				Main m = new Main("level_"+(numerroMap+1));
				m.getFrame().setVisible(false);
				m.getFrame().setVisible(true);
				this.owner.setRunning(false);
				
			}else{
				JOptionPane.showMessageDialog(this.owner.getFrame(), "BRAVO !", "Vous avez fini tous les niveaux !  !!", JOptionPane.INFORMATION_MESSAGE);
				this.owner.retourMenu();
			}
			
		}
	}
	
	/*
	 *  Verifie si la direction choisi n'est pas oposee a celle ou on va
	 */
	public void wayUpdate(String way){
		boolean ok = true;
		
		if(this.direction.equals("Up") && way.equals("Down"))
			ok = false;
		if(this.direction.equals("Down") && way.equals("Up"))
			ok = false;
		if(this.direction.equals("Left") && way.equals("Right"))
			ok = false;
		if(this.direction.equals("Right") && way.equals("Left"))
			ok = false;
		
		if(ok)
			this.direction = way;
	}
	
	
	/*
	 *  GETTERS & SETTERS
	 */
	
	// recupere la direction sous form de int
	public int getXdirection(){
		if(this.direction.equals("Right"))
			return 1;
		else if(this.direction.equals("Left"))
			return -1;
		else return 0;
	}
	public int getYdirection(){
		if(this.direction.equals("Down"))
			return 1;
		else if(this.direction.equals("Up"))
			return -1;
		else return 0;
	}
	
	// retourne la prochaine case qui arrive devant le snake 
	public Case getNextCase(){
		/*
		 *  NEM MARCHE PAS car une fois le snake dessendu, les case ne sont plus au meme endroit.
		 */
		// on recupere la direction
		int x = this.getXdirection()*Case.LARGEUR_CASE;
		int y = this.getYdirection()*Case.LARGEUR_CASE;
		// on recupere la position
		int i= this.body[0].getActualCase().getX() ;
		int j= this.body[0].getActualCase().getY() ;
		//System.out.println(i+" "+j);
		// on recupere la position future
		i += x;
		j += y;
		
		// on recupere la case future
		Case c = this.owner.getMap().getCaseXY(i, j);
		
		// on retourne la case future
		return c;
	}
	public SnakePart[] getBody() {
		return body;
	}

	public void setCorps(SnakePart[] body) {
		this.body = body;
	}



	public Main getOwner() {
		return owner;
	}

	public void setOwner(Main owner) {
		this.owner = owner;
	}

	public int getSnakeLength() {
		return snakeLength;
	}

	public void setSnakeLength(int snakeLength) {
		this.snakeLength = snakeLength;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setBody(SnakePart[] body) {
		this.body = body;
	}

	public static float getSnakeSpeed() {
		return snakeSpeed;
	}

	public static void setSnakeSpeed(float snakeSpeed) {
		Snake.snakeSpeed = snakeSpeed;
	}

	public static int getTailleMax() {
		return TAILLE_MAX;
	}

	public static float getSnakespeeddefaut() {
		return snakeSpeedDefaut;
	}
	
	
}
