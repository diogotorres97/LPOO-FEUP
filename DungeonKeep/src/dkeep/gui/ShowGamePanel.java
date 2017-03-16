package dkeep.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ShowGamePanel extends JPanel{

	BufferedImage heroImg;
	BufferedImage armedHeroImg;
	BufferedImage guardImg;
	BufferedImage sleepingGuardImg;
	BufferedImage ogreImg;
	BufferedImage landImg;
	BufferedImage openDoorImg;
	BufferedImage closedDoorImg;
	BufferedImage swordImg;
	BufferedImage wallImg;
	BufferedImage clubImg;
	
	int x,y;
	
	//tem de se saber a largura do mapa, para se ver qt mede cada figura
	
	

	//coordenadas?, recebe mapa?
	public ShowGamePanel() throws IOException{
		
		heroImg=ImageIO.read(new File("src/hero.png"));

		//addKeyListener(this);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//ciclo do mapa
		g.drawImage(heroImg, 100, 100, null);
		//g.drawImage(img, x, y, observer;
	}



}


