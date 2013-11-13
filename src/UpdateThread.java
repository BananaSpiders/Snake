
public class UpdateThread extends Thread {
private FenetreSnake owner;
	
	public UpdateThread(FenetreSnake owner){
		this.owner = owner;
	}

	public void run() {

		long now = 0, last = 0;

		while (true) {

			try {
				now = System.currentTimeMillis(); // On r�cup�re le temps au
				// d�but de la boucle
				if ((now - last) > (1000.0 / 10.0)) // Si le laps de temps
				// ecoule durant la
				// boucle est plus grand
				// que le temps espere
				// pour chaque boucle:
				{
					last = now;
					this.owner.update(); // On fait le rendu graphique

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
