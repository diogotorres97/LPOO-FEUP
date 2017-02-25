package dkeep.logic;

public class Ogre extends Unit{

	static boolean hasLever;
	static boolean hasClub;
	int [] posClub= new int [2];
	char clubUnit;
	public Ogre(int club) {

		unit='O';
		hasLever=false;
		if (club==1)
			hasClub=true;
		else 
			hasClub=false;
		clubUnit='*';
	}

	public boolean getLever(){
		return hasLever;
	}

	public void setLever(){
		hasLever = !hasLever;
		unit='$';
	}

	public void setClub(){
		hasClub=!hasClub;
	}

	public boolean getClub(){
		return hasClub;
	}
	
	public void setPosClub(int x, int y){
		posClub[0]=x;
		posClub[1]=y;
	}
	
	public int[] getPosClub(){
		return posClub;
	}
	
	public char getClubUnit(){
		return clubUnit;
	}

}
