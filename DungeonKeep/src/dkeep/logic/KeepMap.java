package dkeep.logic;

import java.util.Arrays;

/**
 * 
 *  Class that handles keep map
 *
 */
public class KeepMap extends GameMap{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int [] heroPos= new int [2];
	int [][] ogrePos=new int [5][2];

	int  numOgres=1, numDoors=1, numLevers=1;
	/**
	 * @brief Constructor
	 */
	public KeepMap(){
		char mymap[][]={{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},{'I', ' ', ' ', ' ', ' ', ' ', ' ', 'k', 'X'},{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
				{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};

		map=mymap;

		//Initial Hero position
		heroPos[0]=7;
		heroPos[1]=1;

		//Initial Ogre position
		for(int i=0;i<ogrePos.length;i++){
			ogrePos[i][0]=1;
			ogrePos[i][1]=4;
		}

		numOgres=1;
		numDoors=1;
		numLevers=1;
	}
	/**
	 * @brief Resizes the map
	 * @param x - width
	 * @param y - height
	 */
	public void resizeMap(int x, int y){
		map=new char[y][x];

		for(int i=0;i<y;i++){
			for(int j=0;j<x;j++){ 
				if(i==0 || i==y-1 || j==0 || j==x-1)
					map[i][j]='X';
				else
					map[i][j]=' ';
			}
		}	

		//Set other objects in default positions

		map[1][0]='I';
		map[1][2]='O';
		map[1][x-2]='k';
		map[y-2][1]='A';

		ogrePos[0][0]=1;
		ogrePos[0][1]=2;

		heroPos[0]=y-2;
		heroPos[1]=1;

	}

	/**
	 * @brief Clones KeepMap
	 * @param km - KeepMap
	 */
	public void copyMap(KeepMap km){
		map=new char[km.getMap().length][km.getMap()[0].length];
		for (int i=0; i<km.map.length; i++){
			map[i]=Arrays.copyOf(km.map[i], km.map[i].length);
		}
		heroPos=km.getHeroPos();
		ogrePos=km.getOgrePos();
		numOgres=km.getNumUnit('O');
		numDoors=km.getNumUnit('I');
		numLevers=km.getNumUnit('k');


	}

	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#getHeroPos()
	 */
	public int [] getHeroPos(){
		return heroPos.clone();
	}

	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#getOgrePos()
	 */
	public int [][] getOgrePos(){
		return ogrePos.clone();
	}
	/**
	 * @brief Gets the number of certain unit that exists
	 * @param unit - char
	 * @return
	 */
	public int getNumUnit(char unit){
		switch(unit){
		case 'O': return numOgres;
		case 'k': return numLevers;
		case 'I': return numDoors;
		default: return 2;
		}
	}
	/**
	 * @brief Sets the number of an unit
	 * @param unit - char
	 * @param n -  int
	 */
	public void setNumUnit(char unit, int n){
		switch(unit){
		case 'O': 
			numOgres+=n; 
			break;
		case 'k': 
			numLevers+=n;
			break;
		case 'I': 
			numDoors+=n;
			break;
		default: 
			break;
		}
	}


	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#getGuardPos()
	 */
	public int [] getGuardPos(){
		return null;
	}

	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#setNewMap(char[][])
	 */
	@Override
	public void setNewMap(char[][] m) {
		map=m;
		int iOgre=0;
		numOgres=0;
		numLevers=0; 
		numDoors=0;
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[i].length;j++){
				changeCharMap(map,i,j,iOgre);
			}
	}

	/**
	 * @brief Changes the map at a certain position to a new char
	 * @param map
	 * @param i
	 * @param j
	 * @param iOgre
	 */
	public void changeCharMap(char[][] map, int i, int j, int iOgre){
		switch(map[i][j]){
		case 'A':
			heroPos[0]=i;
			heroPos[1]=j;
			map[i][j]=' ';
			break;
		case 'O':
			ogrePos[iOgre][0]=i;
			ogrePos[iOgre][1]=j;
			iOgre++;
			map[i][j]=' ';
			numOgres++;
			break; 
		case 'k':
			numLevers++;
			break;
		case 'I':
			numDoors++;
			break;
		case 'X':
			map[i][j]='X';
			break;

		}
	}
}
