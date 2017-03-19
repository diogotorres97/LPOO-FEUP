package dkeep.logic;

import java.util.Arrays;

public class KeepMap extends GameMap{

	static int [] heroPos= new int [2];
	//static int [] ogrePos= new int [2];
	static int [][] ogrePos=new int [5][2];

	static int  numOgres=0, numDoors=0, numLevers=0;

	public KeepMap(){
		char mymap[][]={
				{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
				{'I', ' ', ' ', ' ', ' ', ' ', ' ', 'k', 'X'},
				{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
				{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, 
				{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, 
				{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, 
				{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, 
				{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
				{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
		};

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

	//Copy Constructor
	public void copyMap(KeepMap km){
		
		for (int i=0; i<km.map.length; i++){
			map[i]=Arrays.copyOf(km.map[i], km.map[i].length);
		}
		this.heroPos=km.getHeroPos().clone();
		this.ogrePos=km.getOgrePos().clone();
		this.numOgres=km.getNumUnit('O');
		this.numDoors=km.getNumUnit('I');
		this.numLevers=km.getNumUnit('k');


	}

	public int [] getHeroPos(){
		return heroPos.clone();
	}

	public int [][] getOgrePos(){
		return ogrePos.clone();
	}

	public int getNumUnit(char unit){
		switch(unit){
		case 'O': return numOgres;
		case 'k': return numLevers;
		case 'I': return numDoors;
		default: return 2;
		}
	}

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


	public int [] getGuardPos(){
		return null;
	}

	@Override
	public void setNewMap(char[][] m) {
		map=m;
		int iOgre=0;
		numOgres=0;
		numLevers=0;
		numDoors=0;
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[i].length;j++){
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

				}




			}


	}

}
