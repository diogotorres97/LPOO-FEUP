package dkeep.logic;

public class Game {

	private Hero hero;
	private Guard guard;
	private Ogre ogre;
	private GameMap map; 
	private boolean victory;
	private boolean gameOver;
	
	public Game (GameMap startMap){
		hero=new Hero();
		guard=new Guard();
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
		
		
	return 0;	
	}
	
	public char[][] getGameMap()	{
		return map.getMap();

	}

	public boolean gameWin(){
		return victory;
	}	
}