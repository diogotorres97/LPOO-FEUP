package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.Game;
import dkeep.logic.GameMap;

public class TestKeepGameLogic {

	char [][] map = {{'X','X','X','X','X'},
					{'X','H',' ','O','X'},
					{'I',' ',' ',' ','X'},
					{'I','k',' ',' ','X'},
					{'X','X','X','X','X'}};
					
	
	@Test
	public void testHeroIsCapturedByOgre() {
		GameMap gameMap = new testKeepMap();
		Game game = new Game(gameMap);
		
		assertFalse(game.isGameOver());
		game.moveHero('d',1);
		assertTrue(game.checkOgre(game.getOgre(), 1));
		assertFalse(game.gameWin());
	}
	
	@Test
	public void testHeroIsK() {
		GameMap gameMap = new testKeepMap();
		Game game = new Game(gameMap);
		
		assertFalse(game.isGameOver());
		game.moveHero('s',1);
		game.moveHero('s',1);
		assertEquals('K',game.getHero().getUnit());
		assertFalse(game.gameWin());
	}
	

	@Test
	public void testHeroIsCloseToDoorAndFailToLeave() {
		GameMap gameMap = new testKeepMap();
		Game game = new Game(gameMap);
		
		game.moveHero('s',1);
		assertFalse(game.moveHero('a',0));
		assertFalse(game.gameWin());
	}
	
	@Test
	public void testHeroIsLeverCellAndDoorsOpen() {
		GameMap gameMap = new testKeepMap();
		Game game = new Game(gameMap);
		
		game.moveHero('s',1);
		game.moveHero('s',1);
		assertEquals('K',game.getHero().getUnit());
		game.moveHero('a',1);
		//assertEquals('S',game.getGameMap(0)[2][0]);
		assertEquals('S',game.getGameMap(0)[3][0]);
	
	}
	
	@Test
	public void testHeroDoorsOpenandGoToKeep() {
		GameMap gameMap = new testKeepMap();
		Game game = new Game(gameMap);
		
		game.update('s',1);
		game.update('s',1);
		game.update('a',1);
		assertEquals(1,game.update('a',1));
		assertTrue(game.gameWin());
	
	}
	
}


