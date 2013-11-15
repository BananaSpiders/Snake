import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Snake {
	/*
	 * 	ATTRIBUTS
	 */
	private static final int TAILLE_MAX = 10;
	
	private FenetreSnake owner;
	private SnakePart[] body;
	private int snakeLength;
	private int vDeplacement;
	private String direction;
	
	/*
	 * CONSTRUCTEURS
	 */
	public Snake(FenetreSnake owner){
		this.owner = owner;
		this.snakeLength = 5;
		this.direction = "Up";
		
		this.body = new SnakePart[Snake.TAILLE_MAX];
		
		for(int i = 0;i<5;i++)
			this.body[i] = new SnakePart(this.owner.getMap().getLesCases()[10][i+FenetreSnake.SNAKE_CENTER_Y]);
	}
	
	/*
	 *  METHODES
	 */
	public void move(){
		
		Case tmp = null;
		
		for(int i = snakeLength-1;i>0;i--)
			this.body[i].setActualCase(this.body[i-1].getActualCase());
		
		tmp = this.body[0].getActualCase();
		System.out.println("Snake position : x :"+(tmp.getX()/Case.LARGEUR_CASE)+" Y : "+((tmp.getY()-FenetreSnake.OFFSET_TITLEBAR)/Case.LARGEUR_CASE + this.owner.getMap().getStartY())+" -- "+this.owner.getMap().getEndY());
		switch(this.direction){
		case "Left":
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)-1][(tmp.getY()-FenetreSnake.OFFSET_TITLEBAR)/Case.LARGEUR_CASE + this.owner.getMap().getStartY()]);
			break;
		case "Right":
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)+1][(tmp.getY()-FenetreSnake.OFFSET_TITLEBAR)/Case.LARGEUR_CASE + this.owner.getMap().getStartY()]);
			break;
		case "Up": 
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[tmp.getX()/Case.LARGEUR_CASE][((tmp.getY()-FenetreSnake.OFFSET_TITLEBAR)/Case.LARGEUR_CASE)-1 + this.owner.getMap().getStartY()]);
			
			// si la carte est tout en haut ET (que le le bas affiche de la map est inferrieur au nombre de case OU que le joueur est au dessus du centre)
			// alors on fait le rendu de la map (le deplacement)
			if((this.owner.getMap().getStartY()>0)&&(this.owner.getMap().getEndY()<Map.getNbCaseH() || (this.body[0].getActualCase().getY()-FenetreSnake.OFFSET_TITLEBAR)/Case.LARGEUR_CASE < FenetreSnake.SNAKE_CENTER_Y))
				this.owner.updateView("Up");
			break;
		case "Down":
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)][((tmp.getY()-FenetreSnake.OFFSET_TITLEBAR)/Case.LARGEUR_CASE)+1 + this.owner.getMap().getStartY()]);
			
			// si la carte est tout en BAS ET (que le haut la map est supperrieur a 0 OU que le joueur est en dessous du centre)
			// alors on fait le rendu de la map (le deplacement)
			if((this.owner.getMap().getEndY()<Map.getNbCaseH())&&(this.owner.getMap().getStartY()>0 || (this.body[0].getActualCase().getY()-FenetreSnake.OFFSET_TITLEBAR)/Case.LARGEUR_CASE > FenetreSnake.SNAKE_CENTER_Y))
				this.owner.updateView("Down");
			break;
		default:
			System.out.println("Pas de move..");
			break;
		}
		
		
	}
	
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

	public SnakePart[] getBody() {
		return body;
	}

	public void setCorps(SnakePart[] body) {
		this.body = body;
	}

	public int getvDeplacement() {
		return vDeplacement;
	}

	public void setvDeplacement(int vDeplacement) {
		this.vDeplacement = vDeplacement;
	}

	public FenetreSnake getOwner() {
		return owner;
	}

	public void setOwner(FenetreSnake owner) {
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
	
	
}
