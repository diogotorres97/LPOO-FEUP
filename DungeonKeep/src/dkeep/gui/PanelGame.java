package dkeep.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dkeep.logic.Game;

public class PanelGame extends JPanel {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	protected JButton btnLeft=null, btnRight=null, btnUp=null, btnDown=null,btnNewGame=null,btnBackMenu=null,btnGetOptions=null,btnSaveGame=null;
	protected JPanel panelShowGame=null,panelMoves=null, PanelOtherButtons=null;
	protected JLabel lblGameStatus=null, lblNumOgres=null, lblGuardPers=null;
	protected JComboBox<String> cmbGuardPers=null;
	protected JTextField txtNumOgres=null;

	private GUI gui;

	protected final int MAX_LEVEL=1, CELL_WIDTH=50;
	protected Game g;
	protected int level=0;

	/**
	 * Create the panel.
	 */
	public PanelGame(GUI gui) {
		this.gui=gui;
		this.setVisible(false);
		this.setLayout(null);


		initialize();
	}
	/**
	 * @brief Enables Move Buttons 
	 */
	public void enableMoveButtons(){
		btnUp.setEnabled(true);
		btnDown.setEnabled(true);
		btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
	}
	/**
	 * @brief Disables Move Buttons 
	 */
	public void disableMoveButtons(){
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
		requestFocusInWindow();
		panelShowGame.setEnabled(false);
	}
	/**
	 * @brief Changes lblGameStatus text
	 */
	public void changeGameStatus(){
		if(level==MAX_LEVEL && g.gameWin()){
			lblGameStatus.setText("You win");
			disableMoveButtons();
		}else if(g.isGameOver()){
			lblGameStatus.setText("You lose");
			disableMoveButtons();

		}
		panelShowGame.requestFocusInWindow();
		panelShowGame.repaint();

	}
	/**
	 * Initiates a new game and its variables
	 * @param g
	 */
	public void newGame(Game g){
		this.g=g;
		level= g.getLevelGame();
		panelShowGame.setBounds(25,135,g.getCurrentMap().getMap()[0].length*CELL_WIDTH,g.getCurrentMap().getMap().length*CELL_WIDTH);
		add(panelShowGame);
		panelShowGame.setVisible(true);
		panelShowGame.requestFocusInWindow();
		panelShowGame.setEnabled(true);
		panelShowGame.repaint();


		lblGameStatus.setBounds(10, panelShowGame.getY()+panelShowGame.getHeight()+CELL_WIDTH, 300, 35);

		if(((PanelEditor) gui.panelEditor).mapForEdit!=null)
			g.setMap(1, ((PanelEditor) gui.panelEditor).mapForEdit.getMap());

		enableMoveButtons();

		lblGameStatus.setText("Use the key buttons to move the Hero!");
	}
	/**
	 * @brief Initiates labels
	 */
	private void lblInit(){
		lblGameStatus = new JLabel("You can start a new game.");
		lblGameStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGameStatus.setBounds(10, 760, 300, 35);
		add(lblGameStatus);

		lblNumOgres = new JLabel("Number of Ogres");
		lblNumOgres.setBounds(27, 11, 120, 25);
		add(lblNumOgres);

		lblGuardPers = new JLabel("Guard Personality");
		lblGuardPers.setBounds(27, 51, 153, 14);
		add(lblGuardPers);
	}
	/**
	 * @brief Initiates btnLeft and its ActionListener
	 */
	public void btnLeftInit(){
		btnLeft = new JButton("Left");
		btnLeft.setBounds(10, 53, 75, 25);
		panelMoves.add(btnLeft);
		btnLeft.setEnabled(false);

		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentLevel=level;
				level=g.update('a', level);
				if(currentLevel!=level){
					panelShowGame.setBounds(25,135,g.getGameMap(1)[0].length*CELL_WIDTH,g.getGameMap(1).length*CELL_WIDTH);
					lblGameStatus.setBounds(10, panelShowGame.getY()+panelShowGame.getHeight()+CELL_WIDTH, 300, 35);
				}
				changeGameStatus();
			}
		});
	}
	/**
	 * @brief Initiates btnRight and its ActionListener
	 */
	public void btnRightInit(){
		btnRight = new JButton("Right");
		btnRight.setBounds(176, 53, 75, 25);
		panelMoves.add(btnRight);
		btnRight.setEnabled(false);
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentLevel=level;
				level=g.update('d', level);
				if(currentLevel!=level){
					panelShowGame.setBounds(25,135,g.getGameMap(1)[0].length*CELL_WIDTH,g.getGameMap(1).length*CELL_WIDTH);
					lblGameStatus.setBounds(10, panelShowGame.getY()+panelShowGame.getHeight()+CELL_WIDTH, 300, 35);
				}
				changeGameStatus();
			}
		});
	}
	/**
	 * @brief Initiates btnUp and its ActionListener
	 */
	public void btnUpInit(){
		btnUp = new JButton("Up");
		btnUp.setBounds(91, 11, 75, 25);
		panelMoves.add(btnUp);
		btnUp.setEnabled(false);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentLevel=level;
				level=g.update('w', level);
				if(currentLevel!=level){
					panelShowGame.setBounds(25,135,g.getGameMap(1)[0].length*CELL_WIDTH,g.getGameMap(1).length*CELL_WIDTH);
					lblGameStatus.setBounds(10, panelShowGame.getY()+panelShowGame.getHeight()+CELL_WIDTH, 300, 35);
				}
				changeGameStatus();
			}
		});
	}
	/**
	 * @brief Initiates btnDown and its ActionListener
	 */
	public void btnDownInit(){
		btnDown = new JButton("Down");
		btnDown.setBounds(91, 53, 75, 25);
		panelMoves.add(btnDown);
		btnDown.setEnabled(false);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentLevel=level;
				level=g.update('s', level);
				if(currentLevel!=level){
					panelShowGame.setBounds(25,135,g.getGameMap(1)[0].length*CELL_WIDTH,g.getGameMap(1).length*CELL_WIDTH);
					lblGameStatus.setBounds(10, panelShowGame.getY()+panelShowGame.getHeight()+CELL_WIDTH, 300, 35);
				}
				changeGameStatus();
			}
		});
	}
	/**
	 * @brief Initiates btnNewGame and its ActionListener
	 */
	public void btnNewInit(){
		btnNewGame = new JButton("New Game");
		btnNewGame.setFocusPainted(false);
		btnNewGame.setBounds(56, 15, 120, 25);
		PanelOtherButtons.add(btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 0;
				g=new Game(level, cmbGuardPers.getSelectedIndex(), Integer.parseInt(txtNumOgres.getText()));
				newGame(g);
				btnSaveGame.setEnabled(true);
			}
		});
	}
	/**
	 * @brief Initiates btnBackMenu and its ActionListener
	 */
	public void btnBackInit(){
		btnBackMenu = new JButton("Back to Menu");
		btnBackMenu.setFocusPainted(false);
		btnBackMenu.setBounds(56, 135, 120, 25);
		PanelOtherButtons.add(btnBackMenu);
		btnBackMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				panelShowGame.setVisible(false);
				gui.panelMenu.setVisible(true);
				lblGameStatus.setText("You can start a new game.");
				disableMoveButtons();
			}
		});
	}
	/**
	 * @brief Initiates btnSaveGame and its ActionListener
	 */
	public void btnSaveInit(){
		btnSaveGame = new JButton("Save Game");
		btnSaveGame.setFocusPainted(false);
		btnSaveGame.setBounds(56, 95, 120, 25);
		PanelOtherButtons.add(btnSaveGame);
		btnSaveGame.setEnabled(false);
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String path="";
				JButton choose=new JButton();
				JFileChooser fc=new JFileChooser();
				boolean canceled=false;
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Directories");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				File file=null;
				do{
					if(fc.showSaveDialog(choose) == JFileChooser.APPROVE_OPTION){
						path=fc.getSelectedFile().getAbsolutePath();
						file = new File(path);
					}else{
						canceled=true;
						break;
					}

				}while(file.isDirectory());
				if(!canceled){
					StorageGame.storeGame(g,file);
					setVisible(false);
					panelShowGame.setVisible(false);
					gui.panelMenu.setVisible(true);
					lblGameStatus.setText("You can start a new game.");
					disableMoveButtons();
				}
			}
		});
	}
	/**
	 * @brief Initiates btnGetOptions and its ActionListener
	 */
	public void btnOptionsInit(){
		btnGetOptions = new JButton("Choose different values");
		btnGetOptions.setFocusPainted(false);
		btnGetOptions.setBounds(10, 55, 207, 25);
		PanelOtherButtons.add(btnGetOptions);
		btnGetOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.options.setVisible(true);
			}
		});

	}
	/**
	 * @brief Initiates textOgres
	 */
	public void txtOgresInit(){
		txtNumOgres = new JTextField();
		txtNumOgres.setEnabled(false);
		txtNumOgres.setText("1");
		txtNumOgres.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumOgres.setColumns(10);
		txtNumOgres.setBounds(223, 11, 125, 20);
		add(txtNumOgres);
	}
	/**
	 * @brief Initiates cmbGuard
	 */
	public void cmbGuardInit(){
		cmbGuardPers = new JComboBox<String>();
		cmbGuardPers.setEnabled(false);
		cmbGuardPers.addItem("Rookie");
		cmbGuardPers.addItem("Drunken");
		cmbGuardPers.addItem("Suspicious");
		cmbGuardPers.setBounds(223, 51, 125, 20);
		add(cmbGuardPers);
	}
	/**
	 * @brief Initializes panelGame components
	 */
	private void initialize(){

		panelShowGame = new ShowGamePanel(this);
		panelShowGame.setBounds(25,135,500,500);
		panelShowGame.requestFocusInWindow(); 

		panelMoves = new JPanel();
		panelMoves.setBounds(877, 360, 263, 87);
		panelMoves.setLayout(null);
		add(panelMoves);

		PanelOtherButtons = new JPanel();
		PanelOtherButtons.setBounds(891, 140, 227, 178);
		PanelOtherButtons.setLayout(null);
		add(PanelOtherButtons);
		lblInit();
		btnLeftInit(); btnRightInit(); btnUpInit(); btnDownInit();
		btnNewInit(); btnBackInit(); btnSaveInit(); btnOptionsInit();
		txtOgresInit(); cmbGuardInit();
	}

}
