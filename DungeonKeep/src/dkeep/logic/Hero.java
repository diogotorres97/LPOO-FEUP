package dkeep.logic;

public class Hero extends Unit{

	static boolean alive;
	static boolean hasLever;

	public Hero() {

		unit='H';
		alive=true;
		hasLever=false;
	}

	public boolean getAlive(){
		return alive;
	}

	public void setAlive(){
		alive = !alive;
	}

	public boolean getLever(){
		return hasLever;
	}

	public void setLever(){
		hasLever = !hasLever;
		if(hasLever)
			unit = 'K';
		else
			unit='H';
	}

}
