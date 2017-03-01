package dkeep.logic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {

	private Hero hero;
	private Guard guard;
	//private Ogre ogre;
	private GameMap map;
	private GameMap[] maps = new GameMap [2];
	private GuardStrategy[] strategies = new GuardStrategy [3];
	private boolean victory;
	private boolean gameOver;

	private ArrayList<Ogre> ogreMilitia = new ArrayList<Ogre>();

	public Game (int level){

		Random rn = new Random();
		int i = rn.nextInt(3);

		hero=new Hero();
		
		if (level==0){
			strategies[0]=new RookieStrategy();
			strategies[1]=new DrunkenStrategy();
			strategies[2]=new SuspiciousStrategy();
			guard=new Guard(strategies[i]);
			guard.setNumStrategy(i);
		}
		//ogre= new Ogre(1);

		int maxOgre = rn.nextInt(2);
	
		for (int j=0; j<=maxOgre; j++){
			ogreMilitia.add(new Ogre(1));
		}

		//ogreMilitia.addAll(Arrays.asList(new Ogre(1), new Ogre(1), new Ogre(1), new Ogre(1)));

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
			hero.setUnit('A');
			for(int i=0;i<ogreMilitia.size();i++){
				ogreMilitia.get(i).setPosition(maps[1].getOgrePos()[0], maps[1].getOgrePos()[1]);
				ogreMilitia.get(i).setPosClub(ogreMilitia.get(i).getPosition()[0], ogreMilitia.get(i).getPosition()[1]+1);
			}
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

			for(int i=0;i<ogreMilitia.size();i++){
				boolean validMove;

				do{
					validMove=moveOgre(ogreMilitia.get(i));
				}
				while(!validMove);	
				gameOver=checkOgre(ogreMilitia.get(i), 0);
				if(gameOver==true)
					break;
			}
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
		int[] newPos= hero.getPosition();

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
				hero.setUnit('A');
			}
			break;
		default:
			break;
		}
		return false;
	}

	public boolean moveGuard(){
		int[] newPos= guard.getPosition();
		int[] pos = new int[2];

		switch(guard.getNumStrategy()){
		case 0:
			pos = convertCommandToArray(guard.getActualRoute(guard.getIndex()));
			guard.increaseIndex();
			if(guard.getIndex() == guard.getRouteSize())
				guard.resetIndex();

			newPos[0] += pos[0];
			newPos[1] += pos[1];
			guard.setPosition(newPos[0], newPos[1]);
			break;
		case 1:
			if(guard.getStrategy().getIsAsleep()){
				guard.getStrategy().setTime();
				guard.setUnit('g');
			}
			else {
				if(guard.getStrategy().getHasReverted()){
					guard.increaseIndex();
					guard.getStrategy().setHasReverted();
				}

				if((guard.getIndex() == guard.getRouteSize()) || (guard.getIndex()==-1))
					guard.resetIndex();

				pos = convertCommandToArray(guard.getActualRoute(guard.getIndex()));
				guard.increaseIndex();

				newPos[0] += pos[0];
				newPos[1] += pos[1];
				guard.setPosition(newPos[0], newPos[1]);
				guard.setUnit('G');

				Random rn = new Random();
				int i = rn.nextInt(2);

				if(i==1)
					guard.getStrategy().setAsleep();
			}
			break;
		case 2:
			guard.getStrategy().setTime();

			Random rn = new Random();
			int i = rn.nextInt(2);

			if(i==1 && guard.getStrategy().getTime()==2){
				guard.getStrategy().setRevert();
				guard.getStrategy().setHasReverted();
			}

			if(guard.getStrategy().getHasReverted()){
				guard.increaseIndex();
				guard.getStrategy().setHasReverted();
			}

			pos = convertCommandToArray(guard.getActualRoute(guard.getIndex()));
			guard.increaseIndex();

			if((guard.getIndex() == guard.getRouteSize()) || (guard.getIndex()==-1))
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

	public boolean moveOgre(Ogre ogre){
		Random rn = new Random();
		int i = rn.nextInt(4);

		boolean isValidOgreMove=false;

		char moves[]= {'w','a','s','d'};

		int[] pos = convertCommandToArray(moves[i]);
		int[] newPos= ogre.getPosition();

		newPos[0] += pos[0];
		newPos[1] += pos[1];

		if(ogre.getStunned()){
			ogre.setStunnedTime();
			isValidOgreMove=true;
		}
		else{
			if(map.isFree(newPos[0],newPos[1]) || map.getMap()[newPos[0]][newPos[1]]=='O' || map.getMap()[newPos[0]][newPos[1]]=='$'){
				ogre.setPosition(newPos[0], newPos[1]);
				ogre.setUnit('O');

				if(ogre.getLever())
					ogre.setLever();

				isValidOgreMove=true;
			}

			if(map.getMap()[newPos[0]][newPos[1]]=='k'){
				ogre.setLever();
				ogre.setPosition(newPos[0], newPos[1]);
				ogre.setUnit('$');

				isValidOgreMove=true;
			}

			if(checkOgre(ogre, 1)){
				ogre.setUnit('8');
				ogre.setStunned();
			}
		}

		if(ogre.getClub() &&  isValidOgreMove){
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
		return isValidOgreMove;
	}

	public boolean checkGuard(){
		int[] posH= hero.getPosition();
		int[] posG= guard.getPosition(); 

		if(	(posH[0]-1 == posG[0] && posH[1]==posG[1] && guard.getUnit()=='G') ||
				(posH[0]+1 == posG[0]&& posH[1]==posG[1]  && guard.getUnit()=='G')||
				(posH[0] == posG[0] && posH[1]-1 == posG[1]  && guard.getUnit()=='G') || 
				(posH[0] == posG[0] && posH[1]+1 == posG[1]  && guard.getUnit()=='G')||
				(posH[0]== posG[0] && posH[1]== posG[1] && guard.getUnit()=='G'))
			return true;
		else
			return false;
	} 

	public boolean checkOgre(Ogre ogre, int mode){ //mode: if it is to check gameOver or to stun the ogre
		int[] posH= hero.getPosition();
		int[] posO= ogre.getPosition();

		if(mode==0)
			if(ogre.getClub() && checkClub(ogre))
				return true;

		if(	(posH[0]-1 == posO[0] && posH[1]==posO[1] && ogre.getUnit()=='O') ||
				(posH[0]+1 == posO[0]&& posH[1]==posO[1] && ogre.getUnit()=='O')||
				(posH[0] == posO[0] && posH[1]-1 == posO[1] && ogre.getUnit()=='O') || 
				(posH[0] == posO[0] && posH[1]+1 == posO[1] && ogre.getUnit()=='O')||
				(posH[0]== posO[0] && posH[1]== posO[1] && ogre.getUnit()=='O'))
			return true;
		else
			return false;
	} 

	public boolean checkClub(Ogre ogre){
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
			copyMap[posH[0]][posH[1]]=hero.getUnit();
			int[] posO = new int [2];
			for(int i=0;i<ogreMilitia.size();i++){
				posO= ogreMilitia.get(i).getPosition();
				copyMap[posO[0]][posO[1]]=ogreMilitia.get(i).getUnit();
				if(ogreMilitia.get(i).getClub()){
					int[] posC= ogreMilitia.get(i).getPosClub();
					copyMap[posC[0]][posC[1]]=ogreMilitia.get(i).getClubUnit();
				}
			}
			break;
		default:
			break;
		}
		return copyMap;
	}

}