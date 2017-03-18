package dkeep.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dkeep.logic.GameMap;

public class showEditorPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	private BufferedImage armedHeroImg=null;
	private BufferedImage ogreImg=null;
	private BufferedImage tileImg=null;
	private BufferedImage doorImg=null;
	private BufferedImage wallImg=null;
	private BufferedImage leverImg=null;
	
	private GUI gui;

	/**
	 * Create the panel.
	 */
	public showEditorPanel(GUI gui) {
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
