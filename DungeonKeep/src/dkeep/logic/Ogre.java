package dkeep.logic;

public class Ogre extends Unit{

	static boolean hasLever;
	
	public Ogre() {

		unit='O';
		hasLever=false;
	}

	public boolean getLever(){
		return hasLever;
	}

	public void setLever(){
		hasLever = !hasLever;
		unit='$';
	}

	
}
