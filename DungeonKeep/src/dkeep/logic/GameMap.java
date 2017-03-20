package dkeep.logic;

import java.io.Serializable;
import java.util.Arrays;

public abstract class GameMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected char [][]map;
	protected boolean [][] visited;

	public char[][] getMap(){

		char copyMap[][]= new char [map.length][map[0].length];

		for (int i=0; i<map.length; i++){
			copyMap[i]=Arrays.copyOf(map[i], map[i].length);
		}

		return copyMap;
	}

	public boolean isFree(int x, int y){
		if(map[x][y]==' ')
			return true;
		else 
			return false;
	}

	public void setMap(int x, int y, char letter){
		map[x][y]=letter;
	}

	public int[] checkDoorPosition(char unit){
		int[] array = new int[2];
		for(int i=0; i< map.length;i++){
			if(map[i][0]==unit){
				array[0]=i;
				array[1]=0;
				return array;
			}
		}
		return null;
	};

	public abstract void setNewMap(char[][] m);


	public abstract int [] getHeroPos();
	public abstract int [][] getOgrePos();
	public abstract int [] getGuardPos();


	//Functions to Editor Map
	public void initializeVisited()
	{
		visited = new boolean [map.length][map[0].length];
		for (int i = 0; i < map.length; i++)
			for (int a = 0; a < map[0].length; a++)
				visited[i][a] = false;
	}

	public boolean findGoal(int x, int y, char goal){
		visited[y][x] = true;
		if(map[y][x] == 'X' || (map[y][x] == 'I' && goal=='k'))
			return false;
		else if(map[y][x] == goal)
			return true;
		else{
			boolean testUp, testLeft, testDown, testRight;
			testUp = !visited[y-1][x];
			testDown = !visited[y+1][x];
			testLeft = !visited[y][x-1];
			testRight = !visited[y][x+1];
			return (testRight ? findGoal(x+1,y,goal) : false) ||
					(testUp ? findGoal(x,y-1,goal) : false) ||
					(testLeft ? findGoal(x-1,y,goal) : false) ||
					(testDown ? findGoal(x, y+1,goal) : false);
		}
	}
	
	public boolean ogreHasValidMoves(int[][] ogrePos){
		
		for(int i=0;i<ogrePos.length;i++){
			if(!(isFree(ogrePos[i][0]-1, ogrePos[i][1]) || isFree(ogrePos[i][0]+1, ogrePos[i][1]) || isFree(ogrePos[i][0], ogrePos[i][1]-1) || isFree(ogrePos[i][0], ogrePos[i][1]+1)))
				return false;
				
		}
		return true;
		
	}

}
