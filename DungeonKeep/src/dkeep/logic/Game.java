package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hero hero;
	private Guard guard;
	private Ogre ogre;
	private GameMap map;
	private GameMap[] maps = new GameMap [2];
	private GuardStrategy[] strategies = new GuardStrategy [3];
	private boolean victory;
	private boolean gameOver;
	private int levelGame;
	private ArrayList<Ogre> ogreMilitia = new ArrayList<Ogre>();
	private char moves[]= {'w','a','s','d'};

	/**
	 * @brief Constructor of game
	 * 	
	 * @param map to be set
	 * @param club, if 1 active club otherwise club it's deactivated 
	 */

	public Game (GameMap map, int club){
		this.map=map;
		hero=new Hero();
		ogre = new Ogre(club); 
		guard = new Guard(new DrunkenStrategy());
		guard.setNumStrategy(1);
		if(map.getHeroPos() != null)
			hero.setPosition(map.getHeroPos()[0], map.getHeroPos()[1]);
		if(map.getGuardPos() != null)
			guard.setPosition(map.getGuardPos()[0], map.getGuardPos()[1]);
		if(map.getOgrePos() != null)
			ogre.setPosition(map.getOgrePos()[0][0], map.getOgrePos()[0][1]);	

		maps[1] = new KeepMap();
	}

	/**
	 * @brief Constructor of game with 3 parameters
	 * @param level
	 * @param guard_personality
	 * @param num_ogres
	 */

	public Game (int level, int guard_personality, int num_ogres){

		hero=new Hero(); 


		initStrategies();

		guard=new Guard(strategies[guard_personality]);
		guard.setNumStrategy(guard_personality);

		for (int j=0; j<num_ogres; j++){
			ogreMilitia.add(new Ogre(1));
		}

		victory=false;
		gameOver=false;

		maps[0] = new DungeonMap();
		maps[1] = new KeepMap();
		map= maps[level];

		initializeUnits(level);
	}


	private void initStrategies(){
		strategies[0]=new RookieStrategy();
		strategies[1]=new DrunkenStrategy();
		strategies[2]=new SuspiciousStrategy();
	}

	private void initializeUnits(int level){
		switch (level) {
		case 0:
			initL0();
			break;
		case 1:
			initL1();
			break;
		default:
			break;
		}

	} 

	/*Initialize Units Levels*/

	private void initL0(){
		hero.setPosition(maps[0].getHeroPos()[0], maps[0].getHeroPos()[1]);
		guard.setPosition(maps[0].getGuardPos()[0], maps[0].getGuardPos()[1]);
	}

	private void initL1(){
		hero.setPosition(maps[1].getHeroPos()[0], maps[1].getHeroPos()[1]);
		hero.setUnit('A');
		for(int i=0;i<ogreMilitia.size();i++){
			ogreMilitia.get(i).setPosition(maps[1].getOgrePos()[i][0], maps[1].getOgrePos()[i][1]);
			ogreMilitia.get(i).setPosClub(ogreMilitia.get(i).getPosition()[0], ogreMilitia.get(i).getPosition()[1]+1);
		}
	}

	/**
	 * @brief Set map in game
	 * @param level
	 * @param newMap
	 */
	public void setMap(int level, char[][] newMap){
		if(newMap==null)
			this.map=maps[level];
		else
			this.maps[level].setNewMap(newMap);
	}

	/**
	 * @brief Get Current Map
	 * @return current map
	 */
	public GameMap getCurrentMap(){
		return this.map;
	}

	/**
	 * @brief Checks if game is over
	 * @return true if game is over, otherwise return false
	 */
	public boolean isGameOver(){
		return gameOver;
	}

	/**
	 * @brief Manage all game with movements of characters and updating the game state
	 * @param letter, it's the command launched by user to move hero
	 * @param level, actual level
	 * @return
	 */

	public int update(char letter, int level){

		switch (level) {
		case 0:
			updateL0(letter, level);
			break;
		case 1:
			updateL1(letter, level);
			break;
		default:
			break;
		}

		levelGame=updateVictory(level);
		level=levelGame;
		return level;
	}

	/**
	 * @brief Get actual map with positions of characters 
	 * @param level, actual level game
	 * @return complete map
	 */
	public char[][] getGameMap(int level)	{
		return updateMap(level);
	}

	/**
	 * @brief Check if user win the level
	 * @return true if win, otherwise returns false
	 */
	public boolean gameWin(){
		return victory;
	}	

	/**
	 * @brief Gets actual level game
	 * @return level game
	 */
	public int getLevelGame(){
		return levelGame;
	}


	/* levels*/
	private void updateL0(char letter, int level){
		moveHero(letter,level);
		moveGuard(); 
		gameOver=checkGuard();
	}

	private void updateL1(char letter, int level){
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
	}

	private int updateVictory(int level) {
		if(victory){
			if(level==1){
				gameOver=true;
				return level;
			}
			level++;
			victory=false;
			initializeUnits(level);
			setMap(level, null);
		}
		return level;
	}
	/*----------------------------------------------------------------*/
	//Movement

	private static int[] convertCommandToArray(char command){
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

	/**
	 * @brief update hero position depending on a command introduced by user and actual level
	 * @param command
	 * @param level
	 * @return true if movement is possible, otherwise return false
	 */

	public boolean moveHero(char command, int level){
		int[] pos = convertCommandToArray(command);
		int[] newPos= hero.getPosition();

		newPos[0] += pos[0];
		newPos[1] += pos[1]; 

		if(!checkOgreSamePos(newPos))
			return false;

		if(moveHeroFree(newPos))
			return true;

		switch (level) {
		case 0:
			if(moveHeroL0(newPos))
				return true;
		case 1:
			if(moveHeroL1(newPos))
				return true;
		default:
			break;
		}
		return false;
	}

	/*MoveHero Levels*/

	private boolean moveHeroFree(int[] newPos){
		if(map.isFree(newPos[0],newPos[1])){
			hero.setPosition(newPos[0], newPos[1]);
			return true; 
		}
		return false;
	}

	private boolean checkOgreSamePos(int[] newPos){
		for(int i=0;i<ogreMilitia.size();i++){
			int [] posO = ogreMilitia.get(i).getPosition();
			if(newPos[0]==posO[0] && newPos[1]==posO[1])
				return false;
		} 
		return true;
	}

	private boolean moveHeroL0(int [] newPos){
		if(map.getMap()[newPos[0]][newPos[1]]=='k'){
			checkDoorL0();
			hero.setPosition(newPos[0], newPos[1]);
			return true;
		}		
		if(map.getMap()[newPos[0]][newPos[1]]=='S'){
			victory=true;
			hero.setPosition(newPos[0], newPos[1]);
			return true;
		}

		return false;
	}

	private void checkDoorL0(){
		int [] test;
		int flag;
		do{
			test= map.checkDoorPosition('I');
			if(test!= null){
				map.setMap(test[0],test[1],'S');
				flag=1;
			}
			else 
				flag=0;

		}while(flag!=0);
	}

	private boolean moveHeroL1(int[] newPos){
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
		return false;
	}

	/**
	 * @brief update guard position depending on is type
	 */
	public void moveGuard(){
		switch(guard.getNumStrategy()){
		case 0:
			moveGuardS0(guard.getPosition());
			break;
		case 1: 
			moveGuardS1(guard.getPosition());
			break;
		case 2: 
			moveGuardS2(guard.getPosition());
			break;
		default:
			break;
		}

	}


	/*MoveGuard Strategies*/

	private void moveGuardS0(int[] newPos){
		int[] pos = new int[2];
		pos = convertCommandToArray(guard.getActualRoute(guard.getIndex()));
		guard.increaseIndex();
		if(guard.getIndex() == guard.getRouteSize())
			guard.resetIndex();

		newPos[0] += pos[0];
		newPos[1] += pos[1];
		guard.setPosition(newPos[0], newPos[1]);
	}

	private void moveGuardS1(int [] newPos){
		int[] pos = new int[2];
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
	}

	private void moveGuardS2(int[] newPos){
		int[] pos = new int[2];
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
	}

	/**
	 * @brief update ogre and your club position 
	 * @param ogre
	 * @return true if movement is possible, false otherwise
	 */
	public boolean moveOgre(Ogre ogre){
		Random rn = new Random();
		int i = rn.nextInt(4);

		boolean isValidOgreMove=false;

		int[] pos = convertCommandToArray(moves[i]);
		int[] newPos= ogre.getPosition();

		if(ogre.getStunned()){
			ogre.setStunnedTime();
			isValidOgreMove=true;
		} 
		else{

			isValidOgreMove = moveOgreAux(ogre, newPos, pos, isValidOgreMove);
			if(checkOgre(ogre, 1)){
				ogre.setUnit('8');
				ogre.setStunned();
			}
		}

		moveClub(ogre, newPos,isValidOgreMove);


		return isValidOgreMove;
	}

	private boolean moveOgreAux(Ogre ogre, int [] newPos, int[] pos, boolean isValidOgreMove){


		newPos[0] += pos[0];
		newPos[1] += pos[1];

		int [] posH = hero.getPosition();
		if(newPos[0]==posH[0] && newPos[1]==posH[1])
			return false;

		if(map.isFree(newPos[0],newPos[1]) || map.getMap()[newPos[0]][newPos[1]]=='O' || map.getMap()[newPos[0]][newPos[1]]=='$'){
			ogre.setPosition(newPos[0], newPos[1]);
			ogre.setUnit('O');

			if(ogre.getLever())
				ogre.setLever();

			return true;
		}

		if(map.getMap()[newPos[0]][newPos[1]]=='k'){
			ogre.setLever();
			ogre.setPosition(newPos[0], newPos[1]);
			ogre.setUnit('$');

			return true;
		}
		return false;
	}

	private void moveClub(Ogre ogre, int[] newPos,boolean isValidOgreMove){
		if(ogre.getClub() &&  isValidOgreMove){

			int[] posClub = new int[2];
			ogre.setClubUnit('*');

			Random rn = new Random();
			int i = rn.nextInt(4);

			moveClubValid(ogre, rn, i, posClub, newPos);


		}
	}
	private void moveClubValid(Ogre ogre, Random rn, int i, int[] posClub, int[] newPos){
		boolean validMove=false;
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

	/**
	 * @brief Check if Guard is near of Hero
	 * @return true if guard is near of hero, false otherwise
	 */
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

	/**
	 * @brief Check if Guard is near of Hero
	 * @param ogre
	 * @param mode, 0 if check only club is near of Hero or 1 if check only Ogre is near of hero
	 * @return true if character is near of hero, false otherwise
	 */
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

	private boolean checkClub(Ogre ogre){
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

	/**
	 * @brief Update map depending on level
	 * @param level
	 * @return updated map with all characters
	 */
	public char[][] updateMap(int level){

		char [][]copyMap=map.getMap();
		int[] posH= hero.getPosition();

		switch (level) {
		case 0:
			copyMap = updateMapL0(copyMap, posH);
			break;
		case 1:
			copyMap = updateMapL1(copyMap, posH);
			break;
		default:
			break;
		}
		return copyMap;
	}

	/*Update Map Levels*/

	private char[][] updateMapL0(char[][]copyMap, int[] posH){
		int[] posG= guard.getPosition();
		copyMap[posH[0]][posH[1]]=hero.getUnit();
		copyMap[posG[0]][posG[1]]=guard.getUnit();
		return copyMap;
	}

	private char[][] updateMapL1(char[][]copyMap, int[] posH){
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
		return copyMap;
	}

	/**
	 * @brief Gets the character hero
	 * @return hero
	 */
	public Hero getHero(){ 
		return hero;
	}

	/**
	 * @brief Gets the character ogre
	 * @return ogre
	 */
	public Ogre getOgre(){
		return ogre;
	}
	/**
	 * @brief Gets the character guard
	 * @return guard
	 */
	public Guard getGuard(){
		return guard;
	}
	/**
	 * @brief Gets the array of ogre's
	 * @return array of ogre's
	 */
	public ArrayList<Ogre> getMilitia(){
		return ogreMilitia;
	}
}
