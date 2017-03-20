package dkeep.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import dkeep.logic.*;

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
	private BufferedImage ogreStunnedImg=null;
	private BufferedImage tileImg=null;
	private BufferedImage openDoorImg=null;
	private BufferedImage closedDoorImg=null;
	private BufferedImage wallImg=null;
	private BufferedImage clubImg=null;
	private BufferedImage leverImg=null;
	//private BufferedImage heroImg=null;
	
	private GUI gui;

	public ShowGamePanel(GUI gui){
		this.gui = gui;

		try {
			closedDoorImg=ImageIO.read(new File("imgs/closed_door.png"));
			openDoorImg=ImageIO.read(new File("imgs/open_door.png"));
			ogreImg=ImageIO.read(new File("imgs/ogre.png"));
			wallImg=ImageIO.read(new File("imgs/wall.png"));
			//heroImg=ImageIO.read(new File("imgs/hero.png"));
			tileImg=ImageIO.read(new File("imgs/tile.png"));
			drunkenGuardImg=ImageIO.read(new File("imgs/drunken.png"));
			suspiciousGuardImg=ImageIO.read(new File("imgs/suspicious.png"));
			rookieGuardImg=ImageIO.read(new File("imgs/rookie.png"));
			sleepingGuardImg=ImageIO.read(new File("imgs/sleeping.png"));
			leverImg=ImageIO.read(new File("imgs/lever.png"));
			ogreStunnedImg=ImageIO.read(new File("imgs/ogre.png"));
			clubImg=ImageIO.read(new File("imgs/club.png"));
			armedHeroImg=ImageIO.read(new File("imgs/armed_hero.png"));
		} catch (IOException e) {


		}

		addKeyListener(this);

	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		char [][] drawMap = gui.g.getGameMap(gui.level);

		for(int i=0; i< drawMap.length;i++){
			for(int j=0;j< drawMap[i].length;j++){
				int posX= j*50;
				int posY= i*50;

				g.drawImage(tileImg, posX, posY,  null);

				switch (drawMap[i][j]) {
				case 'X':
					g.drawImage(wallImg, posX, posY,  null);
					break;
				case 'G':
					switch(gui.g.getGuard().getNumStrategy()){
					case 0:
						g.drawImage(rookieGuardImg, posX, posY,  null);
						break;
					case 1:
						g.drawImage(drunkenGuardImg, posX, posY, null);
						break;
					case 2:
						g.drawImage(suspiciousGuardImg, posX, posY,  null);
						break;
					default: 
						break;
					}
					break;
				case 'H':
					g.drawImage(armedHeroImg, posX, posY,  null);
					break;
				case 'O':
					g.drawImage(ogreImg, posX, posY,  null);
					break;
				case '8':
					g.drawImage(ogreStunnedImg, posX, posY, null);
					break;
				case 'K':
					g.drawImage(armedHeroImg, posX, posY, null);
					break;
				case 'I':
					g.drawImage(closedDoorImg, posX, posY, null);
					break;
				case 'S':
					g.drawImage(openDoorImg, posX, posY,  null);
					break;
				case 'k':
					g.drawImage(leverImg, posX, posY, null);
					break;
				case '*':
					g.drawImage(clubImg, posX, posY, null);
					break;
				case 'A':
					g.drawImage(armedHeroImg, posX, posY,  null);
					break;
				case 'g':
					g.drawImage(sleepingGuardImg, posX, posY,  null);
					break;
				default:
					break;
				}
			}
		}
	}



	@Override
	public void keyPressed(KeyEvent e) {
		int currentLevel=gui.level;
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT: 
			gui.level=gui.g.update('a', gui.level); 
			break;
		case KeyEvent.VK_RIGHT: 
			gui.level=gui.g.update('d', gui.level); 
			break;
		case KeyEvent.VK_UP: 
			gui.level=gui.g.update('w', gui.level); 
			break;
		case KeyEvent.VK_DOWN: 
			gui.level=gui.g.update('s', gui.level); 
			break;
		}
		if(currentLevel!=gui.level){
			gui.panelShowGame.setBounds(25,135,gui.g.getGameMap(1)[0].length*50,gui.g.getGameMap(1).length*50);
			gui.lblGameStatus.setBounds(10, gui.panelShowGame.getY()+gui.panelShowGame.getHeight()+50, 300, 35);
		}
		if(gui.level==gui.maxLevel && gui.g.gameWin()){
			gui.lblGameStatus.setText("You win");
			gui.disableMoveButtons();
		}else if(gui.g.isGameOver()){
			gui.lblGameStatus.setText("You lose");
			gui.disableMoveButtons();
		}
		repaint();

	}

	//Listeners not used
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

}



