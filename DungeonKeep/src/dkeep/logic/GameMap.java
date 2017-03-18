package dkeep.logic;

import java.util.Arrays;

public abstract class GameMap {

	protected char [][]map;

	public char[][] getMap(){
		
		char copyMap[][]= new char [map.length][map[0].length];
		
		for (int i=0; i<map.length; i++){
			copyMap[i]=Arrays.copyOf(map[i], map[i].length);
		}

		return copyMap;
	}

	boolean isFree(int x, int y){
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
	public abstract int [] getOgrePos();
	public abstract int [] getGuardPos();

}
