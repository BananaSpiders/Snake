package Model;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import Model.Case;
import View.FrameSnake;


public class Map {
	/**
	 *  ATTRIBUTS
	 */
	// Static
	private static int nbCaseH = 200;
	private static int nbCaseL = 20;
	public static final int NB_CASE_H_SHOW = FrameSnake.FRAME_HEIGHT / Case.LARGEUR_CASE;
	// Variables
	private int startY;
	private int endY;
	// Models
	private Case[][] lesCases;
	// Listes
	private HashMap<String, Image> mesImg;
	
	/**
	 *  CONSTRUCTEUR
	 */
	public Map(){
		this.mesImg = new HashMap<String, Image>();
		this.loadImg();
		
		this.endY =Map.nbCaseH;// this.startY + Map.NB_CASE_H_SHOW;
		this.startY = this.endY- Map.NB_CASE_H_SHOW;
		
		this.lesCases = new Case[Map.nbCaseL][Map.nbCaseH];
		
		// ON CHARGE ET INITIALISE LA MAP ICI
		for(int j = 0;j<Map.nbCaseH;j++)
			for(int i = 0;i<Map.nbCaseL;i++){
				// Par defaut la map est remplie d'herbe
				Image im = this.mesImg.get("herbe");
				
				//on met des objets a la main au pif pour exemple
				
				if(((int)(Math.random() * (200-0) ))==1)
					im = this.mesImg.get("rocher");
				
				if(((int)(Math.random() * (150-0) ))==1)
					im = this.mesImg.get("touf");
				
				if( (j==Map.nbCaseH-7 ) ||(j==Map.nbCaseH-8 && i%2==0) || (j==Map.nbCaseH-9 && i%3==0) ||(j==Map.nbCaseH-10 && i%4==0) || ((int)(Math.random() * (650-0) ))==1)
					im = this.mesImg.get("fleure");
				
				// on ajoute l'image recupere a notre case
				this.lesCases[i][j] = new Case(Case.LARGEUR_CASE*i  ,  Case.LARGEUR_CASE*j ,im);
				
				// on ajoute un objet a notre case
				if( (j==140 || j==Map.nbCaseH-6) && i==Map.nbCaseL/2)
					this.lesCases[i][j].makeObjet(this.mesImg.get("porte"),3,3,false);// on cree une porte de taille 3/3
				if(((int)(Math.random() * (950-0) ))==1)
					this.lesCases[i][j].makeObjet(this.mesImg.get("gros_rocher"),1,1,true);// on cree un gros rocher de taille 2/2
				if(((int)(Math.random() * (550-0) ))==1)
					this.lesCases[i][j].makeObjet(this.mesImg.get("sapin"),1,1,true);// on cree un sapin de taille 3/4
			}
		
		
		// ON MODIFIE LES POSITION EN PIXEL EN FONCTION DES STRAT ET END Y
		// etape oblige pour commencer d'en bas
		for(int j=this.startY; j<this.endY; j++){
			for(int i=0; i<Map.nbCaseL; i++) {
				this.lesCases[i][j].setX(i*Case.LARGEUR_CASE);
				this.lesCases[i][j].setY(j*Case.LARGEUR_CASE - this.startY*Case.LARGEUR_CASE);
			}
		}
		
	}
	/**
	 *  METHODES
	 */
	/**
	 *  LOAD METHOD
	 */
	public void loadImg(){
		try{
			// sols
			this.mesImg.put("herbe", new ImageIcon(this.getClass().getResource("/images/herbe.gif")).getImage()) ;
			this.mesImg.put("rocher", new ImageIcon(this.getClass().getResource("/images/rocher.gif")).getImage()) ;
			this.mesImg.put("touf", new ImageIcon(this.getClass().getResource("/images/touf.gif")).getImage()) ;
			this.mesImg.put("fleure", new ImageIcon(this.getClass().getResource("/images/fleure.gif")).getImage()) ;
			// objets
			this.mesImg.put("porte", new ImageIcon(this.getClass().getResource("/images/porte.gif")).getImage()) ;
			this.mesImg.put("gros_rocher", new ImageIcon(this.getClass().getResource("/images/gros_rocher.gif")).getImage()) ;
			this.mesImg.put("sapin", new ImageIcon(this.getClass().getResource("/images/sapin.gif")).getImage()) ;
		}catch(Exception e){
			System.out.println("Erreur de chargement des images de la map :"+e);
		}
	}
	/**
	 * Retourne la case qui contient ce x et ce y en pixel, (utile pour retrouver le serpent sur toute la map de cases)
	 * @return Case
	 */
	public Case getCaseXY(int pixel_x, int pixel_y){
		
		for(int j=this.startY; j<this.endY;j++)
			for(int i=0;i<Map.nbCaseL;i++){
				if(this.lesCases[i][j].getX()==pixel_x && this.lesCases[i][j].getY()==pixel_y){
					
					return this.lesCases[i][j];
				}
			}
		
		
		return null;
	}

	/**
	 *  GETTER & SETTER
	 */
	public Case[][] getLesCases() {
		return lesCases;
	}

	public void setLesCases(Case[][] lesCases) {
		this.lesCases = lesCases;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public static int getNbCaseH() {
		return nbCaseH;
	}

	public static void setNbCaseH(int nbCaseH) {
		Map.nbCaseH = nbCaseH;
	}

	public static int getNbCaseL() {
		return nbCaseL;
	}

	public static void setNbCaseL(int nbCaseL) {
		Map.nbCaseL = nbCaseL;
	}
	public HashMap<String, Image> getMesImg() {
		return mesImg;
	}
	public void setMesImg(HashMap<String, Image> mesImg) {
		this.mesImg = mesImg;
	}
	
	
}