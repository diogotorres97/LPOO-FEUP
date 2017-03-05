package dkeep.logic;


public class GuardStrategy {

	protected boolean revertsRoute;
	protected boolean isAsleep;
	protected boolean hasReverted;
	protected int time=2;

	public boolean getIsRevert(){
		return revertsRoute;
	}

	public void setRevert(){
		revertsRoute = !revertsRoute;
	}
	
	public boolean getIsAsleep(){
		return isAsleep;
	}
	
	public void setAsleep(){
		isAsleep = !isAsleep;
	}
	
	public int getTime(){  
		return time;
	} 
	
	public void setTime(){}
	
	public boolean getHasReverted(){
		return hasReverted;
	}
	
	public void setHasReverted(){
		hasReverted= !hasReverted;
	}

}
