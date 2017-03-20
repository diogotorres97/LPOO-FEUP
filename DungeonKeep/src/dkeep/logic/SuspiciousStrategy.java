package dkeep.logic;

public class SuspiciousStrategy extends GuardStrategy{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
