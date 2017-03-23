package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.*;

public class Main {

	private static String[] consoleText = {"Enter a command:", 
			"Enter the guard personality (0->Rookie, 1->Drunken, 2->Suspicious):",
	"Enter the number of ogres:"};


	public static char readInput(Scanner s, int type){ //type: 0->key, 1->guard personality, 2->num ogres
		System.out.println(consoleText[type]);

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
		Scanner s = new Scanner(System.in);
		char letter;
		int level=0;
		int maxLevel = 1; 
		int num_ogres, guard_personality;

		guard_personality=Character.getNumericValue(readInput(s, 1));
		num_ogres=Character.getNumericValue(readInput(s, 2));

		Game g = new Game(level,guard_personality, num_ogres);

		while(!g.isGameOver()){
			draw(g.getGameMap(level));
			letter=readInput(s,0);
			level=g.update(letter,level);
		}
		draw(g.getGameMap(level));

		printGameState(g,level,maxLevel);

		s.close();
	}


	public static void printGameState(Game g ,int level,int maxLevel){
		if(level==maxLevel && g.gameWin())
			System.out.println("YOU WIN!");
		else 
			System.out.println("GAME OVER!");
	}
}
