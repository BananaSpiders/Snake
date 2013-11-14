import java.awt.Image;

import javax.swing.ImageIcon;


public class SnakePart {
	private Case actualCase;
	private Image[] imgTete;
	
	/*
	 *  Constructor
	 */
	public SnakePart(Case actualCase){
		this.actualCase = actualCase;
		
		//this.chargeImage();
		
	}
	
	public void chargeImage(){
		this.imgTete = new Image[4]; // pour G/H/D/B || < ^ > v
		this.imgTete[1] = new ImageIcon(this.getClass().getResource("/images/teteH.png")).getImage();
		this.imgTete[2] = new ImageIcon(this.getClass().getResource("/images/teteD.png")).getImage();
		this.imgTete[3] = new ImageIcon(this.getClass().getResource("/images/teteB.png")).getImage();
		this.imgTete[0] = new ImageIcon(this.getClass().getResource("/images/teteG.png")).getImage();
	}

	public Case getActualCase() {
		return actualCase;
	}

	public void setActualCase(Case actualCase) {
		this.actualCase = actualCase;
	}
	
	
}
