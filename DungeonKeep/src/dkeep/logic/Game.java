package dkeep.logic;
import java.util.Random;

public class Game {

	private Hero hero;
	private Guard guard;
	private Ogre ogre;
	private GameMap map;
	private GameMap[] maps = new GameMap [2]; 
	private boolean victory;
	private boolean gameOver;

	public Game (int level){
		hero=new Hero();
		guard=new Guard(1);
		ogre = new Ogre(1);
		victory=false;
		gameOver=false;

		maps[0] = new DungeonMap();
		maps[1] = new KeepMap();
		map= maps[level];

		initializeUnits(level);
	}

	public void initializeUnits(int level){
		switch (level) {
		case 0:
			hero.setPosition(maps[0].getHeroPos()[0], maps[0].getHeroPos()[1]);
			guard.setPosition(maps[0].getGuardPos()[0], maps[0].getGuardPos()[1]);
			break;
		case 1:
			hero.setPosition(maps[1].getHeroPos()[0], maps[1].getHeroPos()[1]);
			ogre.setPosition(maps[1].getOgrePos()[0], maps[1].getOgrePos()[1]);
			ogre.setPosClub(ogre.getPosition()[0], ogre.getPosition()[1]+1);
			break;
		default:
			break;
		}

	}

	public void setMap(int level){
		this.map=maps[level];
	}

	public boolean isGameOver(){
		return gameOver;
	}

	public int update(char letter, int level){



		switch (level) {
		case 0:
			moveHero(letter,level);
			moveGuard();
			gameOver=checkGuard();
			break;
		case 1:
			moveHero(letter,level);
			boolean validMove;
			do{
				validMove=moveOgre();
			}
			while(!validMove);
			gameOver=checkOgre();
			break;
		default:
			break;
		}


		if(victory){
			if(level==1){
				gameOver=true;
				return level;
			}
			level++;
			victory=false;
			initializeUnits(level);
			setMap(level);
		}
		return level;
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
		int[] newPos= hero.getPosition().clone();

		newPos[0] += pos[0];
		newPos[1] += pos[1];

		if(map.isFree(newPos[0],newPos[1])){
			hero.setPosition(newPos[0], newPos[1]);
			return true;
		}


		switch (level) {
		case 0:
			if(map.getMap()[newPos[0]][newPos[1]]=='k'){
				//hero.setLever();
				map.setMap(5, 0, 'S');
				map.setMap(6, 0, 'S');
				hero.setPosition(newPos[0], newPos[1]);
				return true;
			}		
			if(map.getMap()[newPos[0]][newPos[1]]=='S'){
				victory=true;
				hero.setPosition(newPos[0], newPos[1]);
				return true;
			}
			break;
		case 1:
			if(map.getMap()[newPos[0]][newPos[1]]=='k'){
				hero.setLever();
				map.setMap(newPos[0], newPos[1], ' ');
				hero.setPosition(newPos[0], newPos[1]);
				hero.setUnit('K');
				return true;
			}	
			if(map.getMap()[newPos[0]][newPos[1]]=='S'){
				victory=true;
				hero.setPosition(newPos[0], newPos[1]);
				return true;
			}
			if(map.getMap()[newPos[0]][newPos[1]]=='I' && hero.getLever()){
				hero.setLever();
				map.setMap(newPos[0],newPos[1],'S');
				hero.setUnit('H');
			}

			break;
		default:
			break;
		}



		return false;
	}

	public boolean moveGuard(){
		int[] newPos= guard.getPosition().clone();

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
		int[] newPos= ogre.getPosition().clone();



		newPos[0] += pos[0];
		newPos[1] += pos[1];

		if(map.isFree(newPos[0],newPos[1])){
			ogre.setPosition(newPos[0], newPos[1]);

			if(ogre.getLever()){
				ogre.setLever();
				ogre.setUnit('O');
			}

			if(ogre.getClub()){

				boolean validMove=false;
				int[] posClub = new int[2];
				ogre.setClubUnit('*');
				do{
					i = rn.nextInt(4);
					posClub = convertCommandToArray(moves[i]);
					posClub[0] += newPos[0];
					posClub[1] += newPos[1];
					if(map.isFree(posClub[0],posClub[1]))
						validMove=true;
					else if(map.getMap()[posClub[0]][posClub[1]]=='k'){
						ogre.setClubUnit('$');
						validMove=true;
					}


				}
				while(!validMove);

				ogre.setPosClub(posClub[0],posClub[1]);
			}

			return true;
		}

		if(map.getMap()[newPos[0]][newPos[1]]=='k'){
			ogre.setLever();
			ogre.setPosition(newPos[0], newPos[1]);
			ogre.setUnit('$');

			if(ogre.getClub()){

				boolean validMove=false;
				int[] posClub = new int[2];
				do{
					i = rn.nextInt(4);
					posClub = convertCommandToArray(moves[i]);
					posClub[0] += newPos[0];
					posClub[1] += newPos[1];
					if(map.isFree(posClub[0],posClub[1]))
						validMove=true;
				}
				while(!validMove);

				ogre.setPosClub(posClub[0],posClub[1]);
			}


			return true;
		}	
		
		return false;
	}

	public boolean checkGuard(){
		int[] posH= hero.getPosition();
		int[] posG= guard.getPosition(); 

		if(	(posH[0]-1 == posG[0] && posH[1]==posG[1]) ||
				(posH[0]+1 == posG[0]&& posH[1]==posG[1])||
				(posH[0] == posG[0] && posH[1]-1 == posG[1]) || 
				(posH[0] == posG[0] && posH[1]+1 == posG[1])||
				(posH[0]== posG[0] && posH[1]== posG[1]))
			return true;
		else
			return false;
	} 


	public boolean checkOgre(){
		int[] posH= hero.getPosition();
		int[] posO= ogre.getPosition();

		if(checkClub())
			return true;


		if(	(posH[0]-1 == posO[0] && posH[1]==posO[1]) ||
				(posH[0]+1 == posO[0]&& posH[1]==posO[1])||
				(posH[0] == posO[0] && posH[1]-1 == posO[1]) || 
				(posH[0] == posO[0] && posH[1]+1 == posO[1])||
				(posH[0]== posO[0] && posH[1]== posO[1]))
			return true;
		else
			return false;
	} 

	public boolean checkClub(){
		int[] posH= hero.getPosition();
		int[] posC= ogre.getPosClub();

		if(	(posH[0]-1 == posC[0] && posH[1]==posC[1]) ||
				(posH[0]+1 == posC[0]&& posH[1]==posC[1])||
				(posH[0] == posC[0] && posH[1]-1 == posC[1]) || 
				(posH[0] == posC[0] && posH[1]+1 == posC[1])||
				(posH[0]== posC[0] && posH[1]== posC[1]))
			return true;
		else
			return false;


	} 
	//Falta o check Club


	public char[][] updateMap(int level){

		char [][]copyMap=map.getMap();

		int[] posH= hero.getPosition();


		switch (level) {
		case 0:
			int[] posG= guard.getPosition();
			copyMap[posH[0]][posH[1]]=hero.getUnit();
			copyMap[posG[0]][posG[1]]=guard.getUnit();
			break;
		case 1:
			int[] posO= ogre.getPosition();
			copyMap[posH[0]][posH[1]]=hero.getUnit();
			copyMap[posO[0]][posO[1]]=ogre.getUnit();
			if(ogre.getClub()){
				int[] posC= ogre.getPosClub();
				copyMap[posC[0]][posC[1]]=ogre.getClubUnit();
			}
			break;
		default:
			break;
		}
		return copyMap;
	}

}