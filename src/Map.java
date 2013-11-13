import java.util.ArrayList;


public class Map {
	
	private static int nbCaseH = 200;
	private static int nbCaseL = 20;
	
	private Case[][] lesCases;
	private int startY;
	private int endY;
	
	public Map(){

		this.lesCases = new Case[20][200];
		System.out.println("Bienvenue");
		for(int j = 0;j<Map.nbCaseH;j++)
			for(int i = 0;i<Map.nbCaseL;i++){
				this.lesCases[i][j] = new Case(Case.LARGEUR_CASE*i,Case.LARGEUR_CASE*j - this.startY*Case.LARGEUR_CASE);
				System.out.println("Case "+i+" "+j+" : X :"+Case.LARGEUR_CASE*i+" Y : "+(Case.LARGEUR_CASE*j - this.startY*Case.LARGEUR_CASE));
			}
		this.endY = Map.nbCaseH;
		this.startY = Map.nbCaseH-20;
		
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
	
	
}
