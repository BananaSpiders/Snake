package Model;

import java.util.HashMap;

import javax.swing.ImageIcon;
/*
 * 	Fichier de config, contient les associations entre les images, le nom des images et leurs effets dans le jeu
 */
public class Association {
	
	/*
	 * 	LES OBJETS
	 */
	public static final String[] Objet = {
		"tronc",
		"barriere_bois"
		};
	
	
	/*
	 *  LES BONUS
	 */
	public static final String[] Bonus= {
		"bonbon_yellow",
		"bonbon_red"
		};

	/*
	 *  LES SOLS
	 */
	public static final String[] Sol= {
		"herbe",
		"touf",
		"fleure",
		"terre",
		"terreHG",
		"terreHD",
		"terreBG",
		"terreBD"
		};
	
	
	/*
	 *  METHODES
	 */
	
	
	/*
	 *  OBJET et enfant d'objet (ex : bonus)
	 */
	public static String[] getObjetAndChildren(){
		String[] str = new String[Association.Objet.length + Association.Bonus.length];
		
		for(int i=0; i< Association.Objet.length; i++)
			str[i] = Association.Objet[i];
		
		int i = Association.Objet.length;
		
		for(int j=0; j< Association.Bonus.length; j++){
			str[i] = Association.Bonus[j];
			i++;
		}
		
		return str;
	}
}