package Model;
import java.awt.Image;

import View.FrameSnake;

/**
 * Class Model Case
 *
 */
public class Case {
	/**
	 * Attributs
	 */
	// static
	public static int LARGEUR_CASE = (FrameSnake.FRAME_WIDTH) / Map.getNbCaseL() ;
	//Variables
	private int x;
	private int y;
	private int i;
	private int j;
	// Models
	private Image sol;
	private Objet objet;
	
	
	/**
	 *  CONSTRUCTEUR
	 */
	public Case(int x, int y,Image image){
		this.x = x;
		this.y = y;
		this.sol = image;
		this.objet = null;
		this.i = x/Case.LARGEUR_CASE;
		this.j = y/Case.LARGEUR_CASE;
	}
	
	/**
	 *  METHODE
	 */
	// Ajoute un objet a notre case avec une largeur et hauteur
	public void makeObjet(Image img, int nbCaseL, int nbCaseH,boolean bloque){
		this.objet = new Objet(img, nbCaseL, nbCaseH,bloque,this);
	}

	/**
	 * GETTER et SETTER
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getSol() {
		return sol;
	}

	public void setSol(Image sol) {
		this.sol = sol;
	}

	public Objet getObjet() {
		return this.objet;
	}

	public void setObjet(Objet objet) {
		this.objet = objet;
	}

	public static int getLARGEUR_CASE() {
		return LARGEUR_CASE;
	}

	public static void setLARGEUR_CASE(int lARGEUR_CASE) {
		LARGEUR_CASE = lARGEUR_CASE;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}	
	
}
