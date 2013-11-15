import java.awt.Image;


public class Case {
	private int x;
	private int y;
	public static int LARGEUR_CASE = FenetreSnake.LARGEUR_FENETRE/Map.getNbCaseL();
	private Image sol;
	private Image objet;
	
	
	/*
	 *  CONSTRUCTEUR
	 */
	public Case(int x, int y,Image image){
		this.x = x;
		this.y = y;
		this.sol = image;
		this.objet = null;
	}

	/* 
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

	public Image getObjet() {
		return objet;
	}

	public void setObjet(Image objet) {
		this.objet = objet;
	}
	
	
}
