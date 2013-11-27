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
	private static int nbCaseH = 50;
	private static int nbCaseL = 20;
	public static final int NB_CASE_H_SHOW = (FrameSnake.FRAME_HEIGHT /*- FrameSnake.FRAME_MARGE_TOP - FrameSnake.FRAME_MARGE_BOTTOM*/) / Case.LARGEUR_CASE;
	// Variables
	private int startY;
	private int endY;
	private String name;
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
				/*
				//on met des objets a la main au pif pour exemple				
				if(((int)(Math.random() * (150-0) ))==1)
					im = this.mesImg.get("touf");
				
				if( (j==Map.nbCaseH-7 ) ||(j==Map.nbCaseH-8 && i%2==0) || (j==Map.nbCaseH-9 && i%3==0) ||(j==Map.nbCaseH-10 && i%4==0) || ((int)(Math.random() * (650-0) ))==1)
					im = this.mesImg.get("fleure");
				*/
				// on ajoute l'image recupere a notre case
				this.lesCases[i][j] = new Case(Case.LARGEUR_CASE*i  ,  Case.LARGEUR_CASE*j ,im);
				
				// on ajoute un objet a notre case
				/*if(((int)(Math.random() * (550-0) ))==1)
					this.lesCases[i][j].makeObjet(this.mesImg.get("tronc"),1,1,true);// on cree un sapin de taille 3/4
				if((j==140 && (i!=10 && i!=11)))
					this.lesCases[i][j].makeObjet(this.mesImg.get("barriere_bois"),1,1,true);
				
				//barrieres qui bouge
				if(j==139 && (i==11 || i==10)){
					this.lesCases[i][j].makeObjet(this.mesImg.get("barriere_bois"),1,1,true);
					this.lesCases[i][j].getObjet().setMoveDelay(2000);
					this.lesCases[i][j].getObjet().setSens_deplacement(1);
					this.lesCases[i][j].getObjet().setNombre_case_deplacement(1);
					this.lesCases[i][j].getObjet().move(this);
				}
					*/
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
			this.mesImg.put("terre", new ImageIcon(this.getClass().getResource("/images/terre.gif")).getImage()) ;
			this.mesImg.put("terreHG", new ImageIcon(this.getClass().getResource("/images/terreHG.gif")).getImage()) ;
			this.mesImg.put("terreHD", new ImageIcon(this.getClass().getResource("/images/terreHD.gif")).getImage()) ;
			this.mesImg.put("terreBG", new ImageIcon(this.getClass().getResource("/images/terreBG.gif")).getImage()) ;
			this.mesImg.put("terreBD", new ImageIcon(this.getClass().getResource("/images/terreBD.gif")).getImage()) ;
			// objets
			this.mesImg.put("porte", new ImageIcon(this.getClass().getResource("/images/porte.gif")).getImage()) ;
			this.mesImg.put("poteau", new ImageIcon(this.getClass().getResource("/images/poteau.gif")).getImage());
			this.mesImg.put("muraille", new ImageIcon(this.getClass().getResource("/images/muraille.gif")).getImage());
			this.mesImg.put("gros_rocher", new ImageIcon(this.getClass().getResource("/images/gros_rocher.gif")).getImage()) ;
			this.mesImg.put("sapin", new ImageIcon(this.getClass().getResource("/images/sapin.gif")).getImage()) ;
			this.mesImg.put("barriere_bois", new ImageIcon(this.getClass().getResource("/images/barriere_bois.gif")).getImage()) ;
			this.mesImg.put("tronc", new ImageIcon(this.getClass().getResource("/images/tronc.gif")).getImage()) ;
			
			// Frame
			this.mesImg.put("butMenu", new ImageIcon(this.getClass().getResource("/images/butMenu.gif")).getImage()) ;
			this.mesImg.put("margeCoteDroite", new ImageIcon(this.getClass().getResource("/images/margeCoteDroite.gif")).getImage()) ;
			this.mesImg.put("margeCoteGauche", new ImageIcon(this.getClass().getResource("/images/margeCoteGauche.gif")).getImage()) ;
			
			// BONBON
			this.mesImg.put("bonbon_red", new ImageIcon(this.getClass().getResource("/images/bonbon_red.gif")).getImage()) ;
			this.mesImg.put("bonbon_yellow", new ImageIcon(this.getClass().getResource("/images/bonbon_yellow.gif")).getImage()) ;
			
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public static int getNbCaseHShow() {
		return NB_CASE_H_SHOW;
	}
	
	
}