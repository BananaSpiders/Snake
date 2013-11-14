import java.util.ArrayList;


public class Map {
	
	private static int nbCaseH = 200;
	private static int nbCaseL = 20;
	public static final int NB_CASE_H_SHOW = 30;
	
	private Case[][] lesCases;
	private int startY;
	private int endY;
	
	public Map(){
		
		this.endY = this.startY + Map.NB_CASE_H_SHOW;
		this.startY = 0;
		
		this.lesCases = new Case[Map.nbCaseL][Map.nbCaseH];
		
		for(int j = 0;j<Map.nbCaseH;j++)
			for(int i = 0;i<Map.nbCaseL;i++){
				this.lesCases[i][j] = new Case(Case.LARGEUR_CASE*i  ,  Case.LARGEUR_CASE*j + FenetreSnake.OFFSET_TITLEBAR);
			}
		
		
	}

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
	
	
}
