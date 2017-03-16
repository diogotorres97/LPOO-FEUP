package dkeep.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import dkeep.logic.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ShowGamePanel extends JPanel{

	BufferedImage heroImg;
	BufferedImage armedHeroImg;
	BufferedImage rookieGuardImg;
	BufferedImage suspiciousGuardImg;
	BufferedImage drunkenGuardImg;
	BufferedImage sleepingGuardImg;
	BufferedImage ogreImg;
	BufferedImage tileImg;
	BufferedImage openDoorImg;
	BufferedImage closedDoorImg;
	BufferedImage swordImg;
	BufferedImage wallImg;
	BufferedImage clubImg;
	BufferedImage leverImg;

	int x,y;
	private GUI gui;

	//tem de se saber a largura do mapa, para se ver qt mede cada figura



	//coordenadas?, recebe mapa?
	public ShowGamePanel(GUI gui){
		this.gui = gui;

		try {
			heroImg=ImageIO.read(new File("src/hero.png"));
			tileImg=ImageIO.read(new File("src/tile.png"));
			drunkenGuardImg=ImageIO.read(new File("src/drunken.png"));
			suspiciousGuardImg=ImageIO.read(new File("src/suspicious.png"));
			rookieGuardImg=ImageIO.read(new File("src/rookie.png"));
			sleepingGuardImg=ImageIO.read(new File("src/sleeping.png"));
			leverImg=ImageIO.read(new File("src/lever.png"));
			wallImg=ImageIO.read(new File("src/wall.png"));
			closedDoorImg=ImageIO.read(new File("src/closed_door.png"));
			openDoorImg=ImageIO.read(new File("src/open_door.png"));
			ogreImg=ImageIO.read(new File("src/ogre.png"));
			clubImg=ImageIO.read(new File("src/club.png"));
			armedHeroImg=ImageIO.read(new File("src/armed_hero.png"));
		} catch (IOException e) {

		}

		//addKeyListener(this);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		char [][] drawMap = gui.g.getGameMap(gui.level);

		for(int i=0; i< drawMap.length;i++){
			for(int j=0;j< drawMap[i].length;j++){
				int posX= j*this.getWidth()/drawMap.length;
				int posY= i*this.getHeight()/drawMap.length;

				switch (drawMap[i][j]) {
				case 'X':
					g.drawImage(wallImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'G':
					switch(gui.g.getGuard().getNumStrategy()){
					case 0:
						g.drawImage(tileImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
						g.drawImage(rookieGuardImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
						break;
					case 1:
						g.drawImage(drunkenGuardImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
						break;
					case 2:
						g.drawImage(suspiciousGuardImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
						break;
					default: break;
					}
					
					break;
				case 'H':
					g.drawImage(heroImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;
				case 'O':
					g.drawImage(ogreImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
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
				case ' ':
					g.drawImage(tileImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
					break;

				case 'k':
					g.drawImage(tileImg, posX, posY, this.getWidth()/drawMap.length, this.getHeight()/drawMap[i].length, null);
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


}



