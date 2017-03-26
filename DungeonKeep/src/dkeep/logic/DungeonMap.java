package dkeep.logic;

/**
 * 
 *  Class that handles dungeon map
 *
 */
public class DungeonMap extends GameMap{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int [] heroPos= new int [2];
	int [] guardPos= new int [2];

	/**
	 * @brief Constructor
	 */
	public DungeonMap(){

		char mymap[][] = {
				{'X','X','X','X','X','X','X','X','X','X'},{'X',' ',' ',' ','I',' ','X',' ',' ','X'},	{'X','X','X',' ','X','X','X',' ',' ','X'},	{'X',' ','I',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'X','X','X',' ','X','X','X','X',' ','X'},	{'X',' ','I',' ','I',' ','X','k',' ','X'},{'X','X','X','X','X','X','X','X','X','X'}	};
		map = mymap;

		//Initial Hero and Guard pos
		heroPos[0]=1;
		heroPos[1]=1;
		guardPos[0]=1;
		guardPos[1]=8;
	}

	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#getHeroPos()
	 */
	public int [] getHeroPos(){
		return heroPos.clone();
	}

	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#getGuardPos()
	 */
	public int [] getGuardPos(){
		return guardPos.clone();
	}

	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#getOgrePos()
	 */
	public int [][] getOgrePos(){ 
		return null;
	}

	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#setNewMap(char[][])
	 */
	@Override
	public void setNewMap(char[][] m) {

	}
}
