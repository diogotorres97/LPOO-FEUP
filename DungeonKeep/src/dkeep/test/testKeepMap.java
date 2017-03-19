package dkeep.test;

import dkeep.logic.GameMap;

public class testKeepMap extends GameMap{

	static int [] heroPos= new int [2];
	static int [][] ogrePos= new int [1][2];
	
	public testKeepMap(){
		char [][] mymap = {{'X','X','X','X','X'},
							{'X',' ',' ',' ','X'},
							{'I',' ',' ',' ','X'},
							{'I','k',' ',' ','X'},
							{'X','X','X','X','X'}};
				 
				map=mymap;
				 
				//Initial Hero position
				heroPos[0]=1;
				heroPos[1]=1;

				//Initial Ogre position
				ogrePos[0][0]=1;
				ogrePos[0][1]=3;
	}
	
	public int [] getHeroPos(){
		return heroPos.clone();
	}
	
	public int [][] getOgrePos(){
		return ogrePos.clone();
	}
	
	public int [] getGuardPos(){
		return null;
	}

	@Override
	public void setNewMap(char[][] m) {
		// TODO Auto-generated method stub
		
	}
	
}
