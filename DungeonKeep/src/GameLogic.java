import java.util.Scanner; 

public class GameLogic {
	static char gameMap[][]= {
			{'X','X','X','X','X','X','X','X','X','X'},
			{'X','H',' ',' ','I',' ','X',' ','G','X'},
			{'X','X','X',' ','X','X','X',' ',' ','X'},
			{'X',' ','I',' ','I',' ','X',' ',' ','X'},
			{'X','X','X',' ','X','X','X',' ',' ','X'},
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X','X','X',' ','X','X','X','X',' ','X'},
			{'X',' ','I',' ','I',' ','X','k',' ','X'},
			{'X','X','X','X','X','X','X','X','X','X'}
	};
	static char routeGuard[] = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};

	public static void printMap(){
		for(int i=0; i< 10;i++){
			for(int j=0;j< 10;j++)
				System.out.print(gameMap[i][j]);
			System.out.print("\n");
		}
	}

	public static char readInput(){
		Scanner s = new Scanner(System.in);
		char letter;
		letter = s.next().charAt(0);
		return letter;
	}

	public static boolean gameOver(){
		return false;
	};

	public static int[] checkUnitPosition(char unit){
		int[] array = new int[2];
		for(int i=0; i< 10;i++){
			for(int j=0;j< 10;j++){
				if(gameMap[i][j]==unit){
					array[0]=i;
					array[1]=j;
				}
			}
		}
		return array;
	};

	public static int moveUnit(char command,char unit){
		int [] pos = checkUnitPosition(unit);



		if (gameMap[pos[0]-1][pos[1]]=='G'||gameMap[pos[0]+1][pos[1]]=='G'||gameMap[pos[0]][pos[1]-1]=='G'||gameMap[pos[0]][pos[1]+1]=='G'){
			return 1;
		}	

		switch(command){
		case 'w':
			if(gameMap[pos[0]-1][pos[1]]!='X' && gameMap[pos[0]-1][pos[1]]!='I' && gameMap[pos[0]-1][pos[1]]!='G'){
				gameMap[pos[0]][pos[1]]=' ';
				gameMap[pos[0]-1][pos[1]]=unit;
				pos[0]-=1;
			}
			break;
		case 's':
			if(gameMap[pos[0]+1][pos[1]]!='X' && gameMap[pos[0]+1][pos[1]]!='I' &&gameMap[pos[0]+1][pos[1]]!='G'){
				gameMap[pos[0]][pos[1]]=' ';
				gameMap[pos[0]+1][pos[1]]=unit;
				pos[0]+=1;
			}
			break;
		case 'a':
			if(gameMap[pos[0]][pos[1]-1]!='X' && gameMap[pos[0]][pos[1]-1]!='I' &&gameMap[pos[0]][pos[1]-1]!='G'&&gameMap[pos[0]][pos[1]-1]!='k'){
				gameMap[pos[0]][pos[1]]=' ';
				gameMap[pos[0]][pos[1]-1]=unit;
				pos[1]-=1;
			}
			else if(gameMap[pos[0]][pos[1]-1]=='k'){
				gameMap[pos[0]][pos[1]]=' ';
				gameMap[pos[0]][pos[1]-1]=unit;
				gameMap[5][0]='S';
				gameMap[6][0]='S';
			}
			break;
		case 'd':
			if(gameMap[pos[0]][pos[1]+1]!='X' && gameMap[pos[0]][pos[1]+1]!='I' &&gameMap[pos[0]][pos[1]+1]!='G'){
				gameMap[pos[0]][pos[1]]=' ';
				gameMap[pos[0]][pos[1]+1]=unit;
				pos[0]+=1;
			}
			break;

		}
		if(pos[1]==0)
			return 2;

		return 0;
	};


	public static void main(String[] args) {
		char letter;
		int flag;
		int posGuard=0;
		do{
			printMap();
			System.out.println("Enter a command:");
			letter=readInput();
			flag=moveUnit(letter,'H');
			moveUnit(routeGuard[posGuard],'G');
			posGuard++;
			if(posGuard == routeGuard.length)
				posGuard=0;
		}while(flag == 0);

		if(flag==2){
			printMap();
			System.out.println("YOU WIN!");
		}	
		else 
			System.out.println("GAME OVER!");
	}
}