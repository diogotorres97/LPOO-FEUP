package dkeep.gui;

import dkeep.logic.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;

public class GUI{

	protected JFrame frmDungeonKeep;

	protected JButton btnExit=null, btnHelp=null, btnGameEditor=null , btnGame=null, btnLoadGame=null;

	protected JLabel lblBackground=null;
	protected JPanel  panelGame=null, panelMenu=null, panelHelp=null, panelEditor=null; 
	protected OptionsDialogGUI options;

	protected BufferedImage bufBackgroundImg;


	//protected Game g;


	/*
	public String drawGame(){

		String txtToShow="";
		for(int i=0; i< g.getGameMap(level).length;i++){
			for(int j=0;j< g.getGameMap(level)[i].length;j++)
				txtToShow+=g.getGameMap(level)[i][j]+" ";
			txtToShow+='\n';
		}
		return txtToShow;
	}
	 */






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

		
		try{
			bufBackgroundImg=ImageIO.read(new File ("imgs/menu.png"));
			
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());

		}
		Image imgBackground=new ImageIcon(bufBackgroundImg).getImage();
		


		/*
		JTextArea txtShowGame = new JTextArea();
		txtShowGame.setEditable(false);
		txtShowGame.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtShowGame.setBounds(27, 134, 300, 265);
		panelGame.add(txtShowGame);
		 */

		panelHelp = new JPanel();
		panelHelp.setVisible(false);

		

		panelGame = new PanelGame(this);

		panelGame.setBounds(0, 0, 1200, 800);
		frmDungeonKeep.getContentPane().add(panelGame);
		
		panelEditor = new PanelEditor(this);

		panelEditor.setBounds(0, 0, 1200, 800);
		frmDungeonKeep.getContentPane().add(panelEditor);



		panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 1200, 800);
		frmDungeonKeep.getContentPane().add(panelMenu);
		panelMenu.setLayout(null);

		btnGame = new JButton("New Game");
		btnGame.setFocusPainted(false);
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setVisible(true);

			}
		});
		btnGame.setBounds(511, 66, 177, 80);
		panelMenu.add(btnGame);

		btnGameEditor = new JButton("Game Editor");
		btnGameEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				panelEditor.setVisible(true);
				panelMenu.setVisible(false);

				((PanelEditor) panelEditor).newEdit();

			}
		});
		btnGameEditor.setFocusPainted(false);
		btnGameEditor.setBounds(511, 358, 177, 80);
		panelMenu.add(btnGameEditor);

		btnHelp = new JButton("Help");
		btnHelp.setFocusPainted(false);
		btnHelp.setBounds(511, 504, 177, 80);
		panelMenu.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnExit= new JButton("Exit");
		btnExit.setFocusPainted(false);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(511, 650, 177, 80);
		panelMenu.add(btnExit);

		btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*if(g!=null)
							g=StorageGame.loadGame();
				 */

				String path="";
				JButton choose=new JButton();
				JFileChooser fc=new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Directories");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fc.showOpenDialog(choose) == JFileChooser.APPROVE_OPTION){
					path=fc.getSelectedFile().getAbsolutePath();
				}

				/*
				do{
							path=JOptionPane.showInputDialog(
								frmDungeonKeep, 
								"Enter the path of the file",
								"Path of file",JOptionPane.PLAIN_MESSAGE);
								}while(path==null); //METER AKI !ISVALIDFILE(path)   !!!!!!!!!!!!! <-<-<-<--<-<-<-<-<-<-<-<-<-<--<<--<-<-<-<-<-<<-
				 */


				if(path!=""){



					options.setVisible(true);

					((PanelGame) panelGame).newGame(StorageGame.loadGame());

				}
			}
		});
		btnLoadGame.setFocusPainted(false);
		btnLoadGame.setBounds(511, 212, 177, 80);
		panelMenu.add(btnLoadGame);

		lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, panelMenu.getWidth(), panelMenu.getHeight());
		lblBackground.setIcon(new ImageIcon(imgBackground));
		panelMenu.add(lblBackground);

 
		


		panelHelp.setBounds(0, 0, 1200, 800);
		frmDungeonKeep.getContentPane().add(panelHelp);
		panelHelp.setLayout(null);




	}
}


