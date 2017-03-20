package dkeep.test;

import org.junit.Test;

import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.KeepMap;

public class TestSomeRandomBehavior {

	@Test(timeout=1000)
	public void test() {
		GameMap gameMap = new KeepMap();
		Game game = new Game(gameMap);

		boolean ogreUp = false,ogreDown = false,ogreLeft = false,ogreRight=false;
		boolean clubUp = false,clubDown = false,clubLeft = false,clubRight=false;

		while(!ogreUp || !ogreDown || !ogreLeft || !ogreRight || !clubUp || !clubDown || !clubLeft || !clubRight){
			int [] oldPos = game.getOgre().getPosition();
			int [] oldPosC = game.getOgre().getPosClub();
			 
			game.moveOgre(game.getOgre());
			int [] newPos = game.getOgre().getPosition();
			int [] newPosC = game.getOgre().getPosClub();
			
			int [] resultPos = new int[2];
			int [] resultPosC = new int[2];
			
			resultPos[0] = newPos[0]-oldPos[0];
			resultPos[1] = newPos[1]-oldPos[1];
			
			System.out.print(resultPos[0]);
			System.out.println("    "+resultPos[1]);
			
			resultPosC[0] = newPosC[0]-oldPosC[0];
			resultPosC[1] = newPosC[1]-oldPosC[1];
			
			System.out.print(resultPosC[0]);
			System.out.println("    "+resultPosC[1]);

			if(resultPos[0]==-1) // w
				ogreUp=true;
			else if(resultPos[1]==-1) //a
				ogreLeft=true;
			else if(resultPos[0]==1) //s
				ogreDown=true;
			else if(resultPos[1]==1) //d
				ogreRight=true;

			if(resultPosC[0]==-1) // w
				clubUp=true;
			else if(resultPosC[1]==-1) //a
				clubLeft=true;
			else if(resultPosC[0]==1) //s
				clubDown=true;
			else if(resultPosC[1]==1) //d
				clubRight=true;
			

		}

	}

}
