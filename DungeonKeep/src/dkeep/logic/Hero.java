package dkeep.logic;

public class Hero extends Unit{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static boolean hasLever;

	public Hero() {
		unit='H';
		hasLever=false;
	}

	public boolean getLever(){
		return hasLever;
	}

	public void setLever(){
		hasLever = !hasLever;
		if(hasLever)
			unit = 'K';
		else
			unit='A';
	}
}
