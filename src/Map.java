import java.util.ArrayList;


public class Map {
	private Case[][] lesCases;
	
	public Map(int hauteur,int largeur){
		this.lesCases = new Case[largeur][hauteur];
		
		for(int j = 0;j<hauteur;j++)
			for(int i = 0;i<largeur;i++){
				this.lesCases[i][j] = new Case(FenetreSnake.LARGEUR_CASE*i,FenetreSnake.LARGEUR_CASE*j );
			}
	}

	public Case[][] getLesCases() {
		return lesCases;
	}

	public void setLesCases(Case[][] lesCases) {
		this.lesCases = lesCases;
	}
	
	
}
