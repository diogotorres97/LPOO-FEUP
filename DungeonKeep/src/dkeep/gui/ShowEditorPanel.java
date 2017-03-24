package dkeep.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ShowEditorPanel extends JPanel implements MouseListener, MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage armedHeroImg=null;
	private BufferedImage ogreImg=null;
	private BufferedImage tileImg=null;
	private BufferedImage doorImg=null;
	private BufferedImage wallImg=null;
	private BufferedImage leverImg=null;

	private int xSelected, ySelected;
	private boolean objectSelected=false;
	private boolean cantBeOnPerimeter=false;

	private PanelEditor pe;

	private HashMap<Character, BufferedImage> CHAR_IMGS=new HashMap<Character, BufferedImage>();

	/**
	 * @brief Constructor
	 */
	public ShowEditorPanel(PanelEditor pe) {
		this.pe=pe;

		try {
			tileImg=ImageIO.read(new File("imgs/tile.png"));
			doorImg=ImageIO.read(new File("imgs/closed_door.png"));
			leverImg=ImageIO.read(new File("imgs/lever.png"));
			wallImg=ImageIO.read(new File("imgs/wall.png"));
			ogreImg=ImageIO.read(new File("imgs/ogre.png"));
			armedHeroImg=ImageIO.read(new File("imgs/armed_hero.png"));

		} catch (IOException e) {

		}

		addMouseListener(this);
		addMouseMotionListener(this); 

	}
	/**
	 * @brief Fills the hashMap with the values
	 */
	private void fillHashMap(){
		CHAR_IMGS.put('X', wallImg);
		CHAR_IMGS.put(' ', tileImg);
		CHAR_IMGS.put('O', ogreImg);
		CHAR_IMGS.put('A', armedHeroImg);
		CHAR_IMGS.put('k', leverImg);
		CHAR_IMGS.put('I', doorImg);

	}
	/**
	 * @brief Converts pixel coordinates to x and y iny char[][]
	 * @param x
	 * @param y
	 * @return
	 */
	private int[] convertCoordinatesToCells(int x, int y){
		int[] res=new int[2];
		res[0]=x/(getWidth()/pe.mapForEdit.getMap()[0].length);
		res[1]=y/(getHeight()/pe.mapForEdit.getMap().length);

		return res;

	}
	/**
	 * @brief Sets map char into the new one
	 * @param x
	 * @param y
	 * @param unit
	 */
	public void placeUnitInMap(int x, int y, char unit){
		int[] cells=convertCoordinatesToCells(x-getX(), y-getY());
		if(pe.mapForEdit.isFree(cells[1], cells[0])){
			if(!(unit=='O' && pe.mapForEdit.getNumUnit('O')==5)){
				pe.mapForEdit.setMap(cells[1], cells[0], unit);
				pe.mapForEdit.setNumUnit(unit, 1);

			}
		}

	}
	/**
	 * @brief Eliminates char from map
	 * @param x
	 * @param y
	 */
	public void eliminateUnitInMap(int x, int y){
		int[] cells=convertCoordinatesToCells(x-getX(), y-getY());
		char unit=pe.mapForEdit.getMap()[cells[1]][cells[0]];
		if(!(pe.mapForEdit.isFree(cells[1], cells[0])) && (unit!='A')){
			if(pe.mapForEdit.getNumUnit(unit)>1){
				pe.mapForEdit.setMap(cells[1], cells[0], ' ');
				pe.mapForEdit.setNumUnit(unit, -1);
			}

		}
	}
	/**
	 * @brief Overrides paint component, that draws images according to the chars present in the map
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		fillHashMap();
		char [][] drawMap = pe.mapForEdit.getMap();

		for(int i=0; i< drawMap.length;i++){
			for(int j=0;j< drawMap[i].length;j++){
				int posX=j*50;
				int posY= i*50;

				g.drawImage(CHAR_IMGS.get(' '), posX, posY, null);
				g.drawImage(CHAR_IMGS.get(drawMap[i][j]), posX, posY,  null);

			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}
	/**
	 * @brief Implements MouseClicked Listener
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		int[] cells=convertCoordinatesToCells(e.getX(), e.getY());

		if(pe.mapForEdit.isFree(cells[1], cells[0])){
			if(objectSelected && !(cantBeOnPerimeter && posOnPerimeter(cells[0], cells[1]))){
				char unit=pe.mapForEdit.getMap()[ySelected][xSelected];
				pe.mapForEdit.setMap(cells[1], cells[0], unit);
				pe.mapForEdit.setMap(ySelected, xSelected, ' ');
				objectSelected=false;
				repaint();

			}
		}else{
			char unit=pe.mapForEdit.getMap()[cells[1]][cells[0]];

			if(unit!='X' && unit!='I')
				cantBeOnPerimeter=true;
			else
				cantBeOnPerimeter=false;
			xSelected=cells[0];
			ySelected=cells[1];
			objectSelected=true;

		}


	}
	/**
	 * @brief Implements MousePressed Listener
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int[] cells=convertCoordinatesToCells(e.getX(), e.getY());

		if(pe.mapForEdit.isFree(cells[1], cells[0])){
			if(objectSelected && !(cantBeOnPerimeter && posOnPerimeter(cells[0], cells[1]))){
				char unit=pe.mapForEdit.getMap()[ySelected][xSelected];
				pe.mapForEdit.setMap(cells[1], cells[0], unit);
				pe.mapForEdit.setMap(ySelected, xSelected, ' ');
				objectSelected=false;
				repaint();

			}
		}else{
			char unit=pe.mapForEdit.getMap()[cells[1]][cells[0]];

			if(unit!='X' && unit!='I')
				cantBeOnPerimeter=true;
			else
				cantBeOnPerimeter=false;
			xSelected=cells[0];
			ySelected=cells[1];
			objectSelected=true;

		}

	}
	/**
	 * @brief Implements MouseReleased Listener
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int[] cells=convertCoordinatesToCells(e.getX(), e.getY());
		if(pe.mapForEdit.isFree(cells[1], cells[0])){
			if(objectSelected && !(cantBeOnPerimeter && posOnPerimeter(cells[0], cells[1]))){
				char unit=pe.mapForEdit.getMap()[ySelected][xSelected];
				pe.mapForEdit.setMap(cells[1], cells[0], unit);
				pe.mapForEdit.setMap(ySelected, xSelected, ' ');
				objectSelected=false;
				repaint();

			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	/**
	 * @brief Checks if a certain position is located in the perimeter of the map 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean posOnPerimeter(int x, int y){
		if((y!=0 && y!= pe.mapForEdit.getMap().length-1) && (x!=0 && x!=pe.mapForEdit.getMap()[0].length)){
			return false;
		}
		return true;
	}
	/**
	 * @brief Checks if the perimeter is completed
	 * @return
	 */
	public boolean checkPerimeter(){
		for(int i=0;i<pe.mapForEdit.getMap().length;i++){
			if(i==0 || i== pe.mapForEdit.getMap().length-1){
				for(int j=0;j<pe.mapForEdit.getMap()[0].length;j++)
					if(pe.mapForEdit.getMap()[i][j]==' ')
						return false;
			}else if(pe.mapForEdit.getMap()[i][0]==' ' || pe.mapForEdit.getMap()[i][pe.mapForEdit.getMap()[0].length-1]==' ')
				return false;

		}
		return true;
	}
	/**
	 * @brief Verifies if the map is valid
	 * @return
	 */
	public String isValidMap() {

		String res="";

		if(!checkPerimeter())
			res="Perimeter of the map not completed.\nInsert walls or doors to create a valid map!\n";
		else{
			int [] pos = pe.mapForEdit.getHeroPos();

			ValidateEditorMap.initializeVisited(pe.mapForEdit.getMap());
			if(!(ValidateEditorMap.findGoal(pe.mapForEdit.getMap(),pos[1], pos[0], 'k')))
				res+="This map isn't valid because the hero cannot reach the key!\n";

			ValidateEditorMap.initializeVisited(pe.mapForEdit.getMap());
			if(!(ValidateEditorMap.findGoal(pe.mapForEdit.getMap(),pos[1], pos[0], 'I')))
				res+="This map isn't valid because the hero cannot end the game!\n";

			if(!(ValidateEditorMap.ogreHasValidMoves(pe.mapForEdit,pe.mapForEdit.getOgrePos())))
				res+="This map isn't valid because one or more ogres are blocked!\n";
		}


		return res;
	}

}
