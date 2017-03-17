package dkeep.gui;

import dkeep.logic.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.FlowLayout;
import java.awt.Rectangle;

public class GUI{

	protected JFrame frmDungeonKeep;
	protected JButton btnLeft=null, btnRight=null, btnUp=null, btnDown=null, btnNewGame=null,btnExit=null, btnBackMenu=null,btnHelp=null, btnGameEditor=null , btnGetOptions=null;
	protected JLabel lblGameStatus=null;	
	protected JPanel panelShowGame=null, panelMoves=null, PanelOtherButtons=null,panelGame=null, panelMenu=null, panelHelp=null; 
	protected OptionsDialogGUI options;
	protected Game g;
	protected int level=0;
	protected int maxLevel = 1;  //PASS TO CONSTANT
	public int numOgres, guardPers;



	public String drawGame(){

		String txtToShow="";
		for(int i=0; i< g.getGameMap(level).length;i++){
			for(int j=0;j< g.getGameMap(level)[i].length;j++)
				txtToShow+=g.getGameMap(level)[i][j]+" ";
			txtToShow+='\n';
		}

		return txtToShow;
	}

	public void enableMoveButtons(){
		btnUp.setEnabled(true);
		btnDown.setEnabled(true);
		btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
	}

	public void disableMoveButtons(){
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
		panelGame.requestFocusInWindow();
	}

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
	 * @throws IOException 
	 */
	public GUI() {
		initialize();
	}



	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() {
		options=new OptionsDialogGUI(this);
		frmDungeonKeep = new JFrame();
		frmDungeonKeep.setTitle("Dungeon Keep");
		frmDungeonKeep.setBounds(100, 100, 800, 650);
		frmDungeonKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeep.getContentPane().setLayout(null);

		panelShowGame = new ShowGamePanel(this);
		panelShowGame.setBounds(25,135,300,265);
		panelShowGame.requestFocusInWindow(); 

		/*
		JTextArea txtShowGame = new JTextArea();
		txtShowGame.setEditable(false);
		txtShowGame.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtShowGame.setBounds(27, 134, 300, 265);
		panelGame.add(txtShowGame);
		 */

		panelHelp = new JPanel();

		panelGame = new JPanel();
		panelGame.setVisible(false);

		panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 800, 650);
		frmDungeonKeep.getContentPane().add(panelMenu);
		panelMenu.setLayout(null);

