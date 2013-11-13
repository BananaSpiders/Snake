import java.util.ArrayList;


public class Snake {
	private FenetreSnake owner;
	private SnakePart[] body;
	private int snakeLength;
	private int vDeplacement;
	private String direction;
	
	public Snake(FenetreSnake owner){
		this.owner = owner;
		this.snakeLength = 5;
		this.direction = "Down";
		
		this.body = new SnakePart[10];
	}
	
	public void move(){
		
		Case tmp = null;
		
		for(int i = snakeLength-1;i>0;i--)
			this.body[i].setActualCase(this.body[i-1].getActualCase());
		
		tmp = this.body[0].getActualCase();
		
		switch(this.direction){
		case "Left":
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)-1][tmp.getY()/Case.LARGEUR_CASE]);
			break;
		case "Right":
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)+1][tmp.getY()/Case.LARGEUR_CASE]);
			break;
		case "Up": 
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[tmp.getX()/Case.LARGEUR_CASE][(tmp.getY()/Case.LARGEUR_CASE)-1]);
			break;
		case "Down": 
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/Case.LARGEUR_CASE)][(tmp.getY()/Case.LARGEUR_CASE)+1]);
			break;
		default:
			
		}
	}
	
	public void wayUpdate(String way){
		boolean ok = true;
		
		if(this.direction.equals("Up") && way.equals("Down"))
			ok = false;
		if(this.direction.equals("Down") && way.equals("Up"))
			ok = false;
		if(this.direction.equals("Left") && way.equals("Right"))
			ok = false;
		if(this.direction.equals("Right") && way.equals("Left"))
			ok = false;
		
		if(ok)
			this.direction = way;
	}

	public SnakePart[] getBody() {
		return body;
	}

	public void setCorps(SnakePart[] body) {
		this.body = body;
	}

	public int getvDeplacement() {
		return vDeplacement;
	}

	public void setvDeplacement(int vDeplacement) {
		this.vDeplacement = vDeplacement;
	}

	public FenetreSnake getOwner() {
		return owner;
	}

	public void setOwner(FenetreSnake owner) {
		this.owner = owner;
	}

	public int getSnakeLength() {
		return snakeLength;
	}

	public void setSnakeLength(int snakeLength) {
		this.snakeLength = snakeLength;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setBody(SnakePart[] body) {
		this.body = body;
	}
	
	
}
