package dkeep.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import dkeep.logic.*;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ShowGamePanel extends JPanel implements KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage armedHeroImg=null;
	private BufferedImage rookieGuardImg=null;
	private BufferedImage suspiciousGuardImg=null;
	private BufferedImage drunkenGuardImg=null;
	private BufferedImage sleepingGuardImg=null;
	private BufferedImage ogreImg=null;
	private BufferedImage tileImg=null;
	private BufferedImage openDoorImg=null;
	private BufferedImage closedDoorImg=null;
	private BufferedImage wallImg=null;
	private BufferedImage clubImg=null;
	private BufferedImage leverImg=null;
	private BufferedImage heroImg=null;
	private BufferedImage stunnedOgreImg=null;
	private BufferedImage dollarImg=null;
	private BufferedImage heroKeyImg=null;
	
	private PanelGame pg;
	
	private HashMap<Character, BufferedImage> CHAR_IMGS=new HashMap<Character, BufferedImage>();
 
	public ShowGamePanel(PanelGame pg){
		this.pg=pg;

		try {
			closedDoorImg=ImageIO.read(new File("imgs/closed_door.png"));
			openDoorImg=ImageIO.read(new File("imgs/open_door.png"));
			ogreImg=ImageIO.read(new File("imgs/ogre.png"));
			wallImg=ImageIO.read(new File("imgs/wall.png"));
			heroImg=ImageIO.read(new File("imgs/hero.png"));
			stunnedOgreImg=ImageIO.read(new File("imgs/stunned_ogre.png"));
			dollarImg=ImageIO.read(new File("imgs/dollar.png"));
			tileImg=ImageIO.read(new File("imgs/tile.png"));
			drunkenGuardImg=ImageIO.read(new File("imgs/drunken.png"));
			suspiciousGuardImg=ImageIO.read(new File("imgs/suspicious.png"));
			rookieGuardImg=ImageIO.read(new File("imgs/rookie.png"));
			sleepingGuardImg=ImageIO.read(new File("imgs/sleeping.png"));
			leverImg=ImageIO.read(new File("imgs/lever.png"));
			clubImg=ImageIO.read(new File("imgs/club.png"));
			armedHeroImg=ImageIO.read(new File("imgs/armed_hero.png"));
			heroKeyImg=ImageIO.read(new File("imgs/hero_lever.png"));
		} catch (IOException e) {


		}
		
		
		addKeyListener(this);

	}
	
	private BufferedImage getGuardPers(){
		switch(pg.g.getGuard().getNumStrategy()){
		case 0:
			return rookieGuardImg;
		case 1:
			return drunkenGuardImg;
		case 2:
			return suspiciousGuardImg;
		default: 
			break;
		}
		return null;
	}
	
	private void fillHashMap(){
		CHAR_IMGS.put('X', wallImg);
		CHAR_IMGS.put(' ', tileImg);
		CHAR_IMGS.put('S', openDoorImg);
		CHAR_IMGS.put('I', closedDoorImg);
		CHAR_IMGS.put('O', ogreImg);
		CHAR_IMGS.put('8', stunnedOgreImg);
		CHAR_IMGS.put('H', heroImg);
		CHAR_IMGS.put('A', armedHeroImg);
		CHAR_IMGS.put('K', heroKeyImg);
		CHAR_IMGS.put('k', leverImg);
		CHAR_IMGS.put('$', dollarImg);
		CHAR_IMGS.put('G', getGuardPers());
		CHAR_IMGS.put('g', sleepingGuardImg);
		CHAR_IMGS.put('*', clubImg);
		
	}
	
	

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		fillHashMap();
		char [][] drawMap = pg.g.getGameMap(pg.level);

		for(int i=0; i< drawMap.length;i++){
			for(int j=0;j< drawMap[i].length;j++){
				int posX= j*pg.CELL_WIDTH;
				int posY= i*pg.CELL_WIDTH;

				g.drawImage(tileImg, posX, posY,  null);
				g.drawImage(CHAR_IMGS.get(drawMap[i][j]), posX, posY,  null);
				
				
			}
		}
	}



	@Override
	public void keyPressed(KeyEvent e) {
		int currentLevel=pg.level;
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT: 
			pg.level=pg.g.update('a', pg.level); 
			break;
		case KeyEvent.VK_RIGHT: 
			pg.level=pg.g.update('d', pg.level); 
			break;
		case KeyEvent.VK_UP: 
			pg.level=pg.g.update('w', pg.level); 
			break;
		case KeyEvent.VK_DOWN: 
			pg.level=pg.g.update('s', pg.level); 
			break;
		}
		if(currentLevel!=pg.level){
			pg.panelShowGame.setBounds(25,135,pg.g.getGameMap(1)[0].length*pg.CELL_WIDTH,pg.g.getGameMap(1).length*pg.CELL_WIDTH);
			pg.lblGameStatus.setBounds(10, getY()+getHeight()+10, 300, 35);
		}
		if(pg.level==pg.MAX_LEVEL && pg.g.gameWin()){
			pg.lblGameStatus.setText("You win");
			pg.disableMoveButtons();
		}else if(pg.g.isGameOver()){
			pg.lblGameStatus.setText("You lose");
			pg.disableMoveButtons();
		}
		repaint();

	}

	//Listeners not used
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

}



