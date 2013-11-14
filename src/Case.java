
public class Case {
	private int x;
	private int y;
	public static int LARGEUR_CASE = FenetreSnake.LARGEUR_FENETRE/Map.getNbCaseL();
	
	
	
	public Case(int x, int y){
		this.x = x;
		this.y = y;
	}

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
	
	
}
