package dkeep.gui;

import dkeep.logic.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frmDungeonKeep;
	private JTextField txtNumOgres;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmDungeonKeep.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDungeonKeep = new JFrame();
		frmDungeonKeep.setTitle("Dungeon Keep");
		frmDungeonKeep.setBounds(100, 100, 675, 500);
		frmDungeonKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeep.getContentPane().setLayout(null);
		
		JLabel lblNumOgres = new JLabel("Number of Ogres");
		lblNumOgres.setBounds(27, 62, 98, 14);
		frmDungeonKeep.getContentPane().add(lblNumOgres);
		
		txtNumOgres = new JTextField();
		txtNumOgres.setBounds(158, 59, 86, 20);
		frmDungeonKeep.getContentPane().add(txtNumOgres);
		txtNumOgres.setColumns(10);
		
		JLabel lblGuardPers = new JLabel("Guard Personality");
		lblGuardPers.setBounds(27, 102, 98, 14);
		frmDungeonKeep.getContentPane().add(lblGuardPers);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(158, 99, 86, 20);
		frmDungeonKeep.getContentPane().add(comboBox);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnNewGame.setBounds(432, 132, 89, 23);
		frmDungeonKeep.getContentPane().add(btnNewGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(432, 376, 89, 23);
		frmDungeonKeep.getContentPane().add(btnExit);
		
		JTextArea txtShowGame = new JTextArea();
		txtShowGame.setEditable(false);
		txtShowGame.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtShowGame.setBounds(27, 134, 300, 265);
		frmDungeonKeep.getContentPane().add(txtShowGame);
		
		JButton btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUp.setBounds(439, 197, 72, 23);
		frmDungeonKeep.getContentPane().add(btnUp);
		
		JButton btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.setBounds(377, 243, 72, 23);
		frmDungeonKeep.getContentPane().add(btnLeft);
		
		JButton btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.setBounds(510, 243, 72, 23);
		frmDungeonKeep.getContentPane().add(btnRight);
		
		JButton btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.setBounds(439, 292, 72, 23);
		frmDungeonKeep.getContentPane().add(btnDown);
		
		JLabel lblGameStatus = new JLabel("You can start a new game.");
		lblGameStatus.setBounds(27, 421, 300, 14);
		frmDungeonKeep.getContentPane().add(lblGameStatus);
	}
}
