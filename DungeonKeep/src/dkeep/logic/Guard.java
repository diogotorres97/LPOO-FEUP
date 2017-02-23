package dkeep.logic;

public class Guard extends Unit{
	
	
	static char routeGuard[] = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	static int type;
	static int indexActual;
	
	public Guard(int type) {
		
		unit='G';
		Guard.type=type;
		indexActual=0;
		
	}
	public int getGuard(){
		return type;
	}
	
	public void setGuard(int type){
		Guard.type=type;
	}
	
	public char getActualRoute(int index){
		return routeGuard[index];
	}
	
	public int getRouteSize(){
		return routeGuard.length;
	}
	
	public void increaseIndex(){
		indexActual++;
	}
	
	public int getIndex(){
		return indexActual;
	}
	public void resetIndex(){
		indexActual=0;
	}
}
