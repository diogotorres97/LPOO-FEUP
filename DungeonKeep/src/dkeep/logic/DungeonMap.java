package dkeep.logic;

public class DungeonMap extends GameMap{

	static int [] heroPos= new int [2];
	static int [] guardPos= new int [2];

	public DungeonMap(){

		char mymap[][] = {
				{'X','X','X','X','X','X','X','X','X','X'},
				{'X',' ',' ',' ','I',' ','X',' ',' ','X'},
				{'X','X','X',' ','X','X','X',' ',' ','X'},
				{'X',' ','I',' ','I',' ','X',' ',' ','X'},
				{'X','X','X',' ','X','X','X',' ',' ','X'},
				{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
				{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
				{'X','X','X',' ','X','X','X','X',' ','X'},
				{'X',' ','I',' ','I',' ','X','k',' ','X'},
				{'X','X','X','X','X','X','X','X','X','X'}
		};
		map = mymap;

		heroPos[0]=1;
		heroPos[1]=1;
		guardPos[0]=1;
		guardPos[1]=8;
	}

}
