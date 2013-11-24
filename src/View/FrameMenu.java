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

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameMenu extends JFrame {

	private JPanel contentPane;
	private int bg_width;
	private int bg_height;
	private ImageIcon bg_image;
	
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
		btnJouer.setFocusable(false);
		btnJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Lancement du jeu
				new Main();
				dispose();
			}
		});
		btnJouer.setBounds(135, 338, 173, 55);
		contentPane.add(btnJouer);
		
		JLabel label = new JLabel("");
		label.setIcon(bg_image);
		label.setBounds(0, 0, bg_width, bg_height);
		contentPane.add(label);
		this.pack();
	}

}
