package dkeep.logic;

public class Hero extends Unit{

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
