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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class GUI{

	protected JFrame frmDungeonKeep;
	protected JButton btnLeft=null, btnRight=null, btnUp=null, btnDown=null;
	protected JButton btnNewGame=null,btnExit=null, btnBackMenu=null,btnHelp=null, btnGameEditor=null , btnGetOptions=null, btnGame=null;
	protected JLabel lblTitle=null,lblGameStatus=null, lblNumOgres=null, lblGuardPers=null, lblNumCols=null, lblNumLines=null, lblObjects=null;	
	protected JPanel  panelShowGame=null, panelMoves=null, PanelOtherButtons=null,panelGame=null, panelMenu=null, panelHelp=null, panelEditor=null; 
	protected OptionsDialogGUI options;
	protected JTextField txtNumOgres=null;
	protected JComboBox<String> cmbGuardPers=null;
	protected JSpinner spnNumLines=null, spnNumCols=null;

	protected Game g;
	protected int level=0;
	protected int maxLevel = 1;  //PASS TO CONSTANT
	protected int xSelected=-1, ySelected=-1; //position of the object to be eliminated
	protected KeepMap mapForEdit;


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
		panelShowGame.setEnabled(false);
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
		frmDungeonKeep = new JFrame();
		mapForEdit=new KeepMap();

		frmDungeonKeep.setTitle("Dungeon Keep");
		frmDungeonKeep.setBounds(100, 100, 800, 650);
		frmDungeonKeep.setLocationRelativeTo(null);
		frmDungeonKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeep.getContentPane().setLayout(null);

		options=new OptionsDialogGUI(this);
		options.setLocationRelativeTo(frmDungeonKeep);

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
		panelHelp.setVisible(false);

		panelGame = new JPanel();
		panelGame.setVisible(false);

		panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 800, 650);
		frmDungeonKeep.getContentPane().add(panelMenu);
		panelMenu.setLayout(null);

		btnGame = new JButton("New Game");
		btnGame.setFocusPainted(false);
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setVisible(true);
			}
		});
		btnGame.setBounds(311, 67, 177, 80);
		panelMenu.add(btnGame);

		btnGameEditor = new JButton("Game Editor");
		btnGameEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
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
				changeGameStatus();
			}
		});

		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('w', level);
				changeGameStatus();
			}
		});

		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('d', level);
				changeGameStatus();
			}
		});

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level=g.update('s', level);
				changeGameStatus();
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
				options.setVisible(true);
			}
		});
		btnGetOptions.setFocusPainted(false);
		btnGetOptions.setBounds(10, 68, 207, 25);
		PanelOtherButtons.add(btnGetOptions);

		lblNumOgres = new JLabel("Number of Ogres");
		lblNumOgres.setBounds(27, 11, 120, 25);
		panelGame.add(lblNumOgres);

		txtNumOgres = new JTextField();
		txtNumOgres.setEnabled(false);
		txtNumOgres.setText("1");
		txtNumOgres.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumOgres.setColumns(10);
		txtNumOgres.setBounds(223, 11, 125, 20);
		panelGame.add(txtNumOgres);

		cmbGuardPers = new JComboBox<String>();
		cmbGuardPers.setEnabled(false);
		cmbGuardPers.addItem("Rookie");
		cmbGuardPers.addItem("Drunken");
		cmbGuardPers.addItem("Suspicious");
		cmbGuardPers.setBounds(223, 51, 125, 20);
		panelGame.add(cmbGuardPers);

		lblGuardPers = new JLabel("Guard Personality");
		lblGuardPers.setBounds(27, 51, 153, 14);
		panelGame.add(lblGuardPers);

		btnBackMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelGame.setVisible(false);
				panelShowGame.setVisible(false);
				panelEditor.setVisible(false);
				panelMenu.setVisible(true);

				lblGameStatus.setText("You can start a new game.");
				disableMoveButtons();

			}
		});

		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				level = 0;

				g=new Game(level, cmbGuardPers.getSelectedIndex(), Integer.parseInt(txtNumOgres.getText()));
				panelGame.add(panelShowGame);
				panelShowGame.setVisible(true);
				panelShowGame.requestFocusInWindow();
				panelShowGame.setEnabled(true);
				panelShowGame.repaint();

				g.setMap(1, mapForEdit.getMap());

				enableMoveButtons();

				lblGameStatus.setText("Use the key buttons to move the Hero!");

				//txtShowGame.setText(drawGame());
			}
		});
		panelHelp.setBounds(0, 0, 800, 650);
		frmDungeonKeep.getContentPane().add(panelHelp);
		panelHelp.setLayout(null);

		panelEditor = new JPanel();
		panelEditor.setBounds(0, 0, 800, 650);
		frmDungeonKeep.getContentPane().add(panelEditor);
		panelEditor.setLayout(null);

		lblNumLines = new JLabel("Number of lines:");
		lblNumLines.setBounds(10, 11, 119, 14);
		panelEditor.add(lblNumLines);

		lblNumCols = new JLabel("Number of columns:");
		lblNumCols.setBounds(10, 48, 119, 14);
		panelEditor.add(lblNumCols);

		lblObjects = new JLabel("OBJECTS");
		lblObjects.setBounds(348, 11, 85, 14);
		panelEditor.add(lblObjects);

		spnNumLines = new JSpinner();
		spnNumLines.setModel(new SpinnerNumberModel(1, 1, 16, 1));
		spnNumLines.setBounds(159, 11, 40, 20);			
		panelEditor.add(spnNumLines);

		spnNumCols = new JSpinner();
		spnNumCols.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		spnNumCols.setBounds(159, 45, 40, 20);
		panelEditor.add(spnNumCols);

		//Esta imagem está a dar asneira, mesmo com o path correcto!
		Image imgHero=new ImageIcon(this.getClass().getResource("/armed_hero.png")).getImage();
		JLabel iconHero = new JLabel("");
		iconHero.setIcon(new ImageIcon(imgHero));
		iconHero.setBounds(463, 11, 60, 64);
		panelEditor.add(iconHero);

		JButton btnDelObj = new JButton("Delete Object");
		btnDelObj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((xSelected != -1) && (ySelected!=-1)){
					if(mapForEdit.getMap()[ySelected][xSelected]!=' ')
						mapForEdit.setMap(xSelected, ySelected, ' ');
				}
			}
		});
		btnDelObj.setBounds(590, 86, 126, 23);
		panelEditor.add(btnDelObj);

	}

	public void changeGameStatus(){
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
}
