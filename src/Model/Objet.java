package Model;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

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
	private int nombre_case_deplacement;
	private int sens_deplacement;
	private long moveDelay;
	private int nombre_case_reste_deplacement;
	private Case owner;
	
	
	/*
	 * Constructeurs
	 */
	public Objet(Image objet, Case owner){
		this.img = objet;
		this.nbCaseL=1;
		this.nbCaseH=1;
		this.bloque=false;
		this.sens_deplacement = 0;
		this.owner = owner;
		this.moveDelay = 1000;
	}
	
	public Objet(Image obj, int nbL, int nbH, boolean bloque, Case owner){
		this.img =obj;
		this.nbCaseL = nbL;
		this.nbCaseH = nbH;
		this.bloque = bloque;
		this.owner = owner;
		this.moveDelay = 1000;
	}
	
	/**
	 *  METHODES
	 */
	/**
	 *  Bouge l'objet
	 * @param map
	 */
	public void move(Map map){
		
		TimerObjet timerObjet = new TimerObjet(map,this);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerObjet, 0, this.moveDelay);
	}
	
	/**
	 *  CLASS timer objet (TIMER )
	 */
	public class TimerObjet extends TimerTask{
		private Map map;
		private Objet objet;
		
		public TimerObjet(Map map,Objet owner){
			this.map = map;
			this.objet = owner;
		}
		@Override
		public void run() {			
			// on recupere la case d'a cote
			int iNext = this.objet.owner.getI() + this.objet.getSens_deplacement() ;
			int jNext = this.objet.owner.getJ();
			
			// si on est  sur un bord ou on touche un autre objet on change de sens
			if( (iNext < 0 || iNext >= Map.getNbCaseL()) || (this.map.getLesCases()[iNext][jNext].getObjet() != null) ){
				this.objet.setSens_deplacement(this.objet.getSens_deplacement() * -1);
				// on redonne le nouveau i
				iNext = this.objet.owner.getI() + this.objet.getSens_deplacement() ;
				// on reincremente le nombre de deplacement
				this.objet.setNombre_case_reste_deplacement(this.objet.getNombre_case_deplacement());
			}
			
			// on se deplace
			this.map.getLesCases()[iNext][jNext].setObjet(this.objet);
			// on supprime lancienne position
			this.map.getLesCases()[this.objet.owner.getI()][this.objet.owner.getJ()].setObjet(null);
			
			//on donne la nouvelle case a l'objet
			this.objet.setOwner(this.map.getLesCases()[iNext][jNext]);
			
			// on decrement son nombre de case de deplacement restant
			this.objet.setNombre_case_reste_deplacement(this.objet.getNombre_case_reste_deplacement() - 1);
			
			// si l'objet a atteint son nb total de case de deplacement on change de sens et reincrement son nombre de case de dplacement
			if(this.objet.getNombre_case_reste_deplacement()<=0){
				this.objet.setNombre_case_reste_deplacement(this.objet.getNombre_case_deplacement());
				this.objet.setSens_deplacement(this.objet.getSens_deplacement() * -1);
			}
			
		}
	}
	
	
	
	/**
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
	
	public boolean isMove(){
		if( this.sens_deplacement == 0)
			return false;
		else 
			return true;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getNombre_case_deplacement() {
		return nombre_case_deplacement;
	}

	public void setNombre_case_deplacement(int nombre_case_deplacement) {
		this.nombre_case_deplacement = nombre_case_deplacement;
		this.nombre_case_reste_deplacement = nombre_case_deplacement;
	}

	public int getSens_deplacement() {
		return sens_deplacement;
	}

	public void setSens_deplacement(int sens_deplacement) {
		this.sens_deplacement = sens_deplacement;
	}

	public int getNombre_case_reste_deplacement() {
		return nombre_case_reste_deplacement;
	}

	public void setNombre_case_reste_deplacement(int nombre_case_reste_deplacement) {
		this.nombre_case_reste_deplacement = nombre_case_reste_deplacement;
	}

	public Case getOwner() {
		return owner;
	}

	public void setOwner(Case owner) {
		this.owner = owner;
	}

	public long getMoveDelay() {
		return moveDelay;
	}

	public void setMoveDelay(long moveDelay) {
		this.moveDelay = moveDelay;
	}
	
}
