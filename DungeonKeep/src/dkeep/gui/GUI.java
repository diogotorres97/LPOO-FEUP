package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class GUI{

	protected JFrame frmDungeonKeep;

	protected JButton btnExit=null, btnHelp=null, btnGameEditor=null , btnGame=null, btnLoadGame=null;

	protected JLabel lblBackground=null;
	protected JPanel  panelGame=null, panelMenu=null, panelHelp=null, panelEditor=null; 
	protected OptionsDialogGUI options;
	Image imgBackground=null;
	protected BufferedImage bufBackgroundImg;

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
	 * @brief Initiates the image of the background
	 */
	private void imgInit(){
		try{
			bufBackgroundImg=ImageIO.read(new File ("imgs/menu.png"));

		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());

		}
		imgBackground=new ImageIcon(bufBackgroundImg).getImage();
	}
	/**
	 * @brief Initiates the panels
	 */
	private void panelInit(){
		panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 1200, 800);
		frmDungeonKeep.getContentPane().add(panelMenu);
		panelMenu.setLayout(null);

		panelGame = new PanelGame(this);

		panelGame.setBounds(0, 0, 1200, 800);
		frmDungeonKeep.getContentPane().add(panelGame);

		panelEditor = new PanelEditor(this);

		panelEditor.setBounds(0, 0, 1200, 800);
		frmDungeonKeep.getContentPane().add(panelEditor);
	}
	/**
	 * @brief Initiates btnGame and its ActionListener
	 */
	private void btnGameInit(){
		btnGame = new JButton("New Game");
		btnGame.setFocusPainted(false);
		btnGame.setBounds(511, 96, 177, 80);
		panelMenu.add(btnGame);
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setVisible(true);

			}
		});
	}
	/**
	 * @brief Initiates btnEditor and its ActionListener
	 */
	private void btnEditorInit(){
		btnGameEditor = new JButton("Game Editor");
		btnGameEditor.setFocusPainted(false);
		btnGameEditor.setBounds(511, 448, 177, 80);
		panelMenu.add(btnGameEditor);
		btnGameEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelEditor.setVisible(true);
				panelMenu.setVisible(false);
				((PanelEditor) panelEditor).newEdit();

			}
		});
	}
	/**
	 * @brief Initiates btnExit and its ActionListener
	 */
	private void btnExitInit(){
		btnExit= new JButton("Exit");
		btnExit.setFocusPainted(false);
		btnExit.setBounds(511, 624, 177, 80);
		panelMenu.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	/**
	 * @brief Initiates btnLoadGame and its ActionListener
	 */
	private void btnLoadInit(){
		btnLoadGame = new JButton("Load Game");
		btnLoadGame.setFocusPainted(false);
		btnLoadGame.setBounds(511, 272, 177, 80);
		panelMenu.add(btnLoadGame);
		btnLoadGame.addActionListener(new ActionListener() {
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
					if(fc.showOpenDialog(choose) == JFileChooser.APPROVE_OPTION){
						path=fc.getSelectedFile().getAbsolutePath();
						file = new File(path);

					}else{
						canceled=true;
						break;
					}
				}while(file.isDirectory());
				if(!canceled){
					((PanelGame) panelGame).newGame(StorageGame.loadGame(file));
					((PanelGame) panelGame).panelShowGame.requestFocusInWindow();
					panelMenu.setVisible(false);
					panelGame.setVisible(true);
					((PanelGame) panelGame).btnSaveGame.setEnabled(true);
				}
			}
		});
	}
	/**
	 * @brief Initiates lblUnit
	 */
	private void lblInit(){
		lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, panelMenu.getWidth(), panelMenu.getHeight());
		lblBackground.setIcon(new ImageIcon(imgBackground));
		panelMenu.add(lblBackground);
	}

	/**
	 * @brief Initialize the contents of the frame.
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
		imgInit();
		panelInit();
		btnGameInit(); btnEditorInit(); btnExitInit(); btnLoadInit();
		lblInit();


	}
}


