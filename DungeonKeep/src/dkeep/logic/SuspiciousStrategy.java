package dkeep.logic;

public class SuspiciousStrategy extends GuardStrategy{
	
	public SuspiciousStrategy(){
		isAsleep=false;
		revertsRoute=false;
	}

	public void setTime(){
		time--;
		if(time==0){
			time=2;
		}
	}
}
