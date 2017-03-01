package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.*;

public class Main {

	public static char readInput(){
		System.out.println("Enter a command:");
		Scanner s = new Scanner(System.in);
		char letter;
		letter = s.next().charAt(0);
		//s.close();
		return letter;
		
	}

	public static void draw(char nameMap[][]){
		for(int i=0; i< nameMap.length;i++){
			for(int j=0;j< nameMap[i].length;j++)
				System.out.print(nameMap[i][j]);
			System.out.print("\n");
		}
	}
	
	public static void main(String[] args) {

		char letter;
		int level=0;
		int maxLevel = 1;
		Game g = new Game(level);

		while(!g.isGameOver()){
			draw(g.getGameMap(level));
			letter=readInput();
			level=g.update(letter,level);
		}
		draw(g.getGameMap(level));
		
		if(level==maxLevel && g.gameWin())
			System.out.println("YOU WIN!");
		else 
			System.out.println("GAME OVER!");
	}
}
