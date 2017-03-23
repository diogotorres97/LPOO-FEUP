package dkeep.gui;
import dkeep.logic.*;

public class ValidateEditorMap {
	protected static boolean [][] visited;

	//Functions to Editor Map
	public static void initializeVisited(char[][] map)
	{
		visited = new boolean [map.length][map[0].length];
		for (int i = 0; i < map.length; i++)
			for (int a = 0; a < map[0].length; a++)
				visited[i][a] = false;
	}

	public static boolean findGoal(char[][] map, int x, int y, char goal){
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
			return (testRight ? findGoal(map,x+1,y,goal) : false) ||
					(testUp ? findGoal(map,x,y-1,goal) : false) ||
					(testLeft ? findGoal(map,x-1,y,goal) : false) ||
					(testDown ? findGoal(map,x, y+1,goal) : false);
		}
	}

	public static boolean ogreHasValidMoves(GameMap map, int[][] ogrePos){

		for(int i=0;i<ogrePos.length;i++){
			if(!(map.isFree(ogrePos[i][0]-1, ogrePos[i][1]) || map.isFree(ogrePos[i][0]+1, ogrePos[i][1]) || map.isFree(ogrePos[i][0], ogrePos[i][1]-1) || map.isFree(ogrePos[i][0], ogrePos[i][1]+1)))
				return false;

		}
		return true;

	}
}
