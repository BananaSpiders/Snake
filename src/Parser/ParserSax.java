package Parser;

import java.awt.Image;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Model.Map;

public class ParserSax {

	/**
	 * Attributs
	 */
	public Map myMap;


	public static void main(String argv[]) {
		ParserSax ps = new ParserSax("level1");
		System.out.println(ps.isMyMapReady());
		if(ps.isMyMapReady())
			System.out.println(ps.myMap);
	}

	public ParserSax(String nomMap){

		try {
			this.myMap = null;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new MonHandler(this);

			saxParser.parse("map/"+nomMap+".xml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isMyMapReady(){
		if( this.myMap == null)
			return false;
		else 
			return true;
	}



	public class MonHandler extends DefaultHandler{
		Map map;
		int inColonne = -99;
		int inLigne = -99;


		boolean inSol = false;
		boolean inObjet = false;
		private ParserSax owner;

		public MonHandler(ParserSax owner){
			super();
			this.owner = owner;
		}



		/**
		 * 	START
		 */
		public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {

			/////////////////////////
			//	ICI on recupere les attributs dans tout les cas 
			/////////////////////////
			String str = qName.toUpperCase();
			String x = attributes.getValue("x");
			String y = attributes.getValue("y");
			String sol = attributes.getValue("type");
			if(sol!=null && sol.equals("null"))
				sol=null;
			String objet = attributes.getValue("type");
			if(objet!=null &&  objet.equals("null"))
				objet=null;
			String bloquant = attributes.getValue("bloquant");
			String nombre_case_deplacement = attributes.getValue("nombre_case_deplacement");
			String sens_deplacement = attributes.getValue("sens_deplacement");
			String moveDelay = attributes.getValue("moveDelay");

			// on recupere les attributs pricipaux de la map
			if(str.equals("MAP")){

				// nb de case
				String nbCaseH = attributes.getValue("nbCaseH");
				if(nbCaseH != null)
					Map.setNbCaseH(Integer.parseInt(nbCaseH));

				String nbCaseL = attributes.getValue("nbCaseL");
				if(nbCaseL != null)
					Map.setNbCaseL(Integer.parseInt(nbCaseL));


				// on instancie notre map
				this.map = new Map();

				// nom
				String name = attributes.getValue("name");
				if(name != null)
					this.map.setName(name);
			}

			// identificateur d'entree dans balise
			if(str.equals("COLONNE"))
				inColonne = Integer.parseInt(x);
			if(str.equals("LIGNE"))
				inLigne = Integer.parseInt(y);
			if(str.equals("SOL"))
				inSol = true;
			if(str.equals("OBJET"))
				inObjet = true;
	
			// reaction si entree

			// on remli notre carte ICI
			if(inColonne!=-99 && inLigne!=-99){
				int i = inColonne;
				int j = inLigne;

				//////////////////////////
				//	LES CHOSES A COMPLETER SERONT ICI <<<<<<<<<<<
				/////////////////////////

				// si on est dans une balise sol on rempli la valeure
				if(inSol)
					this.map.getLesCases()[i][j].setSol(this.map.getMesImg().get(sol));

				// si objet, on rempli
				if(inObjet){
					if(objet != null){
						
						this.map.getLesCases()[i][j].makeObjet(this.map.getMesImg().get(objet), 1, 1, Boolean.parseBoolean(bloquant));
						
						this.map.getLesCases()[i][j].getObjet().setNombre_case_deplacement(Integer.parseInt(nombre_case_deplacement));
						this.map.getLesCases()[i][j].getObjet().setSens_deplacement(Integer.parseInt(sens_deplacement));
						this.map.getLesCases()[i][j].getObjet().setMoveDelay(Integer.parseInt(moveDelay));
						if(Integer.parseInt(nombre_case_deplacement)>0)
							this.map.getLesCases()[i][j].getObjet().move(this.map);
					}
				}
			}
		}

		/**
		 *  END
		 */
		public void endElement(String uri, String localName, String qName) throws SAXException {

			String str = qName.toUpperCase();

			if(str.equals("COLONNE"))
				inColonne = -99;
			if(str.equals("LIGNE"))
				inLigne = -99;
			if(str.equals("SOL"))
				inSol = false;
			if(str.equals("OBJET"))
				inObjet = false;
		}

		/**
		 *  DATA
		 */
		public void characters(char ch[], int start, int length) throws SAXException {
			String str = new String(ch, start, length);
			//System.out.println("Charac "+str);
		}


		//fin du parsing
		public void endDocument() throws SAXException {

			System.out.println("Fin du parsing");
			System.out.println("Resultats du parsing");

			this.owner.myMap = this.map;
		}

		public Map getMap(){
			return this.map;
		}


	}



	public static void SerialiseThisMap(Map map){

		//on va chercher le chemin et le nom du fichier et on me tout ca dans un String
		String adressedufichier = System.getProperty("user.dir") + "/map_editeur/"+map.getName()+".xml" ;

		//on met try si jamais il y a une exception
		try
		{
			/**
			 * BufferedWriter a besoin d un FileWriter, 
			 * les 2 vont ensemble, on donne comme argument le nom du fichier
			 * true signifie qu on ajoute dans le fichier (append), on ne marque pas par dessus 

			 */
			FileWriter fw = new FileWriter(adressedufichier, true);

			// le BufferedWriter output auquel on donne comme argument le FileWriter fw cree juste au dessus
			BufferedWriter output = new BufferedWriter(fw);

			String entete = "<map name='"+map.getName()+"' nbCaseH='"+map.getNbCaseH()+"' nbCaseL='20'>";
			String texte = entete;
			
			for(int i=0; i<map.getNbCaseL(); i++){
				String colonne = "<colonne x='"+i+"'>";
				texte+=colonne;
				
				for(int j=0; j<map.getNbCaseH();j++){
					String ligne = "<ligne y='"+j+"'>";
					texte+=ligne;
					
					String sol = "<sol type='"+ParserSax.getNameOfImage(map.getLesCases()[i][j].getSol(),map)+"' />";
					texte+=sol;
					
					String objet = "<objet type='null' bloquant='false'  nombre_case_deplacement='0' sens_deplacement='0' moveDelay='0' />";
					if(map.getLesCases()[i][j].getObjet() != null){
						objet = "<objet type='"+ParserSax.getNameOfImage(map.getLesCases()[i][j].getObjet().getImage(),map)+"' "
								+ "bloquant='"+map.getLesCases()[i][j].getObjet().isBloque()+"' "
										+ "nombre_case_deplacement='"+map.getLesCases()[i][j].getObjet().getNombre_case_deplacement()+"' "
												+ "sens_deplacement='"+map.getLesCases()[i][j].getObjet().getSens_deplacement()+"'"
														+ " moveDelay='"+map.getLesCases()[i][j].getObjet().getMoveDelay()+"' />";
					}
					texte+=objet;
					
					String endLigne = "</ligne>";
					texte+=endLigne;
				}
				String endColonne = "</colonne>";
				texte+=endColonne;
			}
			
			String footer = "</map>";
			texte += footer;
			
			//on marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
			output.write(texte);
			//on peut utiliser plusieurs fois methode write

			output.flush();
			//ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter

			output.close();
			//et on le ferme
			System.out.println("fichier créé");
		}
		catch(IOException ioe){
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}

		

	}
	
	public static String getNameOfImage(Image img,Map map){
		Set cles = map.getMesImg().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   Object cle = it.next(); 
		   Image valeur = map.getMesImg().get(cle);
		   if(valeur.equals(img)){
			   return cle+"";
		   }
		   
		}
		
		return null;
	}
}
