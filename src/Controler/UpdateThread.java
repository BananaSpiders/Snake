package Controler;


public class UpdateThread extends Thread {
	private Main owner;
	private boolean running;
	
	public UpdateThread(Main owner){
		this.owner = owner;
		this.running=true;
	}

	public void stopRunning(){
		this.running = false;
	}
	
	
	
	public void run() {

		long now = 0, last = 0;

		while (this.running) {

			try {
				now = System.currentTimeMillis(); // On récupère le temps au
				// début de la boucle
				if ((now - last) > (1000.0 / 7.0)) // Si le laps de temps
				// ecoule durant la
				// boucle est plus grand
				// que le temps espere
				// pour chaque boucle:
				{
					last = now;
					this.owner.updateGame(); // On fait le rendu graphique

				} else
					sleep((long) ((1000.0 / 10.0) - (now - last))); // Sinon
				// on
				// attend
				// le
				// temps
				// necessaire

			} catch (Exception e) {
			}
		}
	}
}
