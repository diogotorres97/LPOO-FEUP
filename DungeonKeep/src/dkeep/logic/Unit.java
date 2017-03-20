package dkeep.logic;

import java.io.Serializable;

public abstract class Unit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		return pos.clone();
	}
	
	public char getUnit(){
		return unit;
	}
	
	public void setUnit(char unit){
		this.unit=unit;
	}
}