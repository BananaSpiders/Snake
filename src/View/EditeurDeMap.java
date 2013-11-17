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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Button;
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

import javax.swing.SwingConstants;

import Model.Map;
/**
 *  Class Fenetre de l'editeur de map (a developper)
 */
public class EditeurDeMap extends JFrame implements ActionListener,MouseListener {
	/**
	 *  ATTRIBUTS
	 */
	private JTextField textField;
	private JButton[][] lesCases;
	private Map map;
	private boolean mousePressed = false;
	private int POS_X_DRAW = 357;
	private JTextField textField_1;
	private HashMap<String, ImageIcon> editImage;
	private JTextField select;

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
		this.editImage = new HashMap<String, ImageIcon>();
		this.loadImage();
		
		this.map = new Map();
		setResizable(false);
		setTitle("Editeur de map");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 800, 600);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 293, 387);
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
		button.setBounds(10, 129, 40, 25);
		panel.add(button);
		
		// but herbe
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select.setText("herbe");
			}
		});
		button_1.setIcon(this.editImage.get("herbe"));
		button_1.setBounds(10, 93, 40, 25);
		panel.add(button_1);
		
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

		
		this.lesCases = new JButton[Map.getNbCaseL()][Map.getNbCaseH()];
		
        for(int j=0; j<Map.getNbCaseH();j++){
        	for(int i=0;i<Map.getNbCaseL();i++){
        		this.lesCases[i][j] = new JButton(this.editImage.get("herbe"));
        		this.lesCases[i][j].setPreferredSize(new Dimension(20,20));
        		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        		gbc_btnNewButton.insets = new Insets(0, 0, 0, 0);
        		gbc_btnNewButton.gridx = i;
        		gbc_btnNewButton.gridy = j;
        		gbc_btnNewButton.weightx = 0;
        		panel_1.add(this.lesCases[i][j], gbc_btnNewButton);
        		this.lesCases[i][j].addActionListener(this);
        		this.lesCases[i][j].addMouseListener(this);
        	}
        }

        
	}

	public void loadImage(){
		this.editImage.put("herbe", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/herbe.gif")));
		this.editImage.put("fleure", new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/fleure.gif")));
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
		for(int j=0; j<Map.getNbCaseH();j++){
			for(int i=0;i<Map.getNbCaseL();i++){
				if(e.getSource() == this.lesCases[i][j]){
					// ACTION au moment du clique sur une case
					this.lesCases[i][j].setIcon(this.editImage.get(this.select.getText()+""));
					
				}
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(this.mousePressed){
			for(int j=0; j<Map.getNbCaseH();j++){
				for(int i=0;i<Map.getNbCaseL();i++){
					if(e.getSource() == this.lesCases[i][j]){
						// ACTION au moment du clique sur une case
						this.lesCases[i][j].setIcon(this.editImage.get(this.select.getText()+""));
						
					}
				}
			}
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
