package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.*;

public class Main {

	public static char readInput(Scanner s, int type){ //type: 0->key, 1->guard personality, 2->num ogres
		switch(type){
		case 0:
			System.out.println("Enter a command:");
			break;
		case 1:
			System.out.println("Enter the guard personality (0->Rookie, 1->Drunken, 2->Suspicious):");
			break;
		case 2: 
			System.out.println("Enter the number of ogres:");
			break;
		default: break;
		}
		
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
		
		guard_personality=(int)readInput(s, 1);
		num_ogres=(int)readInput(s, 2);
		
		Game g = new Game(level,guard_personality, num_ogres);

		while(!g.isGameOver()){
			draw(g.getGameMap(level));
			letter=readInput(s,0);
			level=g.update(letter,level);
		}
		draw(g.getGameMap(level));

		if(level==maxLevel && g.gameWin())
			System.out.println("YOU WIN!");
		else 
			System.out.println("GAME OVER!");

		s.close();
	}
}
