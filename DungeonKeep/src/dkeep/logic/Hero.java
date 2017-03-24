package dkeep.logic;

public class Hero extends Unit{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean hasLever;
/**
 * @brief Constructor
 */
	public Hero() {
		unit='H';
		hasLever=false;
	}
/**
 * @brief Gets if the hero has lever
 * @return
 */
	public boolean getLever(){
		return hasLever;
	}
/**
 * @brief Sets hasLever
 */
	public void setLever(){
		hasLever = !hasLever;
		if(hasLever) 
			unit = 'K'; 
		else
			unit='A';
	}
}
