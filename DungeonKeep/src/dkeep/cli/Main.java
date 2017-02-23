package dkeep.cli;

import java.util.Random;
import java.util.Scanner;
import dkeep.logic.*;

public class Main {

	public static char readInput(){
		System.out.println("Enter a command:");
		Scanner s = new Scanner(System.in);
		char letter;
		letter = s.next().charAt(0);
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

		Game g = new Game(new DungeonMap());

		char letter;
		int level=0;

		while(!g.isGameOver()){

			switch (level) {
			case 0:
				g.setMap(new DungeonMap());
				break;
			case 1:
				g.setMap(new KeepMap());
				break;
			default:
				break;
			}

			draw(g.getGameMap());
			letter=readInput();
			g.update(letter,level);
		}

		if(g.gameWin())
			System.out.println("YOU WIN!");
		else 
			System.out.println("GAME OVER!");
	}
}
