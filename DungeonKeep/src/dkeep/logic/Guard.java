package dkeep.logic;

import java.io.Serializable;

/**
 * 
 *  Class that handles guards
 *
 */
public class Guard extends Unit implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	char routeGuard[] = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	char reverseRouteGuard[]=new char [routeGuard.length];
	GuardStrategy strategy;
	int numStrategy; 
	int indexActual;
	boolean hasResetIndex;

	/**
	 * @brief Constructor
	 * @param strategy to be set
	 */
	public Guard(GuardStrategy strategy) {

		unit='G';
		this.strategy=strategy;
		indexActual=0;
		reverseRoute(); 
	}
	/**
	 * @brief Gets the strategy
	 * @return
	 */
	public GuardStrategy getStrategy(){
		return strategy;
	} 
	/**
	 * @brief Gets number of strategy
	 * @return
	 */
	public int getNumStrategy(){
		return numStrategy;
	}
	/**
	 * @brief Sets the number of strategy
	 * @param num - num to be set
	 */
	public void setNumStrategy(int num){
		numStrategy=num;
	}
	/**
	 * @brief Gets the current route char
	 * @param index - of the guard
	 * @return
	 */
	public char getActualRoute(int index){

		if(strategy.getIsRevert())
			return reverseRouteGuard[index]; 
		else 
			return routeGuard[index]; 
	}
	/**
	 * @brief Gets route size
	 * @return
	 */
	public int getRouteSize(){
		return routeGuard.length;
	}
	/**
	 * @brief Increases index
	 */
	public void increaseIndex(){
		if(strategy.getIsRevert())
			indexActual--;
		else
			indexActual++;
	}
	/**
	 * @brief Gets index
	 * @return
	 */
	public int getIndex(){
		return indexActual;
	}
	/**
	 * @brief Resets index
	 */
	public void resetIndex(){
		if(strategy.getIsRevert())
			indexActual=getRouteSize()-1; 
		else 
			indexActual=0;
		hasResetIndex=true;   
	}  
	/**
	 * @brief Gets if the index has been reset
	 * @return
	 */
	public boolean getHasResetIndex(){
		return hasResetIndex;
	}
	/**
	 * @brief Creates the reverse route
	 */
	public void reverseRoute(){
		for(int i=0;i<routeGuard.length; i++){
			switch(routeGuard[i]){
			case 'w':
				reverseRouteGuard[i]='s';
				break;
			case 's':
				reverseRouteGuard[i]='w';
				break;
			case 'a':
				reverseRouteGuard[i]='d';
				break;
			case 'd':
				reverseRouteGuard[i]='a';
				break;
			}
		}
	}
}
