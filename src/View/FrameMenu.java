package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Controler.Main;
import Model.Association;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class FrameMenu extends JFrame {

	private JPanel contentPane;
	private int bg_width;
	private int bg_height;
	private ImageIcon bg_image;
	private JComboBox comboBoxMap;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMenu frame = new FrameMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameMenu() {
		bg_image = new ImageIcon(FrameMenu.class.getResource("/images/bg_menu.gif"));
		bg_width = bg_image.getIconWidth();
		bg_height = bg_image.getIconHeight();
		
		
		setResizable(false);
		setTitle("Snake - Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		this.getContentPane().setPreferredSize(new Dimension(bg_width,bg_height));
		
		JButton btnJouer = new JButton("Jouer");
		btnJouer.setForeground(new Color(0, 0, 0));
		btnJouer.setFocusable(false);
		btnJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mapSelectionnee = comboBoxMap.getSelectedItem()+"";
				// Lancement du jeu
				new Main(mapSelectionnee+"");
				dispose();
			}
		});
		btnJouer.setBounds(161, 388, 173, 55);
		contentPane.add(btnJouer);
		
		this.comboBoxMap = new JComboBox();
		
		String[] tabOfMap = this.selectMapsDisponibles();
		
		comboBoxMap.setModel(new DefaultComboBoxModel(tabOfMap));
		comboBoxMap.setBounds(296, 53, 145, 20);
		contentPane.add(comboBoxMap);
		
		JLabel label = new JLabel("");
		label.setIcon(bg_image);
		label.setBounds(0, 0, bg_width, bg_height);
		contentPane.add(label);
		this.pack();
	}
	
	
	/**
	 * 	Retourne les maps disponibles
	 * @return String[] mapFound
	 */
	public String[] selectMapsDisponibles(){
		// on recupere les path vers les map
		File fileRepertoireMap = new File(System.getProperty("user.dir")+"\\map\\");
		File[] fileListMap = fileRepertoireMap.listFiles();
		
		// on verifie quil y en existe bien au moins une
		if(fileListMap==null || fileListMap.length == 0)
			return new String[]{"Aucune"};
		
		// on instancie notre tableau de map
		String[] tabMapFound = new String[fileListMap.length];
		
		// pour toutes celles que l'on a trouvé
		for(int i=0;i<fileListMap.length;i++){
			
			// on recupere le path
			String pathFile = fileListMap[i]+"";
			
			// on cree une regex
			Pattern p = Pattern.compile("(.)*\\\\((\\w)*)(.xml)");
			Matcher mFile = p.matcher(pathFile);
			
			// on cree une map
			String mapFound = "Undefined";
			
			// si on a trouvee une map dans le path, 
				//on ne recupere que le 2ieme grp de la regex (donc juste le nom de la map)
			if(mFile.find())
				mapFound = mFile.replaceAll("$2");
			
			// on ajoute le nom trouve au tableau
			tabMapFound[i] = mapFound;
		}
		
		return tabMapFound;
	}
	
}
