package dkeep.logic;
import java.util.Random;

public class Game {

	private Hero hero;
	private Guard guard;
	private Ogre ogre;
	private GameMap map; 
	private boolean victory;
	private boolean gameOver;

	public Game (GameMap startMap){
		hero=new Hero();
		guard=new Guard(1);
		ogre = new Ogre();
		map = startMap;
		victory=false;
		gameOver=false;
	}

	public void setMap(GameMap newMap){
		this.map=newMap;
	}

	public boolean isGameOver(){
		return gameOver;
	}

	public int update(char letter, int level){
		/*
		 * switch case level
		 * 
		 * qe faz cenas
		 * move
		 * move
		 * do while move ogre( level 2)
		 * checkguard
		 * check ogre (deppends on level)
		 * */

		return 0;	
	}

	public char[][] getGameMap(int level)	{
		return updateMap(level);

	}

	public boolean gameWin(){
		return victory;
	}	

	/*----------------------------------------------------------------*/
	//Movement

	public static int[] convertCommandToArray(char command){
		int [] pos = new int [2];

		switch(command){
		case 'w':
			pos[0]=-1;
			pos[1]=0;
			break;
		case 'a':
			pos[0]=0;
			pos[1]=-1;
			break;
		case 'd':
			pos[0]=0;
			pos[1]=1;
			break;
		case 's':
			pos[0]=1;
			pos[1]=0;
			break;
		default:
			break;
		}
		return pos;
	}

	public boolean moveHero(char command, int level){
		int[] pos = convertCommandToArray(command);
		int[] newPos= hero.getPosition();

		newPos[0] += pos[0];
		newPos[1] += pos[1];

		if(map.isFree(newPos[0],newPos[1])){
			hero.setPosition(newPos[0], newPos[1]);
			return true;
		}
		else 
			return false;

		/* if (key)
		 * switch level
		 * case 1
		 * set mapa da class mesmo e nao a cópia
		 * case 2
		 */
	}

	public boolean moveGuard(){
		int[] newPos= guard.getPosition();

		switch (guard.getGuard()) {
		case 1:
			int[] pos = convertCommandToArray(guard.getActualRoute(guard.getIndex()));
			guard.increaseIndex();
			if(guard.getIndex() == guard.getRouteSize())
				guard.resetIndex();

			newPos[0] += pos[0];
			newPos[1] += pos[1];
			guard.setPosition(newPos[0], newPos[1]);
			break;
		default:
			break;
		}
		return true; //always true because guard only do valid movements
	}

	public boolean moveOgre(){
		Random rn = new Random();
		int i = rn.nextInt(4);

		char moves[]= {'w','a','s','d'};

		int[] pos = convertCommandToArray(moves[i]);
		int[] newPos= ogre.getPosition();

		newPos[0] += pos[0];
		newPos[1] += pos[1];

		if(map.isFree(newPos[0],newPos[1])){
			ogre.setPosition(newPos[0], newPos[1]);
			return true;
		}
		else 
			return false;

		/* if(guard.clube)
		 * cenas :D
		 * do while(random clube a volta ate ser valido)
		 */

	}

	public boolean checkGuard(){
		int[] posH= hero.getPosition();
		int[] posG= guard.getPosition();

		if(		posH[0]-1 == posG[0] ||
				posH[0]+1 == posG[0]||
				posH[1]-1 == posG[1] || 
				posH[1]+1 == posG[1]||
				(posH[0]== posG[0] && posH[1]== posG[1]))
			return true;
		else
			return false;
	} 


	public boolean checkOgre(){
		int[] posH= hero.getPosition();
		int[] posO= ogre.getPosition();

		if(		posH[0]-1 == posO[0] ||
				posH[0]+1 == posO[0]||
				posH[1]-1 == posO[1] || 
				posH[1]+1 == posO[1]||
				(posH[0]== posO[0] && posH[1]== posO[1]))
			return true;
		else
			return false;

	}

	public char[][] updateMap(int level){
		int[] posH= hero.getPosition();
		
		char [][]copyMap=getGameMap();
		
		//verificar se a cada iteração, ele utiliza um mapa novo 

		switch (level) {
		case 1:
			int[] posG= guard.getPosition();
			copyMap[posH[0]][posH[1]]=hero.getUnit();
			copyMap[posG[0]][posG[1]]=guard.getUnit();
			break;
		case 2:
			int[] posO= ogre.getPosition();
			copyMap[posH[0]][posH[1]]=hero.getUnit();
			copyMap[posO[0]][posO[1]]=ogre.getUnit();
			break;
		default:
			break;
		}

		return copyMap;
	}

}