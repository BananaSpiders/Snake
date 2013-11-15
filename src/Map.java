import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;


public class Map {
	/*
	 *  ATTRIBUTS
	 */
	private static int nbCaseH = 200;
	private static int nbCaseL = 20;
	public static final int NB_CASE_H_SHOW = FenetreSnake.HAUTEUR_FENETRE / Case.LARGEUR_CASE+1;
	
	private Case[][] lesCases;
	private int startY;
	private int endY;
	private HashMap<String, Image> mesImg;
	
	/*
	 *  CONSTRUCTEUR
	 */
	public Map(){
		this.mesImg = new HashMap<String, Image>();
		this.loadImg();
		
		this.endY = this.startY + Map.NB_CASE_H_SHOW;
		this.startY = 0;
		
		this.lesCases = new Case[Map.nbCaseL][Map.nbCaseH];
		
		// ON CHARCHE ET INITIALISE LA MAP ICI
		for(int j = 0;j<Map.nbCaseH;j++)
			for(int i = 0;i<Map.nbCaseL;i++){

				Image im = this.mesImg.get("herbe");
				
				//on met des objets a la main au pif pour exemple
				
				if(((int)(Math.random() * (200-0) ))==1)
					im = this.mesImg.get("rocher");
				
				if(((int)(Math.random() * (150-0) ))==1)
					im = this.mesImg.get("touf");
				
				if( (j==Map.nbCaseH-7 ) ||(j==Map.nbCaseH-8 && i%2==0) || (j==Map.nbCaseH-9 && i%3==0) ||(j==Map.nbCaseH-10 && i%4==0) || ((int)(Math.random() * (650-0) ))==1)
					im = this.mesImg.get("fleure");
				
				this.lesCases[i][j] = new Case(Case.LARGEUR_CASE*i  ,  Case.LARGEUR_CASE*j + FenetreSnake.OFFSET_TITLEBAR,im);
				
				
				if( (j==140 || j==Map.nbCaseH-6) && i==Map.nbCaseL/2)
					this.lesCases[i][j].setObjet(this.mesImg.get("porte"));
			}
		
		
	}
	/*
	 *  METHODES
	 */
	public void loadImg(){
		try{
			this.mesImg.put("herbe", new ImageIcon(this.getClass().getResource("/images/herbe.gif")).getImage()) ;
			this.mesImg.put("rocher", new ImageIcon(this.getClass().getResource("/images/rocher.gif")).getImage()) ;
			this.mesImg.put("touf", new ImageIcon(this.getClass().getResource("/images/touf.gif")).getImage()) ;
			this.mesImg.put("fleure", new ImageIcon(this.getClass().getResource("/images/fleure.gif")).getImage()) ;
			this.mesImg.put("porte", new ImageIcon(this.getClass().getResource("/images/porte.gif")).getImage()) ;
		}catch(Exception e){
			System.out.println("Erreur de chargement des images de la map :"+e);
		}
	}

	/*
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