package dkeep.gui;

import dkeep.logic.*;

import java.awt.Dimension;
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
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class GUI {

	private JFrame frmDungeonKeep;
	private JTextField txtNumOgres;

	Game g;
	int level=0;
	int maxLevel = 1; 

	public String drawGame(){

		String txtToShow="";
		for(int i=0; i< g.getGameMap(level).length;i++){
			for(int j=0;j< g.getGameMap(level)[i].length;j++)
				txtToShow+=g.getGameMap(level)[i][j]+" ";
			txtToShow+='\n';
		}

		return txtToShow;
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
	public GUI() throws IOException {
		initialize();


	}
	
	

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frmDungeonKeep = new JFrame();
		frmDungeonKeep.setTitle("Dungeon Keep");
		frmDungeonKeep.setBounds(100, 100, 675, 500);
		frmDungeonKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeep.getContentPane().setLayout(null);
		
	
		JPanel panel = new ShowGamePanel();
		panel.setBounds(150,150,60,64);
		panel.requestFocusInWindow(); 
		frmDungeonKeep.getContentPane().add(panel);
/*
		JLabel lblNumOgres = new JLabel("Number of Ogres");
		lblNumOgres.setBounds(27, 62, 153, 14);
		frmDungeonKeep.getContentPane().add(lblNumOgres);

		txtNumOgres = new JTextField();
		txtNumOgres.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField textField = (JTextField) e.getSource();

				String text = textField.getText();
				boolean validate=true;
				int num=0;
				try{
					if(validate == true){
						num=Integer.parseInt(text);
						if(num>=1 && num<=5)
							validate=true;
						else{
							validate=false;
						}

					}


				}catch (NumberFormatException n){

					validate=false;
				}
				if(validate==true)
					textField.setText(text);
				else
					textField.setText("1");
			}
		});
		
		txtNumOgres.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumOgres.setText("1");
		txtNumOgres.setBounds(241, 62, 86, 20);
		frmDungeonKeep.getContentPane().add(txtNumOgres);
		txtNumOgres.setColumns(10);

		JLabel lblGuardPers = new JLabel("Guard Personality");
		lblGuardPers.setBounds(27, 102, 153, 14);
		frmDungeonKeep.getContentPane().add(lblGuardPers);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(241, 102, 86, 20);
		comboBox.addItem("Rookie");
		comboBox.addItem("Drunken");
		comboBox.addItem("Suspicious");
		frmDungeonKeep.getContentPane().add(comboBox);

		JTextArea txtShowGame = new JTextArea();
		txtShowGame.setEditable(false);
		txtShowGame.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtShowGame.setBounds(27, 134, 300, 265);
		frmDungeonKeep.getContentPane().add(txtShowGame);

		JLabel lblGameStatus = new JLabel("You can start a new game.");
		lblGameStatus.setBounds(27, 421, 300, 14);
		frmDungeonKeep.getContentPane().add(lblGameStatus);

		JPanel panelMoves = new JPanel();
		panelMoves.setBounds(402, 285, 237, 103);
		frmDungeonKeep.getContentPane().add(panelMoves);



		JButton btnLeft = new JButton("Left");
		panelMoves.add(btnLeft);
		btnLeft.setEnabled(false);

		JButton btnUp = new JButton("Up");
		panelMoves.add(btnUp);
		btnUp.setEnabled(false);

		JButton btnRight = new JButton("Right");
		panelMoves.add(btnRight);
		btnRight.setEnabled(false);

		JButton btnDown = new JButton("Down");
		panelMoves.add(btnDown);
		btnDown.setEnabled(false);

		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('a', level);
				if(level==maxLevel && g.gameWin()){
					lblGameStatus.setText("You win");
					btnRight.setEnabled(false);
					btnDown.setEnabled(false);
					btnUp.setEnabled(false);
					btnLeft.setEnabled(false);
				}else if(g.isGameOver()){
					lblGameStatus.setText("You lose");
					btnRight.setEnabled(false);
					btnDown.setEnabled(false);
					btnUp.setEnabled(false);
					btnLeft.setEnabled(false);

				}

				txtShowGame.setText(drawGame());
			}
		});



		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				level=g.update('w', level);
				if(level==maxLevel && g.gameWin()){
					lblGameStatus.setText("You win");
					btnRight.setEnabled(false);
					btnDown.setEnabled(false);
					btnUp.setEnabled(false);
					btnLeft.setEnabled(false);
				}else if(g.isGameOver()){
					lblGameStatus.setText("You lose");
					btnRight.setEnabled(false);
					btnDown.setEnabled(false);
					btnUp.setEnabled(false);
					btnLeft.setEnabled(false);

				}

				txtShowGame.setText(drawGame());

			}
		});



		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('d', level);
				if(level==maxLevel && g.gameWin()){
					lblGameStatus.setText("You win");
					btnRight.setEnabled(false);
					btnDown.setEnabled(false);
					btnUp.setEnabled(false);
					btnLeft.setEnabled(false);
				}else if(g.isGameOver()){
					lblGameStatus.setText("You lose");
					btnRight.setEnabled(false);
					btnDown.setEnabled(false);
					btnUp.setEnabled(false);
					btnLeft.setEnabled(false);

				}

				txtShowGame.setText(drawGame());
			}
		});

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('s', level);
				if(level==maxLevel && g.gameWin()){
					lblGameStatus.setText("You win");
					btnRight.setEnabled(false);
					btnDown.setEnabled(false);
					btnUp.setEnabled(false);
					btnLeft.setEnabled(false);
				}else if(g.isGameOver()){
					lblGameStatus.setText("You lose");
					btnRight.setEnabled(false);
					btnDown.setEnabled(false);
					btnUp.setEnabled(false);
					btnLeft.setEnabled(false);

				}

				txtShowGame.setText(drawGame());

			}

		});

		JPanel PanelOtherButtons = new JPanel();
		PanelOtherButtons.setBounds(351, 178, 278, 64);
		frmDungeonKeep.getContentPane().add(PanelOtherButtons);


		JButton btnNewGame = new JButton("New Game");
		PanelOtherButtons.add(btnNewGame);


		JButton btnExit = new JButton("Exit");
		PanelOtherButtons.add(btnExit);


		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int level =0;

				g = new Game(level,comboBox.getSelectedIndex(), Integer.parseInt(txtNumOgres.getText()));


				btnUp.setEnabled(true);
				btnDown.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);

				lblGameStatus.setText("Use the key buttons to move the Hero!");

				txtShowGame.setText(drawGame());


			}
		});

*/

	}
}
