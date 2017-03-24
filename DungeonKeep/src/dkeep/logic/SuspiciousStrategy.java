package dkeep.logic;

public class SuspiciousStrategy extends GuardStrategy{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @brief Constructor
	 */
	public SuspiciousStrategy(){
		isAsleep=false;
		revertsRoute=false;
	}

	/* (non-Javadoc)
	 * @see dkeep.logic.GuardStrategy#setTime()
	 */
	public void setTime(){
		time--;  
		if(time==0){
			time=2; 
		}
	} 
} 
