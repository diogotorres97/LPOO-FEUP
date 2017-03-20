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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class GUI{

	protected JFrame frmDungeonKeep;
	protected JButton btnLeft=null, btnRight=null, btnUp=null, btnDown=null, btnValidate=null, btnBackMenu2=null;
	protected JButton btnNewGame=null,btnExit=null, btnBackMenu=null,btnHelp=null, btnGameEditor=null , btnGetOptions=null, btnGame=null;
	protected JLabel lblTitle=null,lblGameStatus=null, lblNumOgres=null, lblGuardPers=null, lblNumCols=null, lblNumLines=null, lblObjects=null;	
	protected JPanel  panelShowGame=null,panelShowEditor=null, panelMoves=null, PanelOtherButtons=null,panelGame=null, panelMenu=null, panelHelp=null, panelEditor=null; 
	protected OptionsDialogGUI options;
	protected JTextField txtNumOgres=null;
	protected JComboBox<String> cmbGuardPers=null;
	protected JSpinner spnNumLines=null, spnNumCols=null;

	protected Game g;
	protected int level=0;
	protected int maxLevel = 1;  //PASS TO CONSTANT
	protected int xSelected=-1, ySelected=-1; //position of the object to be eliminated
	protected int[] wallPos, eliminatePos, ogrePos, leverPos, doorPos;
	protected KeepMap mapForEdit, mapEditCopy;


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

	protected boolean checkObjectReleasedInShowEditorPanel(int x, int y) {

		if((x>=panelShowEditor.getX() && x<=(panelShowEditor.getWidth()+panelShowEditor.getX())) && (y>=panelShowEditor.getY() && y<=(panelShowEditor.getHeight()+panelShowEditor.getY())))
			return true;
		else
			return false;
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
		frmDungeonKeep.setResizable(false);
		frmDungeonKeep.setTitle("Dungeon Keep");
		frmDungeonKeep.setBounds(100, 100, 1200, 800);
		frmDungeonKeep.setLocationRelativeTo(null);
		frmDungeonKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeep.getContentPane().setLayout(null);

		options=new OptionsDialogGUI(this);
		options.setLocationRelativeTo(frmDungeonKeep);

		panelShowGame = new ShowGamePanel(this);
		panelShowGame.setBounds(25,135,500,500);
		panelShowGame.requestFocusInWindow(); 

		panelShowEditor = new ShowEditorPanel(this);



		Image imgWall=new ImageIcon(this.getClass().getResource("/wall.png")).getImage();
		Image imgOgre=new ImageIcon(this.getClass().getResource("/ogre.png")).getImage();
		Image imgLever=new ImageIcon(this.getClass().getResource("/lever.png")).getImage();
		Image imgDoor=new ImageIcon(this.getClass().getResource("/closed_door.png")).getImage();
		Image imgEliminate=new ImageIcon(this.getClass().getResource("/X.png")).getImage();

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

		panelEditor = new JPanel();
		panelEditor.setVisible(false);
		panelEditor.setBounds(97, 0, 1000, 800);
		frmDungeonKeep.getContentPane().add(panelEditor);
		panelEditor.setLayout(null);

		lblNumLines = new JLabel("Number of lines:");
		lblNumLines.setBounds(10, 11, 119, 14);
		panelEditor.add(lblNumLines);

		lblNumCols = new JLabel("Number of columns:");
		lblNumCols.setBounds(10, 48, 119, 14);
		panelEditor.add(lblNumCols);

		lblObjects = new JLabel("OBJECTS");
		lblObjects.setBounds(346, 44, 85, 14);
		panelEditor.add(lblObjects);

		spnNumLines = new JSpinner();
		spnNumLines.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mapForEdit.resizeMap((Integer)spnNumCols.getValue(), (Integer)spnNumLines.getValue());
				panelShowEditor.setBounds(20,135, (Integer)spnNumCols.getValue()*50, (Integer)spnNumLines.getValue()*50);
				panelShowEditor.repaint();
			}
		});
		spnNumLines.setModel(new SpinnerNumberModel(9, 5, 12, 1));
		spnNumLines.setBounds(159, 11, 40, 20);			
		panelEditor.add(spnNumLines);

		spnNumCols = new JSpinner();
		spnNumCols.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mapForEdit.resizeMap((Integer)spnNumCols.getValue(), (Integer)spnNumLines.getValue());
				panelShowEditor.setBounds(20,135, (Integer)spnNumCols.getValue()*50, (Integer)spnNumLines.getValue()*50);
				panelShowEditor.repaint();
			}
		});
		spnNumCols.setModel(new SpinnerNumberModel(9, 5, 16, 1));
		spnNumCols.setBounds(159, 45, 40, 20);
		panelEditor.add(spnNumCols);

		JLabel iconWall = new JLabel("");
		iconWall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(wallPos[2], wallPos[3])){
					((ShowEditorPanel) panelShowEditor).placeUnitInMap(wallPos[2], wallPos[3], 'X');  

				}
				panelShowEditor.repaint();


				wallPos[2]=wallPos[0];
				wallPos[3]=wallPos[1];
				iconWall.setBounds(wallPos[0], wallPos[1], 50, 50);

			}
		});
		iconWall.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconWall.setBounds(wallPos[2]+e.getX(), wallPos[3]+e.getY(), 50,50);
				wallPos[2]+=e.getX();
				wallPos[3]+=e.getY();
			}

		});
		iconWall.setIcon(new ImageIcon(imgWall));
		iconWall.setBounds(420, 44, 50, 50);
		wallPos=new int[]{iconWall.getX(), iconWall.getY(), iconWall.getX(), iconWall.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		panelEditor.add(iconWall);

		JLabel iconOgre = new JLabel("");
		iconOgre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(ogrePos[2], ogrePos[3])){
					((ShowEditorPanel) panelShowEditor).placeUnitInMap(ogrePos[2], ogrePos[3], 'O');  

				}
				panelShowEditor.repaint();


				ogrePos[2]=ogrePos[0];
				ogrePos[3]=ogrePos[1];
				iconOgre.setBounds(ogrePos[0], ogrePos[1], 50, 50);

			}
		});
		iconOgre.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconOgre.setBounds(ogrePos[2]+e.getX(), ogrePos[3]+e.getY(), 50,50);
				ogrePos[2]+=e.getX();
				ogrePos[3]+=e.getY();
			}

		}); 	
		iconOgre.setIcon(new ImageIcon(imgOgre));
		iconOgre.setBounds(475, 44, 50, 50);
		ogrePos=new int[]{iconOgre.getX(), iconOgre.getY(), iconOgre.getX(), iconOgre.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		panelEditor.add(iconOgre);

		JLabel iconLever = new JLabel("");
		iconLever.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(leverPos[2], leverPos[3])){
					((ShowEditorPanel) panelShowEditor).placeUnitInMap(leverPos[2], leverPos[3], 'k');  

				}
				panelShowEditor.repaint();


				leverPos[2]=leverPos[0];
				leverPos[3]=leverPos[1];
				iconLever.setBounds(leverPos[0], leverPos[1], 50, 50);

			}
		});
		iconLever.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconLever.setBounds(leverPos[2]+e.getX(), leverPos[3]+e.getY(), 50,50);
				leverPos[2]+=e.getX();
				leverPos[3]+=e.getY();
			}

		}); 	
		iconLever.setIcon(new ImageIcon(imgLever));
		iconLever.setBounds(530, 44, 50, 50);
		leverPos=new int[]{iconLever.getX(), iconLever.getY(), iconLever.getX(), iconLever.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		panelEditor.add(iconLever);

		JLabel iconDoor = new JLabel("");
		iconDoor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(doorPos[2], doorPos[3])){
					((ShowEditorPanel) panelShowEditor).placeUnitInMap(doorPos[2], doorPos[3], 'I');  

				}
				panelShowEditor.repaint();


				doorPos[2]=doorPos[0];
				doorPos[3]=doorPos[1];
				iconDoor.setBounds(doorPos[0], doorPos[1], 50, 50);

			}
		});
		iconDoor.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconDoor.setBounds(doorPos[2]+e.getX(), doorPos[3]+e.getY(), 50,50);
				doorPos[2]+=e.getX();
				doorPos[3]+=e.getY();
			}

		}); 	
		iconDoor.setIcon(new ImageIcon(imgDoor));
		iconDoor.setBounds(597, 44, 50, 50);
		doorPos=new int[]{iconDoor.getX(), iconDoor.getY(), iconDoor.getX(), iconDoor.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		panelEditor.add(iconDoor);

		JLabel iconEliminate = new JLabel("");
		iconEliminate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(eliminatePos[2], eliminatePos[3])){
					((ShowEditorPanel) panelShowEditor).eliminateUnitInMap(eliminatePos[2], eliminatePos[3]);  

				}
				panelShowEditor.repaint();


				eliminatePos[2]=eliminatePos[0];
				eliminatePos[3]=eliminatePos[1];
				iconEliminate.setBounds(eliminatePos[0], eliminatePos[1], 50, 50);

			}
		});
		iconEliminate.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconEliminate.setBounds(eliminatePos[2]+e.getX(), eliminatePos[3]+e.getY(), 50,50);
				eliminatePos[2]+=e.getX();
				eliminatePos[3]+=e.getY();
			}

		});
		iconEliminate.setIcon(new ImageIcon(imgEliminate));
		iconEliminate.setBounds(792, 44, 50, 50);
		eliminatePos=new int[]{iconEliminate.getX(), iconEliminate.getY(), iconEliminate.getX(), iconEliminate.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		panelEditor.add(iconEliminate);

		btnBackMenu2 = new JButton("Back to Menu");
		btnBackMenu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelEditor.setVisible(false);
				panelShowEditor.setVisible(false);
				panelMenu.setVisible(true);
				spnNumCols.setValue(mapEditCopy.getMap()[0].length);
				spnNumLines.setValue(mapEditCopy.getMap().length);
				mapForEdit.copyMap(mapEditCopy);
			}
		});
		btnBackMenu2.setBounds(828, 139, 125, 50);
		panelEditor.add(btnBackMenu2);

		btnValidate = new JButton("Validate");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String verification=((ShowEditorPanel) panelShowEditor).isValidMap();
				if(verification!=null){
					JOptionPane.showMessageDialog(null, verification);
				}else{
					panelEditor.setVisible(false);
					panelShowEditor.setVisible(false);
					panelMenu.setVisible(true);
				}
				lblNumCols.setText(""+mapForEdit.getHeroPos()[0]+" "+mapForEdit.getHeroPos()[1]);
			}
		});
		btnValidate.setBounds(828, 231, 125, 50);
		panelEditor.add(btnValidate);


		panelMenu = new JPanel();
		panelMenu.setBounds(97, 0, 1000, 800);
		frmDungeonKeep.getContentPane().add(panelMenu);
		panelMenu.setLayout(null);

		btnGame = new JButton("New Game");
		btnGame.setFocusPainted(false);
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setVisible(true);
			}
		});
		btnGame.setBounds(411, 96, 177, 80);
		panelMenu.add(btnGame);

		btnGameEditor = new JButton("Game Editor");
		btnGameEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(mapForEdit==null)
					mapForEdit=new KeepMap();

				mapEditCopy=new KeepMap();
				mapEditCopy.copyMap(mapForEdit);

				mapForEdit.setMap(mapForEdit.getHeroPos()[0], mapForEdit.getHeroPos()[1], 'A');
				for(int i=0;i<mapForEdit.getNumUnit('O');i++){
					mapForEdit.setMap(mapForEdit.getOgrePos()[i][0], mapForEdit.getOgrePos()[i][1], 'O');
				}

				panelEditor.setVisible(true);
				panelMenu.setVisible(false);
				panelEditor.add(panelShowEditor);

				panelShowEditor.setBounds(20,135, (Integer)spnNumCols.getValue()*50, (Integer)spnNumLines.getValue()*50);
				panelShowEditor.setVisible(true);
				panelShowEditor.repaint();

			}
		});
		btnGameEditor.setFocusPainted(false);
		btnGameEditor.setBounds(411, 272, 177, 80);
		panelMenu.add(btnGameEditor);

		btnHelp = new JButton("Help");
		btnHelp.setFocusPainted(false);
		btnHelp.setBounds(411, 448, 177, 80);
		panelMenu.add(btnHelp);

		btnExit= new JButton("Exit");
		btnExit.setFocusPainted(false);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(411, 624, 177, 80);
		panelMenu.add(btnExit);
		panelGame.setBounds(0, 0, 1200, 800);
		frmDungeonKeep.getContentPane().add(panelGame);
		panelGame.setLayout(null);

		lblGameStatus = new JLabel("You can start a new game.");
		lblGameStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGameStatus.setBounds(10, 730, 300, 35);
		panelGame.add(lblGameStatus);

		panelMoves = new JPanel();
		panelMoves.setBounds(876, 286, 263, 87);
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
				int currentLevel=level;
				level=g.update('a', level);
				if(currentLevel!=level){
					panelShowGame.setBounds(25,135,g.getGameMap(1).length*50,g.getGameMap(1)[0].length*50);
				}
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
		PanelOtherButtons.setBounds(890, 66, 227, 178);
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

				if(mapForEdit!=null)
					g.setMap(1, mapForEdit.getMap());

				enableMoveButtons();

				lblGameStatus.setText("Use the key buttons to move the Hero!");

				//txtShowGame.setText(drawGame());
			}
		});
		panelHelp.setBounds(97, 0, 1000, 800);
		frmDungeonKeep.getContentPane().add(panelHelp);
		panelHelp.setLayout(null);




	}
}


