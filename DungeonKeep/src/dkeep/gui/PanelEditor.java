package dkeep.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dkeep.logic.KeepMap;

public class PanelEditor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel iconWall=null, iconOgre=null, iconLever=null, iconDoor=null, iconEliminate=null;
	protected JLabel lblNumCols=null, lblNumLines=null, lblObjects=null,lblEliminate=null;
	protected JPanel  panelShowEditor=null,panelButtonsEditor=null;
	protected JButton  btnValidate=null, btnBackMenu=null;
	protected JSpinner spnNumLines=null, spnNumCols=null;
	protected BufferedImage bufWallImg=null, bufOgreImg=null, bufLeverImg=null, bufDoorImg=null, bufEliminateImg=null;
	Image imgWall=null,imgOgre=null,imgLever=null,imgDoor=null,imgEliminate=null;


	protected final int CELL_WIDTH=50;

	protected int xSelected=-1, ySelected=-1; //position of the object to be eliminated
	protected int[] wallPos, eliminatePos, ogrePos, leverPos, doorPos;
	protected KeepMap mapForEdit, mapEditCopy;



	private GUI gui;


	/**
	 * @brief Create the panel.
	 */
	public PanelEditor(GUI gui) {
		
		this.gui=gui;
		this.setVisible(false);
		this.setLayout(null);


		initialize();
	}
	/**
	 * @brief Checks if the object is released inside showEditorPanel space
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean checkObjectReleasedInShowEditorPanel(int x, int y) {

		if((x>=panelShowEditor.getX() && x<=(panelShowEditor.getWidth()+panelShowEditor.getX())) && (y>=panelShowEditor.getY() && y<=(panelShowEditor.getHeight()+panelShowEditor.getY())))
			return true;
		else
			return false;
	}
	/**
	 * @brief Creates the Edit Mode variables
	 */
	public void newEdit(){

		if(mapForEdit==null)
			mapForEdit=new KeepMap();

		mapEditCopy=new KeepMap();
		mapEditCopy.copyMap(mapForEdit);

		mapForEdit.setMap(mapForEdit.getHeroPos()[0], mapForEdit.getHeroPos()[1], 'A');
		for(int i=0;i<mapForEdit.getNumUnit('O');i++){
			mapForEdit.setMap(mapForEdit.getOgrePos()[i][0], mapForEdit.getOgrePos()[i][1], 'O');
		}

		add(panelShowEditor);
		panelShowEditor.setBounds(145,135, (Integer)spnNumCols.getValue()*CELL_WIDTH, (Integer)spnNumLines.getValue()*CELL_WIDTH);
		panelButtonsEditor.setBounds(panelShowEditor.getX()+panelShowEditor.getWidth(),panelButtonsEditor.getY(), panelButtonsEditor.getWidth(), panelButtonsEditor.getHeight());
		panelShowEditor.setVisible(true);
		panelShowEditor.repaint();
	}
	/**
	 * @brief Initiates the images
	 */
	public void imagesInit(){
		try {

			bufWallImg = ImageIO.read(new File("imgs/wall.png"));
			bufOgreImg = ImageIO.read(new File("imgs/ogre.png"));
			bufLeverImg = ImageIO.read(new File("imgs/lever.png"));
			bufDoorImg = ImageIO.read(new File("imgs/closed_door.png"));
			bufEliminateImg = ImageIO.read(new File("imgs/X.png"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());

		}
		imgWall=new ImageIcon(bufWallImg).getImage();
		imgOgre=new ImageIcon(bufOgreImg).getImage();
		imgLever=new ImageIcon(bufLeverImg).getImage();
		imgDoor=new ImageIcon(bufDoorImg).getImage();
		imgEliminate=new ImageIcon(bufEliminateImg).getImage();
	}
	/**
	 * @brief Initiates labels
	 */
	public void lblsInit(){

		lblNumLines = new JLabel("Number of lines:");
		lblNumLines.setBounds(145, 11, 139, 14);
		add(lblNumLines);

		lblNumCols = new JLabel("Number of columns:");
		lblNumCols.setBounds(145, 48, 139, 14);
		add(lblNumCols);

		lblObjects = new JLabel("OBJECTS");
		lblObjects.setBounds(481, 44, 85, 14);
		add(lblObjects);

		lblEliminate = new JLabel("ELIMINATE");
		lblEliminate.setBounds(829, 48, 74, 14);
		add(lblEliminate);
	}
	/**
	 * @brief Initiates spinners and their listeners
	 */
	public void spnInit(){
		spnNumLines = new JSpinner();
		spnNumLines.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mapForEdit.resizeMap((Integer)spnNumCols.getValue(), (Integer)spnNumLines.getValue());
				panelShowEditor.setBounds(145,135, (Integer)spnNumCols.getValue()*CELL_WIDTH, (Integer)spnNumLines.getValue()*CELL_WIDTH);
				panelShowEditor.repaint();
			}
		});
		spnNumLines.setModel(new SpinnerNumberModel(9, 5, 12, 1));
		spnNumLines.setBounds(294, 11, 40, 20);			
		add(spnNumLines);

		spnNumCols = new JSpinner();
		spnNumCols.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mapForEdit.resizeMap((Integer)spnNumCols.getValue(), (Integer)spnNumLines.getValue());
				panelShowEditor.setBounds(145,135, (Integer)spnNumCols.getValue()*CELL_WIDTH, (Integer)spnNumLines.getValue()*CELL_WIDTH);
				panelButtonsEditor.setBounds(panelShowEditor.getX()+panelShowEditor.getWidth(),panelButtonsEditor.getY(), panelButtonsEditor.getWidth(), panelButtonsEditor.getHeight());
				panelShowEditor.repaint();
			}
		});
		spnNumCols.setModel(new SpinnerNumberModel(9, 5, 16, 1));
		spnNumCols.setBounds(294, 45, 40, 20);
		add(spnNumCols);
	}
	/**
	 * @brief Initiates iconWall and its MouseListeners
	 */
	public void wallInit(){
		iconWall = new JLabel("");
		iconWall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(wallPos[2], wallPos[3])){
					((ShowEditorPanel) panelShowEditor).placeUnitInMap(wallPos[2], wallPos[3], 'X');  

				}
				panelShowEditor.repaint();
				wallPos[2]=wallPos[0];
				wallPos[3]=wallPos[1];
				iconWall.setBounds(wallPos[0], wallPos[1], CELL_WIDTH, CELL_WIDTH);

			}
		});
		iconWall.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconWall.setBounds(wallPos[2]+e.getX(), wallPos[3]+e.getY(), CELL_WIDTH,CELL_WIDTH);
				wallPos[2]+=e.getX();
				wallPos[3]+=e.getY();
			}

		});
		iconWall.setIcon(new ImageIcon(imgWall));
		iconWall.setBounds(555, 44, CELL_WIDTH, CELL_WIDTH);
		wallPos=new int[]{iconWall.getX(), iconWall.getY(), iconWall.getX(), iconWall.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		add(iconWall);
	}
	/**
	 * @brief Initiates iconOgre and its MouseListeners
	 */
	public void ogreInit(){
		iconOgre = new JLabel("");
		iconOgre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(ogrePos[2], ogrePos[3])){
					((ShowEditorPanel) panelShowEditor).placeUnitInMap(ogrePos[2], ogrePos[3], 'O');  

				}
				panelShowEditor.repaint();
				ogrePos[2]=ogrePos[0];
				ogrePos[3]=ogrePos[1];
				iconOgre.setBounds(ogrePos[0], ogrePos[1], CELL_WIDTH, CELL_WIDTH);

			}
		});
		iconOgre.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconOgre.setBounds(ogrePos[2]+e.getX(), ogrePos[3]+e.getY(), CELL_WIDTH,CELL_WIDTH);
				ogrePos[2]+=e.getX();
				ogrePos[3]+=e.getY();
			}

		}); 	
		iconOgre.setIcon(new ImageIcon(imgOgre));
		iconOgre.setBounds(610, 44, CELL_WIDTH, CELL_WIDTH);
		ogrePos=new int[]{iconOgre.getX(), iconOgre.getY(), iconOgre.getX(), iconOgre.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		add(iconOgre);
	}
	/**
	 * @brief Initiates iconLever and its MouseListeners
	 */
	public void leverInit(){
		iconLever = new JLabel("");
		iconLever.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(leverPos[2], leverPos[3])){
					((ShowEditorPanel) panelShowEditor).placeUnitInMap(leverPos[2], leverPos[3], 'k');  

				}
				panelShowEditor.repaint();
				leverPos[2]=leverPos[0];
				leverPos[3]=leverPos[1];
				iconLever.setBounds(leverPos[0], leverPos[1], CELL_WIDTH, CELL_WIDTH);

			}
		});
		iconLever.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconLever.setBounds(leverPos[2]+e.getX(), leverPos[3]+e.getY(), CELL_WIDTH,CELL_WIDTH);
				leverPos[2]+=e.getX();
				leverPos[3]+=e.getY();
			}

		}); 	
		iconLever.setIcon(new ImageIcon(imgLever));
		iconLever.setBounds(665, 44, CELL_WIDTH, CELL_WIDTH);
		leverPos=new int[]{iconLever.getX(), iconLever.getY(), iconLever.getX(), iconLever.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		add(iconLever);
	}
	/**
	 * @brief Initiates iconDoor and its MouseListeners
	 */
	public void doorInit(){
		iconDoor = new JLabel("");
		iconDoor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(doorPos[2], doorPos[3])){
					((ShowEditorPanel) panelShowEditor).placeUnitInMap(doorPos[2], doorPos[3], 'I');  

				}
				panelShowEditor.repaint();
				doorPos[2]=doorPos[0];
				doorPos[3]=doorPos[1];
				iconDoor.setBounds(doorPos[0], doorPos[1], CELL_WIDTH, CELL_WIDTH);

			}
		});
		iconDoor.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconDoor.setBounds(doorPos[2]+e.getX(), doorPos[3]+e.getY(), CELL_WIDTH,CELL_WIDTH);
				doorPos[2]+=e.getX();
				doorPos[3]+=e.getY();
			}

		}); 	
		iconDoor.setIcon(new ImageIcon(imgDoor));
		iconDoor.setBounds(732, 44, CELL_WIDTH, CELL_WIDTH);
		doorPos=new int[]{iconDoor.getX(), iconDoor.getY(), iconDoor.getX(), iconDoor.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		add(iconDoor);
	}
	/**
	 * @brief Initiates iconEliminate and its MouseListeners
	 */
	public void eliminateInit(){
		iconEliminate = new JLabel("");
		iconEliminate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				if(checkObjectReleasedInShowEditorPanel(eliminatePos[2], eliminatePos[3])){
					((ShowEditorPanel) panelShowEditor).eliminateUnitInMap(eliminatePos[2], eliminatePos[3]);  

				}
				panelShowEditor.repaint();
				eliminatePos[2]=eliminatePos[0];
				eliminatePos[3]=eliminatePos[1];
				iconEliminate.setBounds(eliminatePos[0], eliminatePos[1], CELL_WIDTH, CELL_WIDTH);

			}
		});
		iconEliminate.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				iconEliminate.setBounds(eliminatePos[2]+e.getX(), eliminatePos[3]+e.getY(), CELL_WIDTH,CELL_WIDTH);
				eliminatePos[2]+=e.getX();
				eliminatePos[3]+=e.getY();
			}

		});
		iconEliminate.setIcon(new ImageIcon(imgEliminate));
		iconEliminate.setBounds(927, 44, CELL_WIDTH, CELL_WIDTH);
		eliminatePos=new int[]{iconEliminate.getX(), iconEliminate.getY(), iconEliminate.getX(), iconEliminate.getY()}; //[0,1] -> initial pos, [2,3] -> current pos
		add(iconEliminate);
	}
	/**
	 * @brief Initiates buttons and their ActonListeners
	 */
	public void btnInit(){
		btnBackMenu = new JButton("Back to Menu");
		btnBackMenu.setBounds(29, 20, 167, 40);
		panelButtonsEditor.add(btnBackMenu);

		btnValidate = new JButton("Validate");
		btnValidate.setBounds(29, 104, 167, 40);
		panelButtonsEditor.add(btnValidate);

		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String verification=((ShowEditorPanel) panelShowEditor).isValidMap();
				if(verification!=""){
					JOptionPane.showMessageDialog(null, verification);
				}else{
					setVisible(false);
					panelShowEditor.setVisible(false);
					gui.panelMenu.setVisible(true);
				}

			}
		});

		btnBackMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				panelShowEditor.setVisible(false);
				gui.panelMenu.setVisible(true);
				spnNumCols.setValue(mapEditCopy.getMap()[0].length);
				spnNumLines.setValue(mapEditCopy.getMap().length);
				mapForEdit.copyMap(mapEditCopy);
			}
		});
	}
	/**
	 * @brief Initialize panelEditor components
	 */
	public void initialize(){
		panelButtonsEditor = new JPanel();
		panelButtonsEditor.setBounds(717, 130, 225, 155);
		add(panelButtonsEditor);
		panelButtonsEditor.setLayout(null);

		panelShowEditor = new ShowEditorPanel(this);
		imagesInit();
		lblsInit();
		spnInit();
		wallInit();
		ogreInit();
		leverInit();
		doorInit();
		eliminateInit();
		btnInit();
	}
}
