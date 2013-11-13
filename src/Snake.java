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
		
		this.vDeplacement = 5;
		
		this.body = new SnakePart[10];
	}
	
	public void move(){
		
		Case tmp = null;
		
		for(int i = snakeLength-1;i>0;i--)
			this.body[i].setActualCase(this.body[i-1].getActualCase());
		
		tmp = this.body[0].getActualCase();
		
		switch(this.direction){
		case "Left":System.out.println("Left"); 
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[tmp.getX()-1][tmp.getY()]);
			break;
		case "Right":System.out.println("Right"); 
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[tmp.getX()+1][tmp.getY()]);
			break;
		case "Up":System.out.println("Up"); 
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[tmp.getX()][tmp.getY()-1]);
			break;
		case "Down": System.out.println("Down");
			this.body[0].setActualCase(this.owner.getMap().getLesCases()[(tmp.getX()/FenetreSnake.LARGEUR_CASE)][(tmp.getY()/FenetreSnake.LARGEUR_CASE)+1]);
			break;
		default:
			
		}
	}
	
	public void wayUpdate(String way){
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
