package Model;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import Controler.Main;
import Model.Objet.TimerObjet;

public class Bonus extends Objet{

	private String name;
	
	/**
	 * 	CONSTRUCTEUR
	 * @param objet
	 * @param owner
	 */
	public Bonus(Image objet, Case owner) {
		super(objet, owner);
	}
	
	
	/**
	 *  METHODE
	 */
	public void touche(Map map,Snake snake,Main main){
		//on recupere les position de l'objet
		int i = this.getOwner().getI();
		int j = this.getOwner().getJ();
		
		//on enleve le timer de l'objet si il en avait un
		if(this.getTimerMove() != null){
			this.getTimerMove().cancel();
			this.getTimerMove().purge();
		}
		//on suprimme le bonus de la map
		map.getLesCases()[i][j].setObjet(null);
		
		// on fait une action differente en fonction du bonus
		
		// BONBON jaune : on accelere
		if(this.getName().equals(Association.Bonus[0]))
			this.accelereSnake(4000,1.5f);
		// BONBON rouge : on rajoute une snake part
		else if(this.getName().equals(Association.Bonus[1])){
			snake.addOnePart();
			// et on dit au main qu'on a chope un bonus de plus
			main.setNbBonbonRougeAttrape(main.getNbBonbonRougeAttrape() + 1);
			main.refreshNbBonusAttrape();
		}
	}
	
	
	public void accelereSnake(long time, float coefSpeed){
		// on fait accelerer le serpent pendant 
		Snake.setSnakeSpeed(Snake.getSnakeSpeed()*coefSpeed);
		
		Timer timerStop = new Timer();
		TimerStopAcceleration timerStopAcceleration = new TimerStopAcceleration(timerStop);
		timerStop.scheduleAtFixedRate(timerStopAcceleration, 0, time);
	}
	
	
	
	
	/*
	 *  LES CLASS INTERNES
	 */
	
	/*
	 *  Stop l'acceleration du serpent
	 */
	public class TimerStopAcceleration extends TimerTask{
		
		private Timer timerStop;
		private boolean stop;
		
		public TimerStopAcceleration(Timer timerStop){
			this.timerStop = timerStop;
			this.stop = false;
		}
		@Override
		public void run() {
			if(this.stop){
				Snake.setSnakeSpeed(Snake.getSnakespeeddefaut());
				this.timerStop.cancel();
				this.timerStop.purge();
			}else
				this.stop = true;
		}
	}




	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
}

