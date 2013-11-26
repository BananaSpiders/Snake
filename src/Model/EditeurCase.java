package Model;

import java.awt.Dimension;

import javax.swing.JButton;

public class EditeurCase {
	
	// attributs
	private JButton button;
	private String imgName;
	private boolean isObjet;
	private int nombre_case_deplacement;
	private int sens_deplacement;
	private long moveDelay;
	
	
	
	
	
	// constructs
	public EditeurCase(){		
		this.imgName="herbe";
		this.isObjet=true;
	}
	
	public EditeurCase(String imgName,JButton but,boolean isObjet){
		this.button = but;
		this.imgName = imgName;
		this.isObjet = isObjet;
		this.sens_deplacement = 0;
		this.nombre_case_deplacement = 0;
		this.moveDelay = 0;
	}


	
	
	
	//getters et setters
	public JButton getButton() {
		return button;
	}


	public void setButton(JButton button) {
		this.button = button;
	}


	public String getImgName() {
		return imgName;
	}


	public void setImgName(String imgName) {
		this.imgName = imgName;
	}


	public boolean isObjet() {
		return isObjet;
	}


	public void setObjet(boolean isObjet) {
		this.isObjet = isObjet;
	}

	public int getNombre_case_deplacement() {
		return nombre_case_deplacement;
	}

	public void setNombre_case_deplacement(int nombre_case_deplacement) {
		this.nombre_case_deplacement = nombre_case_deplacement;
	}

	public int getSens_deplacement() {
		return sens_deplacement;
	}

	public void setSens_deplacement(int sens_deplacement) {
		this.sens_deplacement = sens_deplacement;
	}

	public long getMoveDelay() {
		return moveDelay;
	}

	public void setMoveDelay(long moveDelay) {
		this.moveDelay = moveDelay;
	}
	
	
	
	
}
