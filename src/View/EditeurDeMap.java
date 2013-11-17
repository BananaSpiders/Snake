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
import java.awt.geom.Dimension2D;

import javax.swing.SwingConstants;

import Model.Map;
/**
 *  Class Fenetre de l'editeur de map (a developper)
 */
public class EditeurDeMap extends JFrame {
	/**
	 *  ATTRIBUTS
	 */
	private JTextField textField;
	private JButton[][] lesCases;
	private Map map;

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
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setBounds(10, 11, 46, 14);
		panel.add(lblNom);
		
		textField = new JTextField();
		textField.setBounds(101, 8, 171, 20);
		panel.add(textField);
		textField.setColumns(10);
			
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(357, 11, 425, 549);
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
        		this.lesCases[i][j] = new JButton(new ImageIcon(EditeurDeMap.class.getResource("/images/editeur/herbe.gif")));
        		this.lesCases[i][j].setPreferredSize(new Dimension(20,20));
        		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        		gbc_btnNewButton.insets = new Insets(0, 0, 0, 0);
        		gbc_btnNewButton.gridx = i;
        		gbc_btnNewButton.gridy = j;
        		gbc_btnNewButton.weightx = 0;
        		panel_1.add(this.lesCases[i][j], gbc_btnNewButton);
        		//panel_1.add(this.lesCases[i][j]);
        	}
        }

        
	}
}
