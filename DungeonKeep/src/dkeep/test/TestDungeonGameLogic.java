package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.Game;
import dkeep.logic.GameMap;

public class TestDungeonGameLogic {

	char [][] map = {{'X','X','X','X','X'},
					{'X','H',' ','G','X'},
					{'I',' ',' ',' ','X'},
					{'I','k',' ',' ','X'},
					{'X','X','X','X','X'}};
					
	 
	@Test
	public void testMoveHeroIntoToFreeCell() {
		GameMap gameMap = new testMap();
		Game game = new Game(gameMap,0);
		int [] posTest = {1,1};
		assertArrayEquals(posTest,game.getHero().getPosition());
		game.moveHero('s',0); 
		int [] posTest2 = {2,1};
		assertArrayEquals(posTest2,game.getHero().getPosition());
		
	} 
	
	@Test
	public void testMoveHeroIntoToWall() {
		GameMap gameMap = new testMap();
		Game game = new Game(gameMap,0);
		int [] posTest = {1,1};
		assertArrayEquals(posTest,game.getHero().getPosition());
		game.moveHero('a',0); 
		assertArrayEquals(posTest,game.getHero().getPosition());
		
	}
	

	@Test
	public void testHeroIsCapturedByGuard() {
		GameMap gameMap = new testMap();
		Game game = new Game(gameMap,0);
		
		assertFalse(game.isGameOver());
		game.moveHero('d',0);
		assertTrue(game.checkGuard());
		assertFalse(game.gameWin());
	}
	
	@Test
	public void testHeroIsCloseToDoorAndFailToLeave() {
		GameMap gameMap = new testMap();
		Game game = new Game(gameMap,0);
		
		game.moveHero('s',0);
		assertFalse(game.moveHero('a',0));
		assertFalse(game.gameWin());
	}

	@Test
	public void testHeroIsLeverCellAndDoorsOpen() {
		GameMap gameMap = new testMap();
		Game game = new Game(gameMap,0);
		
		game.moveHero('s',0);
		game.moveHero('s',0);
		int [] posTest = {3,1};
		assertArrayEquals(posTest,game.getHero().getPosition());
		assertEquals('S',game.getGameMap(0)[2][0]);
		assertEquals('S',game.getGameMap(0)[3][0]);
	
	}
	
	@Test
	public void testHeroDoorsOpenandGoToKeep() {
		GameMap gameMap = new testMap();
		Game game = new Game(gameMap,0);
		
		game.update('s',0);
		game.update('s',0);
		assertEquals(1,game.update('a',0));
	
	}
	
}


