package dkeep.logic;

public class Ogre extends Unit{

	static boolean hasLever;
	static boolean hasClub;
	int [] posClub= new int [2];
	char clubUnit;
	int stunnedTime;
	boolean stunned;
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
		return posClub.clone();
	}
	
	public char getClubUnit(){
		return clubUnit;
	}
	
	public void setClubUnit(char unit){
		clubUnit=unit;
	}
	
	public int getStunnedTime(){
		return stunnedTime;
	}
	
	public void setStunnedTime(){
		stunnedTime--;
		if(stunnedTime==0){
			stunnedTime=2;
			setStunned();
		}
	}
	
	public void setStunned(){
		stunned = !stunned;
	}
	
	public boolean getStunned(){
		return stunned;
	}
	
	public void setMoreStunnedTime(){
		stunnedTime++;
	}

}
