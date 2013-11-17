package Model;
import java.awt.Image;

/*
 *  Class des objets presents sur la map
 */
public class Objet {

	/*
	 *  ATTRIBUTS
	 */
	private Image img;
	private int nbCaseL;
	private int nbCaseH;
	private boolean bloque;
	
	/*
	 * Constructeurs
	 */
	public Objet(Image objet){
		this.img = objet;
		this.nbCaseL=1;
		this.nbCaseH=1;
		this.bloque=false;
	}
	
	public Objet(Image obj, int nbL, int nbH, boolean bloque){
		this(obj);
		this.nbCaseL = nbL;
		this.nbCaseH = nbH;
		this.bloque = bloque;
	}
	
	/*
	 *  GETTERS et SETTERS
	 */
	public Image getImage() {
		return img;
	}

	public void setImage(Image objet) {
		this.img = objet;
	}

	public int getNbCaseL() {
		return nbCaseL;
	}

	public void setNbCaseL(int nbCaseL) {
		this.nbCaseL = nbCaseL;
	}

	public int getNbCaseH() {
		return nbCaseH;
	}

	public void setNbCaseH(int nbCaseH) {
		this.nbCaseH = nbCaseH;
	}

	public boolean isBloque() {
		return bloque;
	}

	public void setBloque(boolean bloque) {
		this.bloque = bloque;
	}
	
	
}