		JButton btnGame = new JButton("New Game");
		btnGame.setFocusPainted(false);
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				Component[] components = PanelOtherButtons.getComponents();
				for(Component comp:components){
					comp.setEnabled(false);
				}
				panelMenu.setVisible(false);
				panelGame.setVisible(true);
				options.setVisible(true);
				
				
				
			}
		});
		btnGame.setBounds(311, 67, 177, 80);
		panelMenu.add(btnGame);

		btnGameEditor = new JButton("Game Editor");
		btnGameEditor.setFocusPainted(false);
		btnGameEditor.setBounds(311, 191, 177, 80);
		panelMenu.add(btnGameEditor);

		btnHelp = new JButton("Help");
		btnHelp.setFocusPainted(false);
		btnHelp.setBounds(311, 335, 177, 80);
		panelMenu.add(btnHelp);

		btnExit= new JButton("Exit");
		btnExit.setFocusPainted(false);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(311, 476, 177, 80);
		panelMenu.add(btnExit);
		panelGame.setBounds(0, 0, 800, 650);
		frmDungeonKeep.getContentPane().add(panelGame);
		panelGame.setLayout(null);

		lblGameStatus = new JLabel("You can start a new game.");
		lblGameStatus.setBounds(27, 421, 300, 14);
		panelGame.add(lblGameStatus);

		panelMoves = new JPanel();
		panelMoves.setBounds(402, 285, 263, 87);
		panelMoves.setLayout(null);
		panelGame.add(panelMoves);

		btnLeft = new JButton("Left");
		btnLeft.setBounds(10, 53, 75, 25);
		panelMoves.add(btnLeft);
		btnLeft.setEnabled(false);

		btnUp = new JButton("Up");
		btnUp.setBounds(91, 11, 75, 25);
		panelMoves.add(btnUp);
		btnUp.setEnabled(false);

		btnRight = new JButton("Right");
		btnRight.setBounds(176, 53, 75, 25);
		panelMoves.add(btnRight);
		btnRight.setEnabled(false);

		btnDown = new JButton("Down");
		btnDown.setBounds(91, 53, 75, 25);
		panelMoves.add(btnDown);
		btnDown.setEnabled(false);


		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('a', level);
				if(level==maxLevel && g.gameWin()){
					lblGameStatus.setText("You win");
					disableMoveButtons();
				}else if(g.isGameOver()){
					lblGameStatus.setText("You lose");
					disableMoveButtons();

				}
				panelShowGame.requestFocusInWindow();
				panelShowGame.repaint();
				//txtShowGame.setText(drawGame());
			}
		});



		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				level=g.update('w', level);
				if(level==maxLevel && g.gameWin()){
					lblGameStatus.setText("You win");
					disableMoveButtons();
				}else if(g.isGameOver()){
					lblGameStatus.setText("You lose");
					disableMoveButtons();

				}
				panelShowGame.requestFocusInWindow();
				panelShowGame.repaint();
				//txtShowGame.setText(drawGame());

			}
		});



		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('d', level);
				if(level==maxLevel && g.gameWin()){
					lblGameStatus.setText("You win");
					disableMoveButtons();
				}else if(g.isGameOver()){
					lblGameStatus.setText("You lose");
					disableMoveButtons();

				}
				panelShowGame.requestFocusInWindow();
				panelShowGame.repaint();
				//txtShowGame.setText(drawGame());
			}
		});

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('s', level);
				if(level==maxLevel && g.gameWin()){
					lblGameStatus.setText("You win");
					disableMoveButtons();
				}else if(g.isGameOver()){
					lblGameStatus.setText("You lose");
					disableMoveButtons();

				}
				panelShowGame.requestFocusInWindow();
				panelShowGame.repaint();
				//txtShowGame.setText(drawGame());

			}

		});

		PanelOtherButtons = new JPanel();
		PanelOtherButtons.setBounds(416, 65, 227, 178);
		panelGame.add(PanelOtherButtons);
		PanelOtherButtons.setLayout(null);


		btnNewGame = new JButton("New Game");
		btnNewGame.setFocusPainted(false);
		btnNewGame.setBounds(56, 11, 120, 25);
		PanelOtherButtons.add(btnNewGame);


		btnBackMenu = new JButton("Back to Menu");
		btnBackMenu.setFocusPainted(false);
		btnBackMenu.setBounds(56, 130, 120, 25);
		PanelOtherButtons.add(btnBackMenu);

		btnGetOptions = new JButton("Choose different values");
		btnGetOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Component[] components = PanelOtherButtons.getComponents();
				for(Component comp:components){
					comp.setEnabled(false);
				}
				
				options.setVisible(true);
			}
		});
		btnGetOptions.setFocusPainted(false);
		btnGetOptions.setBounds(10, 68, 207, 25);
		PanelOtherButtons.add(btnGetOptions);


		btnBackMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelGame.setVisible(false);
				panelShowGame.setVisible(false);
				panelMenu.setVisible(true);
				lblGameStatus.setText("You can start a new game.");
				disableMoveButtons();
			}
		});

		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				level = 0;

				//g = new Game(level,cmbGuardPers.getSelectedIndex(), Integer.parseInt(txtNumOgres.getText()));
				g=new Game(level, guardPers, numOgres);
				panelGame.add(panelShowGame);
				panelShowGame.setVisible(true);
				panelShowGame.requestFocusInWindow();
				panelShowGame.repaint();

				enableMoveButtons();

				lblGameStatus.setText("Use the key buttons to move the Hero!");


				//txtShowGame.setText(drawGame());
			}
		});
		panelHelp.setBounds(0, 0, 800, 650);
		frmDungeonKeep.getContentPane().add(panelHelp);




	}
}
