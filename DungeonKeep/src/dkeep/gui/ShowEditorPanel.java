package dkeep.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

	private GUI gui;

	/**
	 * Create the panel.
	 */
	public ShowEditorPanel(GUI gui) {
		this.gui=gui;

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

	private int[] convertCoordinatesToCells(int x, int y){
		int[] res=new int[2];
		res[0]=x/(getWidth()/gui.mapForEdit.getMap()[0].length);
		res[1]=y/(getHeight()/gui.mapForEdit.getMap().length);

		return res;



	}
	public void placeUnitInMap(int x, int y, char unit){
		int[] cells=convertCoordinatesToCells(x-getX(), y-getY());
		if(gui.mapForEdit.isFree(cells[1], cells[0])){
			if(!(unit=='O' && gui.mapForEdit.getNumUnit('O')==5)){
				gui.mapForEdit.setMap(cells[1], cells[0], unit);
				gui.mapForEdit.setNumUnit(unit, 1);
				gui.lblNumCols.setText(""+ gui.mapForEdit.getNumUnit('O'));
			}
		}

	}


	public void eliminateUnitInMap(int x, int y){
		int[] cells=convertCoordinatesToCells(x-getX(), y-getY());
		char unit=gui.mapForEdit.getMap()[cells[1]][cells[0]];
		if(!(gui.mapForEdit.isFree(cells[1], cells[0])) && (unit!='A')){
			if(gui.mapForEdit.getNumUnit(unit)>1){
				gui.mapForEdit.setMap(cells[1], cells[0], ' ');
				gui.mapForEdit.setNumUnit(unit, -1);
			}

		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		char [][] drawMap = gui.mapForEdit.getMap();

		for(int i=0; i< drawMap.length;i++){
			for(int j=0;j< drawMap[i].length;j++){
				int posX=j*50;
				int posY= i*50;

				g.drawImage(tileImg, posX, posY, null);

				switch (drawMap[i][j]) {
				case 'X':
					g.drawImage(wallImg, posX, posY, null);
					break;
				case 'O':
					g.drawImage(ogreImg, posX, posY, null);
					break;
				case 'I':
					g.drawImage(doorImg, posX, posY, null);
					break;
				case 'A':
					g.drawImage(armedHeroImg, posX, posY, null);
					break;
				case 'k':
					g.drawImage(leverImg, posX, posY, null);
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {



	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int[] cells=convertCoordinatesToCells(e.getX(), e.getY());

		if(gui.mapForEdit.isFree(cells[1], cells[0])){
			if(objectSelected && !(cantBeOnPerimeter && posOnPerimeter(cells[0], cells[1]))){
				char unit=gui.mapForEdit.getMap()[ySelected][xSelected];
				gui.mapForEdit.setMap(cells[1], cells[0], unit);
				gui.mapForEdit.setMap(ySelected, xSelected, ' ');
				objectSelected=false;
				repaint();

			}
		}else{
			char unit=gui.mapForEdit.getMap()[cells[1]][cells[0]];

			if(unit!='X' && unit!='I')
				cantBeOnPerimeter=true;
			else
				cantBeOnPerimeter=false;
			xSelected=cells[0];
			ySelected=cells[1];
			objectSelected=true;

		}


	}

	@Override
	public void mousePressed(MouseEvent e) {
		int[] cells=convertCoordinatesToCells(e.getX(), e.getY());

		if(gui.mapForEdit.isFree(cells[1], cells[0])){
			if(objectSelected && !(cantBeOnPerimeter && posOnPerimeter(cells[0], cells[1]))){
				char unit=gui.mapForEdit.getMap()[ySelected][xSelected];
				gui.mapForEdit.setMap(cells[1], cells[0], unit);
				gui.mapForEdit.setMap(ySelected, xSelected, ' ');
				objectSelected=false;
				repaint();

			}
		}else{
			char unit=gui.mapForEdit.getMap()[cells[1]][cells[0]];

			if(unit!='X' && unit!='I')
				cantBeOnPerimeter=true;
			else
				cantBeOnPerimeter=false;
			xSelected=cells[0];
			ySelected=cells[1];
			objectSelected=true;

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int[] cells=convertCoordinatesToCells(e.getX(), e.getY());
		if(gui.mapForEdit.isFree(cells[1], cells[0])){
			if(objectSelected && !(cantBeOnPerimeter && posOnPerimeter(cells[0], cells[1]))){
				char unit=gui.mapForEdit.getMap()[ySelected][xSelected];
				gui.mapForEdit.setMap(cells[1], cells[0], unit);
				gui.mapForEdit.setMap(ySelected, xSelected, ' ');
				objectSelected=false;
				repaint();

			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean posOnPerimeter(int x, int y){
		if((y!=0 && y!= gui.mapForEdit.getMap().length-1) && (x!=0 && x!=gui.mapForEdit.getMap()[0].length)){
			return false;
		}
		return true;
	}

	public boolean checkPerimeter(){
		for(int i=0;i<gui.mapForEdit.getMap().length;i++){
			if(i==0 || i== gui.mapForEdit.getMap().length-1){
				for(int j=0;j<gui.mapForEdit.getMap()[0].length;j++)
					if(gui.mapForEdit.getMap()[i][j]==' ')
						return false;
			}else if(gui.mapForEdit.getMap()[i][0]==' ' || gui.mapForEdit.getMap()[i][gui.mapForEdit.getMap()[0].length-1]==' ')
				return false;

		}
		return true;
	}

	public String isValidMap() {

		String res="";

		if(!checkPerimeter())
			res="Perimeter of the map not completed.\nInsert walls or doors to create a valid map!\n";

		int [] pos = gui.mapForEdit.getHeroPos();
		
		gui.mapForEdit.initializeVisited();
		if(!(gui.mapForEdit.findGoal(pos[1], pos[0], 'k')))
			res+="This map isn't valid because the hero cannot reach the key!\n";
		
		gui.mapForEdit.initializeVisited();
		if(!(gui.mapForEdit.findGoal(pos[1], pos[0], 'I')))
			res+="This map isn't valid because the hero cannot end the game!\n";
		
		
		
		if(!(gui.mapForEdit.ogreHasValidMoves(gui.mapForEdit.getOgrePos())))
			res+="This map isn't valid because one or more ogres are blocked!\n";
		
		
		
		return res;
	}

}
