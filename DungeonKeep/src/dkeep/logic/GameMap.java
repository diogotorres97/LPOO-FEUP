package dkeep.logic;

import java.io.Serializable;
import java.util.Arrays;


public abstract class GameMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected char [][]map;

	/**
	 * @brief Gets map
	 * @return copyMap - copy of map
	 */
	public char[][] getMap(){

		char copyMap[][]= new char [map.length][map[0].length];

		for (int i=0; i<map.length; i++){
			copyMap[i]=Arrays.copyOf(map[i], map[i].length);
		}

		return copyMap;
	}
	/**
	 * @brief Checks if a certain position of map is free
	 * @param x - line
	 * @param y - column
	 * @return copyMap - copy of map
	 */
	public boolean isFree(int x, int y){
		if(map[x][y]==' ')
			return true;
		else 
			return false;
	}

	/**
	 * @brief Sets a position of map to a new char
	 * @param x - line
	 * @param y - column
	 * @param letter - char to insert
	 */
	public void setMap(int x, int y, char letter){
		map[x][y]=letter;
	}
	/**
	 * @brief Gets the position of a Unit
	 * @param unit
	 * @return array with x and y
	 */
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
	/**
	 * @brief Sets map to m
	 * @param m - new map
	 */
	public abstract void setNewMap(char[][] m);
	/**
	 * @brief Gets Hero position array
	 * @return array with x and y
	 */
	public abstract int [] getHeroPos();
	/**
	 * @brief Gets Ogre position array
	 * @return array with x and y
	 */
	public abstract int [][] getOgrePos();
	/**
	 * @brief Gets Guard position array
	 * @return array with x and y
	 */
	public abstract int [] getGuardPos();

}
