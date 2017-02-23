package dkeep.logic;

public abstract class Unit {
	
	protected int [] pos= new int [2];
	protected char unit;
	static char moves[]= {'w','a','s','d'};

	
	public Unit() {
		
		
	}
	
	public void setPosition(int x, int y){
		pos[0]=x;
		pos[1]=y;
	}
	
	public int[] getPosition(){
		return pos;
	}

}