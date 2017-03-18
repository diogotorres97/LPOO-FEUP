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

	private BufferedImage heroImg=null;
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

	private GUI gui;

	public ShowGamePanel(GUI gui){
		this.gui = gui;

		try {
			heroImg=ImageIO.read(new File("imgs/hero.png"));
			tileImg=ImageIO.read(new File("imgs/tile.png"));
			drunkenGuardImg=ImageIO.read(new File("imgs/drunken.png"));
			suspiciousGuardImg=ImageIO.read(new File("imgs/suspicious.png"));
			rookieGuardImg=ImageIO.read(new File("imgs/rookie.png"));
			sleepingGuardImg=ImageIO.read(new File("imgs/sleeping.png"));
			leverImg=ImageIO.read(new File("imgs/lever.png"));
			wallImg=ImageIO.read(new File("imgs/wall.png"));
			closedDoorImg=ImageIO.read(new File("imgs/closed_door.png"));
			openDoorImg=ImageIO.read(new File("imgs/open_door.png"));
			ogreImg=ImageIO.read(new File("imgs/ogre.png"));
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
				int posX= j*this.getWidth()/drawMap.length;
				int posY= i*this.getHeight()/drawMap.length;

				g.drawImage(tileImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);

				switch (drawMap[i][j]) {
				case 'X':
					g.drawImage(wallImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'G':
					switch(gui.g.getGuard().getNumStrategy()){
					case 0:
						g.drawImage(rookieGuardImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
						break;
					case 1:
						g.drawImage(drunkenGuardImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
						break;
					case 2:
						g.drawImage(suspiciousGuardImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
						break;
					default: 
						break;
					}
					break;
				case 'H':
					g.drawImage(heroImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'O':
					g.drawImage(ogreImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case '8':
					g.drawImage(ogreStunnedImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'K':
					g.drawImage(armedHeroImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'I':
					g.drawImage(closedDoorImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'S':
					g.drawImage(openDoorImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'k':
					g.drawImage(leverImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case '*':
					g.drawImage(clubImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'A':
					g.drawImage(armedHeroImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'g':
					g.drawImage(sleepingGuardImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				default:
					break;
				}
			}
		}
	}



	@Override
	public void keyPressed(KeyEvent e) {
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



