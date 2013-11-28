package Model;

import java.awt.Image;

public class Bonus extends Objet{

	public String bon;
	/**
	 * 	CONSTRUCTEUR
	 * @param objet
	 * @param owner
	 */
	public Bonus(Image objet, Case owner) {
		super(objet, owner);
		this.bon = "bonuuuus";
	}
	
	
	/**
	 *  METHODE
	 */
	public void touche(Map map,Snake snake){
		//on recupere les position de l'objet
		int i = this.getOwner().getI();
		int j = this.getOwner().getJ();
		
		//on suprimme le bonus de la map
		map.getLesCases()[i][j].setObjet(null);
		
		// on fait accelerer le serpent
		Snake.setSnakeSpeed(Snake.getSnakeSpeed()*1.2f);
	}
	

}
