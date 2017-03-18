package dkeep.logic;

public class KeepMap extends GameMap{

	static int [] heroPos= new int [2];
	static int [] ogrePos= new int [2];

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
		ogrePos[0]=1;
		ogrePos[1]=4;
	}

	public int [] getHeroPos(){
		return heroPos.clone();
	}

	public int [] getOgrePos(){
		return ogrePos.clone();
	}

	public int [] getGuardPos(){
		return null;
	}

	@Override
	public void setNewMap(char[][] m) {
		map=m;
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[i].length;j++){
				switch(map[i][j]){
				case 'A':
					heroPos[0]=i;
					heroPos[1]=j;
					break;
				case 'O':
					
					break;
				}
				
					
					
				
			}


	}

}
