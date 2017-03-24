package dkeep.logic;

import java.io.Serializable;

public class GuardStrategy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean revertsRoute;
	protected boolean isAsleep;
	protected boolean hasReverted;
	protected int time=2;
	/**
	 * @brief Gets if the route is revert
	 * @return
	 */
	public boolean getIsRevert(){
		return revertsRoute;
	}
	/**
	 * @brief Sets revertRoute
	 */
	public void setRevert(){
		revertsRoute = !revertsRoute;
	}  
	/**
	 * @brief Gets if guard is asleep
	 * @return
	 */
	public boolean getIsAsleep(){
		return isAsleep;
	}
	/**
	 * @brief Sets isAsleep
	 */
	public void setAsleep(){ 
		isAsleep = !isAsleep;
	}
	/**
	 * @brief Gets the time
	 * @return
	 */
	public int getTime(){  
		return time;
	}  
	/**
	 * @brief Sets the time
	 */
	public void setTime(){}
	/**
	 * @brief Gets if the route has reverted
	 * @return
	 */
	public boolean getHasReverted(){
		return hasReverted; 
	}
/**
 * @brief Sets hasReverted
 */
	public void setHasReverted(){
		hasReverted= !hasReverted;
	}

}
