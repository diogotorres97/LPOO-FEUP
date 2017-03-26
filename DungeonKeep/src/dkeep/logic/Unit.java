package dkeep.logic;

import java.io.Serializable;

/**
 * 
 *  Class that handles units, super class
 *
 */
public abstract class Unit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int [] pos= new int [2];
	protected char unit;
	char moves[]= {'w','a','s','d'};
	/**
	 * @brief Constructor
	 */
	public Unit() {

	}
	/**
	 * @brief Sets unit position to the new one
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		pos[0]=x;
		pos[1]=y;
	}
	/**
	 * @brief Gets position
	 * @return
	 */
	public int[] getPosition(){
		return pos.clone();
	}
	/**
	 * Gets the unit char
	 * @return
	 */
	public char getUnit(){
		return unit;
	}
	/**
	 * @brief Sets the unit char
	 * @param unit
	 */
	public void setUnit(char unit){
		this.unit=unit;
	}
}