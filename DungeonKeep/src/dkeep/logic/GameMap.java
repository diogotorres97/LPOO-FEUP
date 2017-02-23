package dkeep.logic;

public abstract class GameMap {

	protected char [][]map;

	public char[][] getMap(){
		return map;
	}

	boolean isFree(int x, int y){
		if(map[x][y]==' ')
			return true;
		else 
			return false;
	}

}
