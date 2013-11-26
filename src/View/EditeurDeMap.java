package View;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JToggleButton.ToggleButtonModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Dimension2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.SwingConstants;

import Model.EditeurCase;
import Model.Map;
import Parser.ParserSax;

import java.awt.Font;

import javax.swing.JToggleButton;
/**
 *  Class Fenetre de l'editeur de map (a developper)
 */
public class EditeurDeMap extends JFrame implements ActionListener,MouseListener {
	/**
	 *  ATTRIBUTS
	 */
	private JTextField textField;
	private EditeurCase[][] lesCases;
	private Map map;
	private boolean mousePressed = false;
	private int POS_X_DRAW = 357;
	private JTextField textField_1;
	private HashMap<String, ImageIcon> editImage;
	private JTextField select;
	private int nbCaseH;
	private JToggleButton tglbtnBouge;

	/**
	 * Main test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditeurDeMap frame = new EditeurDeMap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructeur
	 */
	public EditeurDeMap() {
		
		
		this.nbCaseH = this.demandeQuelleHauteur();
		// on dit que la fenetre ce load via un cursor wait
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.setTitle("Chargement...");
		this.setVisible(true);
		
		this.editImage = new HashMap<String, ImageIcon>();
		
		this.loadImage();
		
		this.map = new Map();
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 50, 800, 600);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 293, 549);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNom = new JLabel("Nom carte :");
		lblNom.setBounds(10, 48, 81, 14);
		panel.add(lblNom);
		
		textField = new JTextField();
		textField.setBounds(101, 45, 171, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblSelect = new JLabel("Select :");
		lblSelect.setBounds(10, 11, 81, 14);
		panel.add(lblSelect);
		
		this.select = new JTextField("herbe");
		this.select.setEditable(false);
		this.select.setBounds(101, 8, 171, 20);
		panel.add(this.select);
		this.select.setColumns(10);
		
		// but fleure
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				select.setText("fleure");
			}
		});
		button.setIcon(this.editImage.get("fleure"));
		button.setBounds(78, 164, 40, 25);
		panel.add(button);
		
		// but herbe
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select.setText("herbe");
			}
		});
		button_1.setIcon(this.editImage.get("herbe"));
		button_1.setBounds(78, 128, 40, 25);
		panel.add(button_1);
		
		// But touf
		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select.setText("touf");
			}
		});
		button_2.setIcon(this.editImage.get("touf"));
		button_2.setBounds(193, 128, 40, 25);
		panel.add(button_2);
		
		JLabel lblHerbe = new JLabel("Herbe :");
		lblHerbe.setBounds(10, 128, 46, 14);
		panel.add(lblHerbe);
		
		JLabel lblFleure = new JLabel("Fleure :");
		lblFleure.setBounds(10, 165, 46, 14);
		panel.add(lblFleure);
		
		JLabel lblTouf = new JLabel("Touf :");
		lblTouf.setBounds(147, 128, 46, 14);
		panel.add(lblTouf);
		
		JLabel lblTerre = new JLabel("Terre :");
		lblTerre.setBounds(147, 165, 46, 14);
		panel.add(lblTerre);
		
		// but terre
		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select.setText("terre");
			}
		});
		button_3.setIcon(this.editImage.get("terre"));
		button_3.setBounds(193, 164, 40, 25);
		panel.add(button_3);
		
		// BUT TERRE COINS
		// HG
		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select.setText("terreHG");
			}
		});
		button_4.setBounds(247, 164, 10, 10);
		panel.add(button_4);
		// HD
		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select.setText("terreHD");
			}
		});
		button_5.setBounds(262, 164, 10, 10);
		panel.add(button_5);
		//BG
		JButton button_6 = new JButton("");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select.setText("terreBD");
			}
		});
		button_6.setBounds(262, 179, 10, 10);
		panel.add(button_6);
		// BD
		JButton button_7 = new JButton("");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select.setText("terreBG");
			}
		});
		button_7.setBounds(247, 179, 10, 10);
		panel.add(button_7);
		
		JLabel lblSol = new JLabel("Sol :");
		lblSol.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSol.setBounds(10, 92, 81, 25);
		panel.add(lblSol);
		
		JLabel lblObjet = new JLabel("Objet :");
		lblObjet.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblObjet.setBounds(10, 230, 81, 25);
		panel.add(lblObjet);
		
		JLabel lblTronc = new JLabel("Tronc :");
		lblTronc.setBounds(10, 276, 58, 14);
		panel.add(lblTronc);
		
		// TRONC
		JButton button_8 = new JButton("");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				select.setText("tronc");
			}
		});
		button_8.setIcon(this.editImage.get("tronc"));
		button_8.setBounds(78, 272, 40, 25);
		panel.add(button_8);
		
		JButton btnCreer = new JButton("Creer");
		btnCreer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				serialiseLaMap();
			}
		});
		btnCreer.setBounds(194, 515, 89, 23);
		panel.add(btnCreer);
		
		JLabel lblPortailleBois = new JLabel("Barriere Bois :");
		lblPortailleBois.setBounds(147, 276, 86, 14);
		panel.add(lblPortailleBois);
		
		
		//barriere_bois
		JButton button_9 = new JButton("");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				select.setText("barriere_bois");
			}
		});
		button_9.setIcon(this.editImage.get("barriere_bois"));
		button_9.setBounds(227, 272, 40, 25);
		panel.add(button_9);
		
		// Togggle Bouton BOUGE
		this.tglbtnBouge = new JToggleButton("Bouge");
		this.tglbtnBouge.setBounds(78, 234, 71, 23);
		panel.add(this.tglbtnBouge);
		
		
		// panel dessin
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(this.POS_X_DRAW, 11, 425, 549);
		getContentPane().add(panel_2);
		
		JScrollPane scrollFrame = new JScrollPane();
		panel_2.add(scrollFrame);
		scrollFrame.setPreferredSize(new Dimension( 418,545));
		
		
		
		JPanel panel_1 = new JPanel();
		scrollFrame.setViewportView(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		//////////////////////
		//	Init le tableau de cases
		//////////////////////
		this.lesCases = new EditeurCase[Map.getNbCaseL()][this.nbCaseH];
		
        for(int j=0; j<this.nbCaseH;j++){
        	for(int i=0;i<Map.getNbCaseL();i++){
        		this.lesCases[i][j] = new EditeurCase("herbe",new JButton(this.editImage.get("herbe")),false);
        		this.lesCases[i][j].getButton().setPreferredSize(new Dimension(20,20));
        		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        		gbc_btnNewButton.insets = new Insets(0, 0, 0, 0);
        		gbc_btnNewButton.gridx = i;
        		gbc_btnNewButton.gridy = j;
        		gbc_btnNewButton.weightx = 0;
        		panel_1.add(this.lesCases[i][j].getButton(), gbc_btnNewButton);
        		this.lesCases[i][j].getButton().addActionListener(this);
        		this.lesCases[i][j].getButton().addMouseListener(this);
        	}
        }

        // la fenetre c'est loadee
        this.setTitle("Editeur de map");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public void loadImage(){
		this.editImage.put("herbe", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/herbe.gif")));
		this.editImage.put("fleure", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/fleure.gif")));
		this.editImage.put("touf", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/touf.gif")));
		this.editImage.put("terre", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/terre.gif")));
		this.editImage.put("terreHG", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/terreHG.gif")));
		this.editImage.put("terreHD", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/terreHD.gif")));
		this.editImage.put("terreBG", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/terreBG.gif")));
		this.editImage.put("terreBD", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/terreBD.gif")));
		this.editImage.put("barriere_bois", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/barriere_bois.gif")));
		this.editImage.put("tronc", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/tronc.gif")));
	}
	/**
	 * 	Demande la hauteure de la map
	 */
	public int demandeQuelleHauteur(){
		int hauteur = 200;
		boolean hauteurError = true;
		
		do{
			
			hauteurError = true;
			try{
				
				hauteur = Integer.parseInt(JOptionPane.showInputDialog(this, "Donnez la hauteur de la map (en nombre de case > 200)", 200));
				if(hauteur<200)
					hauteurError = false;
			}catch(Exception e){
				hauteurError = false;
			}
			
		}while(!hauteurError);
		
		return hauteur;
	}
	
	/**
	 *  Serialise la map
	 */
	public void serialiseLaMap(){
		// on redeffini la hauteure
		Map.setNbCaseH(this.nbCaseH);
		// on instancie la map
		
		Map mapASerialiser = new Map();
		// on lui donne un nom
		if(this.textField.getText() != "" && this.textField.getText() != null)
			mapASerialiser.setName(this.textField.getText());
		else
			mapASerialiser.setName("defaut");
		// on rempli ses cases
		for(int i=0; i<Map.getNbCaseL(); i++){
			for(int j=0; j<Map.getNbCaseH(); j++){
				
				String img = this.lesCases[i][j].getImgName();
				
				// on determine si cest un sol ou objet
				if(!this.lesCases[i][j].isObjet()){
					mapASerialiser.getLesCases()[i][j].setSol(this.map.getMesImg().get(img));
				}else{
					mapASerialiser.getLesCases()[i][j].makeObjet(this.map.getMesImg().get(img), 1, 1, true);
					mapASerialiser.getLesCases()[i][j].getObjet().setNombre_case_deplacement(this.lesCases[i][j].getNombre_case_deplacement());
					mapASerialiser.getLesCases()[i][j].getObjet().setSens_deplacement(this.lesCases[i][j].getSens_deplacement());
					mapASerialiser.getLesCases()[i][j].getObjet().setMoveDelay(this.lesCases[i][j].getMoveDelay());
				}
			}
			
		}
		
		// on envoi tout ca a la classe Parseuse qui se demerdera
		ParserSax.SerialiseThisMap(mapASerialiser);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.getSource());
		
	}

	/*
	 *  IL FAUT rendre ces evenements unique au panel de dessin svp
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		for(int j=0; j<this.nbCaseH;j++){
			for(int i=0;i<Map.getNbCaseL();i++){
				if(e.getSource() == this.lesCases[i][j].getButton()){
					// ACTION au moment du clique sur une case
					this.assigneNouvelleImage(this.lesCases[i][j]);
				}
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(this.mousePressed){
			for(int j=0; j<this.nbCaseH;j++){
				for(int i=0;i<Map.getNbCaseL();i++){
					if(e.getSource() == this.lesCases[i][j].getButton()){
						// ACTION au moment du clique sur une case
						this.assigneNouvelleImage(this.lesCases[i][j]);
					}
				}
			}
		}
	}

	
	
	
	
	// Assigne une nouvelle image a la case
	public void assigneNouvelleImage(EditeurCase editCase){
		// donne l'image
		editCase.getButton().setIcon(this.editImage.get(this.select.getText()+""));
		// donne le nom
		editCase.setImgName(this.select.getText());
		// dit si cest une imgae objet ou non
		editCase.setObjet(this.isObjet(this.select.getText()));
		// si cest un objet et on a voulue qu'il bouge
		if(this.isObjet(this.select.getText()))
			if(tglbtnBouge.isSelected()){
				
				// on recupere les information sur le deplacement voulu
				int nombre_case_deplacement = 1;
				int sens_deplacement = 1;
				int moveDelay = 1000;
				boolean nbTotal = true;
				do{
					try{
						nbTotal=true;
						nombre_case_deplacement = Integer.parseInt(JOptionPane.showInputDialog(this, "Donnez le nombre total de case de deplacement", 1));
						
						boolean sensDep = true;
						do{
							try{
								sensDep = true;
								sens_deplacement = Integer.parseInt(JOptionPane.showInputDialog(this, "Donnez le sens de deplacement initial (droite : 1/rien : 0/gauche : -1)", 1));
								
								boolean moveDel = true;
								do{
									try{
										moveDel = true;
										moveDelay = Integer.parseInt(JOptionPane.showInputDialog(this, "Donnez le temps en milliseconde de deplacement de l'objet (cae par case)", 1000));
									}catch(Exception e){
										moveDel = false;
									}
								}while(!moveDel);
							}catch(Exception e){
								sensDep = false;
							}						
						}while(!sensDep);
					}catch(Exception e){
						nbTotal = false;
					}
				}while(!nbTotal);
				
				// on donne les infos trouve a la case
				editCase.setNombre_case_deplacement(nombre_case_deplacement);
				editCase.setSens_deplacement(sens_deplacement);
				editCase.setMoveDelay(moveDelay);
				
				// on colori larriere de la case pour la reperer
				editCase.getButton().setBackground(Color.RED);
				System.out.println(sens_deplacement);
			}
	}
	
	
	
	
	// test si le string est un objet ou non
	public boolean isObjet(String str){
		switch(str){
			case "tronc" : case "barriere_bois" : return true;
			
			default : return false;
		}
	}
	
	
	
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mousePressed = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.mousePressed = false;
	}
}
