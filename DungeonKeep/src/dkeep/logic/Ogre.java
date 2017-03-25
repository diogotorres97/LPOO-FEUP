package dkeep.logic;

public class Ogre extends Unit{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean hasLever;
	boolean hasClub;
	int [] posClub= new int [2];
	char clubUnit;
	int stunnedTime;
	boolean stunned;

	/**
	 * Ogre constructor
	 * @param club, 1 if club is activated, 0 otherwise
	 */
	public Ogre(int club) {

		stunnedTime=2;
		stunned=false;
		unit='O';
		hasLever=false;
		if (club==1)
			hasClub=true;
		else  
			hasClub=false;
		clubUnit='*';

	}

	/**
	 * Gets if Ogre has lever
	 * @return true or false
	 */
	public boolean getLever(){
		return hasLever;
	} 

	/**
	 * Set ogre lever
	 */
	public void setLever(){
		hasLever = !hasLever;

	}

	/**
	 * Gets if ogre has a club
	 * @return true or false
	 */
	public boolean getClub(){
		return hasClub;
	}

	/**
	 * Set Club Position
	 * @param x
	 * @param y
	 */
	public void setPosClub(int x, int y){
		posClub[0]=x;
		posClub[1]=y;
	}

	/**
	 * Get actual position of club of certain ogre
	 * @return array with x and y position
	 */
	public int[] getPosClub(){
		return posClub.clone();
	}

	/**
	 * Get symbol of Club
	 * @return symbol
	 */
	public char getClubUnit(){
		return clubUnit;
	}

	/**
	 * Set symbol of club
	 * @param unit
	 */
	public void setClubUnit(char unit){
		clubUnit=unit;
	}

	/**
	 * get stunned time
	 * @return stunned time
	 */
	public int getStunnedTime(){
		return stunnedTime;
	}

	/**
	 * set stunned time
	 */
	public void setStunnedTime(){
		stunnedTime--; 
		if(stunnedTime==0){
			stunnedTime=2;
			setStunned(); 
		}
	}

	/**
	 * set ogre stunned
	 */
	public void setStunned(){
		stunned = !stunned;
	}

	/**
	 * get ogre stunned 
	 * @return true if ogre get stunned, false otherwise
	 */
	public boolean getStunned(){
		return stunned;
	}

	/**
	 * Increase time of stunned
	 */
	public void setMoreStunnedTime(){
		stunnedTime++;
	}

}
