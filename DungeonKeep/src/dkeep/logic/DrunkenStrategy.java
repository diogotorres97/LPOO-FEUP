package dkeep.logic;

import java.util.Random;

public class DrunkenStrategy extends GuardStrategy{

	public DrunkenStrategy(){
		isAsleep=false;
		revertsRoute=false;
	}

	public void setTime(){
		time--;
		if(time==0){
			setAsleep(); 
			time=2;
			Random rn = new Random();
			int i = rn.nextInt(2);
 
			if(i==1){
				setRevert(); 
				setHasReverted();
			}
		}
	}
}
