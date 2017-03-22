package dkeep.logic;

public class Guard extends Unit{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static char routeGuard[] = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	static char reverseRouteGuard[]=new char [routeGuard.length];
	static GuardStrategy strategy;
	static int numStrategy; 
	static int indexActual;
	static boolean hasResetIndex;
 
	public Guard(GuardStrategy strategy) {

		unit='G';
		Guard.strategy=strategy;
		indexActual=0;
		reverseRoute(); 

	}
	public GuardStrategy getStrategy(){
		return strategy;
	} 

	public int getNumStrategy(){
		return numStrategy;
	}

	public void setNumStrategy(int num){
		numStrategy=num;
	}

	public char getActualRoute(int index){
		 
		if(strategy.getIsRevert())
			return reverseRouteGuard[index]; 
		else 
			return routeGuard[index]; 
	}

	public int getRouteSize(){
		return routeGuard.length;
	}
 
	public void increaseIndex(){
		if(strategy.getIsRevert())
			indexActual--;
		else
			indexActual++;
	}

	public int getIndex(){
		return indexActual;
	}
	public void resetIndex(){
		if(strategy.getIsRevert())
			indexActual=getRouteSize()-1; 
		else 
			indexActual=0;
		hasResetIndex=true;   
	}  

	public boolean getHasResetIndex(){
		return hasResetIndex;
	}

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
