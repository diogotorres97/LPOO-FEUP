package dkeep.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import dkeep.logic.Game;
import dkeep.logic.GameMap;

public class TestGameLogic {

	char moves[]= {'w','a','s','d'};
	char heroMovesToGoToLeverDungeon[] = {'d','d', 's', 's', 's', 'd', 's', 's', 'd', 'd', 'd', 'd', 'd', 's', 's', 'a'};
	char heroMovesToFromLeverToDoorsDungeon[] = {'d','w', 'w', 'a', 'a', 'a', 'a', 'a', 'a', 'a'};
	char heroMovesToGetToGuard[]= {'d','d','s','s','s','s','d','d','d','d','w','w','w','w'};
	char heroMovesToGetTokKeep[]= {'d','d','d','d','d','d','w','w','w','w','w','w'};
	char heroMovesFromkToDoorKeep[]= {'s','s','a','a','a','a','a','a','w','w','a'};

	//DUNGEON 

	@Test
	public void testMoveHeroIntoToFreeCell() {

		Game game = new Game(0); 
		int [] posTest = {1,1};
		assertArrayEquals(posTest,game.getHero().getPosition());
		game.moveHero('d',0); 
		int [] posTest2 = {1,2}; 
		assertArrayEquals(posTest2,game.getHero().getPosition());

	}

	@Test
	public void testMoveHeroIntoToWall() {

		Game game = new Game(0);
		int [] posTest = {1,1};
		assertArrayEquals(posTest,game.getHero().getPosition());
		game.moveHero('a',0); 
		assertArrayEquals(posTest,game.getHero().getPosition());

	}


	@Test
	public void testHeroIsCapturedByGuard() {

		Game game = new Game(0);

		assertFalse(game.isGameOver());

		for(int i=0;i<heroMovesToGetToGuard.length;i++){
			game.moveHero(heroMovesToGetToGuard[i], 0);
		}
		assertTrue(game.checkGuard());
		assertFalse(game.gameWin()); 
	}

	@Test
	public void testHeroIsCloseToDoorAndFailToLeaveDungeon() {

		Game game = new Game(0);

		game.moveHero('s',0);
		assertFalse(game.moveHero('a',0));
		assertFalse(game.gameWin());
	}

	@Test
	public void testHeroIsLeverCellAndDoorsOpenDungeon() {

		Game game = new Game(0);


		for(int i=0;i<heroMovesToGoToLeverDungeon.length;i++){
			game.moveHero(heroMovesToGoToLeverDungeon[i], 0);
		}
		int [] posTest = {8,7}; 
		assertArrayEquals(posTest,game.getHero().getPosition());
		assertEquals('S',game.getGameMap(0)[5][0]);
		assertEquals('S',game.getGameMap(0)[6][0]);

	}

	@Test
	public void testHeroDoorsOpenandGoToKeep() {

		Game game = new Game(0);
		for(int i=0;i<heroMovesToGoToLeverDungeon.length;i++){
			game.moveHero(heroMovesToGoToLeverDungeon[i], 0);
		}
		for(int i=0;i<heroMovesToFromLeverToDoorsDungeon.length;i++){
			game.moveHero(heroMovesToFromLeverToDoorsDungeon[i], 0);
		}
		assertEquals(1,game.update('a',0));

	}

	@Test
	public void testDrunkenGuard(){
		Game game;
		do{
			game = new Game(0);
		}while(game.getGuard().getNumStrategy()!=1); 
		do{
			game.update('w',0);
		}while(!game.getGuard().getHasResetIndex());



	}

	@Test
	public void testRookieGuard(){
		Game game;
		do{
			game = new Game(0);
		}while(game.getGuard().getNumStrategy()!=0); 
		do{
			game.update('w',0);
		}while(!game.getGuard().getHasResetIndex());

	}  

	@Test  
	public void testSuspiciousGuard(){
		Game game;
		do{
			game = new Game(0); 
		}while(game.getGuard().getNumStrategy()!=2); 
		do{
			game.update('w',0);
		}while(!game.getGuard().getHasResetIndex());
	}

	//KEEP

	@Test 
	public void testHeroIsCapturedByOgre() {

		Game game = new Game(1);

		assertFalse(game.isGameOver());
		while(!game.isGameOver()){

			Random rn = new Random();
			int i = rn.nextInt(4); 
			game.update(moves[i],1);
		}  

		for(int i=0;i<game.getMilitia().size();i++){ 


			if(game.checkOgre(game.getMilitia().get(i), 1)){
				assertTrue(game.checkOgre(game.getMilitia().get(i), 1));

				break; 
			}
		} 
		assertFalse(game.gameWin());
	} 

	@Test
	public void testHeroIsK() {

		Game game = new Game(1);

		assertFalse(game.isGameOver());

		for(int i=0;i<heroMovesToGetTokKeep.length;i++){
			game.update(heroMovesToGetTokKeep[i], 1);
		}

		assertEquals('K',game.getHero().getUnit());
		assertFalse(game.gameWin());
	} 


	@Test
	public void testHeroIsCloseToDoorAndFailToLeaveKeep() {

		Game game = new Game(1);

		game.moveHero('s',1); 
		assertFalse(game.moveHero('a',0));
		assertFalse(game.gameWin());
	}

	@Test
	public void testHeroIsLeverCellAndDoorsOpenKeep() {

		Game game = new Game(1);

		for(int i=0;i<heroMovesToGetTokKeep.length;i++){
			game.update(heroMovesToGetTokKeep[i], 1);
		}
		assertEquals('K',game.getHero().getUnit());
		for(int i=0;i<heroMovesFromkToDoorKeep.length;i++){
			game.update(heroMovesFromkToDoorKeep[i], 1);
		}
		
		assertEquals('S',game.getGameMap(1)[1][0]);

	}

	@Test 
	public void testHeroDoorsOpenandGoToWin() {

		Game game = new Game(1); 

		for(int i=0;i<heroMovesToGetTokKeep.length;i++){
			game.update(heroMovesToGetTokKeep[i], 1);
		}
		assertEquals('K',game.getHero().getUnit());
		for(int i=0;i<heroMovesFromkToDoorKeep.length;i++){
			game.update(heroMovesFromkToDoorKeep[i], 1);
		}
		assertEquals(1,game.update('a',1));
		assertTrue(game.gameWin());

	}


}
