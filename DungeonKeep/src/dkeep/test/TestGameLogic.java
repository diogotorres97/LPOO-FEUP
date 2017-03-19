package dkeep.test;

import static org.junit.Assert.*;

import java.util.Arrays;
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

		Game game = new Game(0,0,0);  
		if(game.getGuard().getNumStrategy()!=0)
			fail("Don't set strategy");
		if(game.getCurrentMap().getOgrePos()!=null)
			fail("Ogre not null");
		
		int [] posTest = {1,1};
		assertArrayEquals(posTest,game.getHero().getPosition());
		game.moveHero('d',0); 
		int [] posTest2 = {1,2}; 
		assertArrayEquals(posTest2,game.getHero().getPosition());

	}

	@Test
	public void testMoveHeroIntoToWall() {
		GameMap gameMap = new testKeepMap();
		Game game = new Game(gameMap); 
		
		if(game.getHero().getPosition()==null)
			fail("Don't set hero position");
		if(game.getGuard().getPosition()==null)
			fail("Don't set guard position");
		if(game.getOgre()==null)
			fail("Don't set ogre");
		
		int[]posOgre= game.getOgre().getPosition();
		assertEquals(posOgre[0],game.getCurrentMap().getOgrePos()[0][0]);
		assertEquals(posOgre[1],game.getCurrentMap().getOgrePos()[0][1]);
		
		int [] posTest = {1,1};
		assertArrayEquals(posTest,game.getHero().getPosition());
		game.moveHero('a',0); 
		assertArrayEquals(posTest,game.getHero().getPosition());

	}

	@Test
	public void testCoversinDungeon() {
		GameMap gameMap = new testMap();
		Game game = new Game(gameMap); 
		if(game.getGuard().getNumStrategy()!=1)
			fail("Don't set strategy");
		if(game.getHero().getPosition()==null)
			fail("Don't set hero position");
		if(game.getGuard().getPosition()==null)
			fail("Don't set guard position");
		if(game.getOgre()==null)
			fail("Don't set ogre");
		
		int[]posGuard= game.getGuard().getPosition();
		assertEquals(posGuard[0],game.getCurrentMap().getGuardPos()[0]);
		assertEquals(posGuard[1],game.getCurrentMap().getGuardPos()[1]);
		
		
		int [] posTest = {1,1};
		assertArrayEquals(posTest,game.getHero().getPosition());
		game.moveHero('a',0); 
		assertArrayEquals(posTest,game.getHero().getPosition());

	}

	@Test
	public void testHeroIsCapturedByGuard() {

		Game game = new Game(0,0,0); 
		if(game.getGuard().getNumStrategy()!=0)
			fail("Don't set strategy");

		assertFalse(game.isGameOver());

		for(int i=0;i<heroMovesToGetToGuard.length;i++){
			game.moveHero(heroMovesToGetToGuard[i], 0);
		}
		assertTrue(game.checkGuard());
		assertFalse(game.gameWin()); 
	}

	@Test
	public void testHeroIsCloseToDoorAndFailToLeaveDungeon() {

		Game game = new Game(0,0,0); 
		if(game.getGuard().getNumStrategy()!=0)
			fail("Don't set strategy");

		game.moveHero('s',0);
		assertFalse(game.moveHero('a',0));
		assertFalse(game.gameWin());
	}

	@Test
	public void testHeroIsLeverCellAndDoorsOpenDungeon() {

		Game game = new Game(0,0,1); 
		if(game.getGuard().getNumStrategy()!=0)
			fail("Don't set strategy");

		boolean test = true;
		for(int i=0;i<heroMovesToGoToLeverDungeon.length;i++){
			test=game.moveHero(heroMovesToGoToLeverDungeon[i], 0);
		}
		int [] posTest = {8,7}; 
		assertArrayEquals(posTest,game.getHero().getPosition());
		
		if(test==false)
			fail("Don't return true but move");
			
		
		assertEquals('S',game.getGameMap(0)[5][0]);
		assertEquals('S',game.getGameMap(0)[6][0]);
		
		

	}

	@Test
	public void testHeroDoorsOpenandGoToKeep() {

		Game game = new Game(0,0,1); 
		for(int i=0;i<heroMovesToGoToLeverDungeon.length;i++){
			game.moveHero(heroMovesToGoToLeverDungeon[i], 0);
		}
		for(int i=0;i<heroMovesToFromLeverToDoorsDungeon.length;i++){
			game.moveHero(heroMovesToFromLeverToDoorsDungeon[i], 0);
		}
		assertEquals(1,game.update('a',0));

		if(game.getHero().getUnit()!='A')
			fail("Unit not change to A");
		
		if(game.getHero().getUnit()=='K')
			fail("Unit change to K at beginning");

		if(game.getGameMap(0)==game.getCurrentMap().getMap())
			fail("Don't change map of level");

		int [] test = game.getMilitia().get(0).getPosClub();
		if(game.getMilitia().get(0).getPosClub()[0]!=test[0] &&
				game.getMilitia().get(0).getPosClub()[1]!=test[1])
			fail("Don't update pos club");
		
		if(game.getCurrentMap().getGuardPos()!=null)
			fail("Don't return null guard");

	}

	@Test
	public void testDrunkenGuard(){
		Game game = new Game(0,1,0); 
		if(game.getGuard().getNumStrategy()!=1)
			fail("Don't set strategy");
		/*do{
			game = new Game(0);
		}while(game.getGuard().getNumStrategy()!=1); */
		do{
			game.update('w',0);
		}while(!game.getGuard().getHasResetIndex());



	}

	@Test
	public void testRookieGuard(){
		Game game = new Game(0,0,0); 
		if(game.getGuard().getNumStrategy()!=0)
			fail("Don't set strategy");
		/*do{
			game = new Game(0);
		}while(game.getGuard().getNumStrategy()!=0); */
		
		
		do{
			int[] pos = game.getGuard().getPosition();
			game.update('w',0);
			int[] pos1=game.getGuard().getPosition();
			if(Arrays.equals(pos, pos1))
				fail("don't change position");
			if(game.getGuard().getIndex() == game.getGuard().getRouteSize()&&game.getGuard().getHasResetIndex()==false)
				fail("Don't activate booleal hasResetIndex");
				
		}while(!game.getGuard().getHasResetIndex());

	}  

	@Test  
	public void testSuspiciousGuard(){
		Game game = new Game(0,2,0); 
		if(game.getGuard().getNumStrategy()!=2)
			fail("Don't set strategy");
		/*do{
			game = new Game(0); 
		}while(game.getGuard().getNumStrategy()!=2); */
		boolean test=false;
		boolean test1=false;
		do{
			test=game.getGuard().getStrategy().getHasReverted();
			game.update('w',0);
			test1=game.getGuard().getStrategy().getHasReverted();
			if( test==true&&test1==true)
				fail("Don't setHasReverted");
		}while(!game.getGuard().getHasResetIndex());
	}

	//KEEP

	@Test 
	public void testHeroIsCapturedByOgre() {

		Game game = new Game(1,0,3); 
		if(game.getMilitia().size()!=3)
			fail("Don't create all the ogres");

		assertFalse(game.isGameOver());
		while(!game.isGameOver()){

			Random rn = new Random();
			int i = rn.nextInt(4); 
			
			int[]posOgre= game.getMilitia().get(0).getPosition();
			
			game.update(moves[i],1);
			if(posOgre[0]==game.getMilitia().get(0).getPosition()[0]&&posOgre[1]==game.getMilitia().get(0).getPosition()[1])
				fail("Don't change position");
		
		}  

		if(game.getHero().getUnit()!='A')
			fail("Unit not change to A");
		
		for(int i=0;i<game.getMilitia().size();i++){ 


			if(game.checkOgre(game.getMilitia().get(i), 1)){
				assertTrue(game.checkOgre(game.getMilitia().get(0), 1));

				break; 
			} 
		} 
		assertFalse(game.gameWin());
	} 

	@Test
	public void testHeroIsK() {

		Game game = new Game(1,0,1); 

		assertFalse(game.isGameOver());
		if(game.getHero().getUnit()!='A')
			fail("Unit not change to A");

		for(int i=0;i<heroMovesToGetTokKeep.length;i++){
			game.update(heroMovesToGetTokKeep[i], 1);
		}

		assertEquals('K',game.getHero().getUnit());
		assertFalse(game.gameWin());
	} 


	@Test
	public void testHeroIsCloseToDoorAndFailToLeaveKeep() {

		Game game = new Game(1,0,1); 

		game.moveHero('s',1); 
		assertFalse(game.moveHero('a',0));
		assertFalse(game.gameWin());
	}

	@Test
	public void testHeroIsLeverCellAndDoorsOpenKeep() {

		Game game = new Game(1,0,1); 

		for(int i=0;i<heroMovesToGetTokKeep.length;i++){
			game.update(heroMovesToGetTokKeep[i], 1);
		}
		assertEquals('K',game.getHero().getUnit());
		
		for(int i=0;i<heroMovesFromkToDoorKeep.length;i++){
			game.update(heroMovesFromkToDoorKeep[i], 1);
		}

		if(game.getHero().getUnit()!= 'A')
			fail("Don't change the unit character to A");
		
		
		assertEquals('S',game.getGameMap(1)[1][0]);

	}

	@Test 
	public void testHeroDoorsOpenandGoToWin() {

		Game game = new Game(1,0,1); 

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

	@Test
	public void testMoveHeroIntoToFreeCellMT() {

		Game game = new Game(1,0,1); 
		int [] posTest = game.getHero().getPosition(); 

		assertTrue(game.moveHero('d',0));

		if( !game.moveHero('d',0) &&   game.getHero().getPosition()[0]==posTest[0]+1
				&&game.getHero().getPosition()[1]==posTest[1])
			fail("Move but return false");

	}

}
