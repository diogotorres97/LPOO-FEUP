package dkeep.logic;

public class KeepMap extends GameMap{

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
				
				/*
				//Initial Hero position
				posH[0]=7;
				posH[1]=1;

				//Initial Ogre position
				posO[0]=1;
				posO[1]=4;

				//Initial Club position (not visible in the initial map)
				posC[0]=1;
				posC[1]=3;
				*/
		
	}
}
