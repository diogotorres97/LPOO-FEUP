import java.util.Scanner; 
import java.util.Random;

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

	static char ogreMap[][]={
			{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
			{'I', ' ', ' ', ' ', 'O', ' ', ' ', 'k', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, 
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, 
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, 
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, 
			{'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
	};

	static char moves[]= {'w','a','s','d'};

	public static void printMap(char nameMap[][]){
		for(int i=0; i< nameMap.length;i++){
			for(int j=0;j< nameMap[i].length;j++)
				System.out.print(nameMap[i][j]);
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

	public static int[] checkUnitPosition(char unit, char nameMap[][]){
		int[] array = new int[2];
		for(int i=0; i< nameMap.length;i++){
			for(int j=0;j<  nameMap[i].length;j++){
				if(nameMap[i][j]==unit){
					array[0]=i;
					array[1]=j;
				}
			}
		}
		return array;
	};

	static boolean keyHero=false;
	static boolean keyOgre=false;
	static boolean keyClub=false;

	static int [] posH= new int [2];
	static int [] posG= new int [2];
	static int [] posO= new int [2];
	static int [] posC= new int [2];


	public static int moveUnit(char command,char unit,char nameMap[][], int [] pos){


		if(keyHero && unit=='H'){
			unit='K';
		}
		else if(keyOgre && unit=='O') {
			unit='$';
		}
		else if(keyClub && unit=='*'){
			unit='$';
		}

		
		if(unit=='H' || unit=='K')
			if (nameMap[pos[0]-1][pos[1]]=='G'||nameMap[pos[0]+1][pos[1]]=='G'||nameMap[pos[0]][pos[1]-1]=='G'||nameMap[pos[0]][pos[1]+1]=='G'
			||nameMap[pos[0]-1][pos[1]]=='O'||nameMap[pos[0]+1][pos[1]]=='O'||nameMap[pos[0]][pos[1]-1]=='O'||nameMap[pos[0]][pos[1]+1]=='O'
			||nameMap[pos[0]-1][pos[1]]=='*'||nameMap[pos[0]+1][pos[1]]=='*'||nameMap[pos[0]][pos[1]-1]=='*'||nameMap[pos[0]][pos[1]+1]=='*'){

				return 1;
			}

		switch(command){
		case 'w':
			if(nameMap[pos[0]-1][pos[1]]==' ' || nameMap[pos[0]-1][pos[1]]=='*'){
				if(unit=='*'){
					if(nameMap[posC[0]][posC[1]]!='O')
						nameMap[posC[0]][posC[1]]=' ';
					posC[0]=pos[0]-1;
					posC[1]=pos[1];
					nameMap[pos[0]-1][pos[1]]=unit; //Case the unit is * then it uses Ogre position
				}
				else{
					nameMap[pos[0]][pos[1]]=' ';
					pos[0]-=1;
					nameMap[pos[0]][pos[1]]=unit; 

				}



			}
			else if(nameMap[pos[0]-1][pos[1]]=='k'){

				if(unit == 'H'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]-1][pos[1]]='K';
					pos[0]-=1;
					keyHero=true;	
				}
				else if(unit== 'O'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]-1][pos[1]]='$';
					pos[0]-=1;
					keyOgre=true;
				}
				else
				{
					if(nameMap[posC[0]][posC[1]]!='O')
						nameMap[posC[0]][posC[1]]=' ';
					nameMap[pos[0]-1][pos[1]]='$';
					posC[0]=pos[0]-1;
					posC[1]=pos[1];
					keyClub=true;
				}

			}
			else if(unit=='*' && nameMap[posC[0]][posC[1]]!='O')
				nameMap[posC[0]][posC[1]]=' ';

			if(keyClub && unit=='$')
			{
				if(pos[0]!=1 && pos[1]!=7)
					nameMap[1][7]='k';
				keyClub=false;


			}

			break;
		case 's':
			if(nameMap[pos[0]+1][pos[1]]==' ' || nameMap[pos[0]+1][pos[1]]=='*'){
				if(unit=='*'){
					if(nameMap[posC[0]][posC[1]]!='O')
						nameMap[posC[0]][posC[1]]=' ';
					posC[0]=pos[0]+1;
					posC[1]=pos[1];
					nameMap[pos[0]+1][pos[1]]=unit;//Case the unit is * then it uses Ogre position
				}
				else if(!keyClub || unit!='$'){
					nameMap[pos[0]][pos[1]]=' ';
					pos[0]+=1;
					nameMap[pos[0]][pos[1]]=unit;

				}

			}
			else if(unit=='*' && nameMap[posC[0]][posC[1]]!='O')
				nameMap[posC[0]][posC[1]]=' ';

			if(nameMap==ogreMap && nameMap[1][7]==' ' && (posH[0]!=1 || posH[1]!=7) )
				nameMap[1][7]='k';

			if(keyOgre && unit=='$'){
				nameMap[1][7]='k';
				keyOgre=false;

				nameMap[pos[0]][pos[1]]='O';
			}
			else if(keyClub && unit=='$')
			{
				if(pos[0]!=1 && pos[1]!=7)
					nameMap[1][7]='k';
				keyClub=false;

				nameMap[pos[0]+1][pos[1]]='*';
			}
			break;
		case 'a':
			if(nameMap[pos[0]][pos[1]-1]==' ' || nameMap[pos[0]][pos[1]-1]=='*' || nameMap[pos[0]][pos[1]-1]=='S'){
				if(unit=='*'){
					if(nameMap[posC[0]][posC[1]]!='O')
						nameMap[posC[0]][posC[1]]=' ';
					posC[0]=pos[0];
					posC[1]=pos[1]-1;
					nameMap[pos[0]][pos[1]-1]=unit;//Case the unit is * then it uses Ogre position
				}
				else if(!keyClub || unit!='$'){
					nameMap[pos[0]][pos[1]]=' ';
					pos[1]-=1;
					nameMap[pos[0]][pos[1]]=unit;
				}


			}
			else if(nameMap[pos[0]][pos[1]-1]=='k'){
				nameMap[pos[0]][pos[1]]=' ';
				nameMap[pos[0]][pos[1]-1]=unit;
				pos[1]-=1;
				nameMap[5][0]='S';
				nameMap[6][0]='S';
			}else if(nameMap[pos[0]][pos[1]-1]=='I' && nameMap[pos[0]][pos[1]]=='K'){
				nameMap[1][0]='S';
				nameMap[pos[0]][pos[1]]='H';
				keyHero=false;

			}
			else if(unit=='*' && nameMap[posC[0]][posC[1]]!='O' )
				nameMap[posC[0]][posC[1]]=' ';

			if(nameMap==ogreMap && nameMap[1][7]==' ' && (posH[0]!=1 || posH[1]!=7))
			{
				nameMap[1][7]='k';

			}


			if(keyOgre && unit=='$'){
				nameMap[1][7]='k';				
				keyOgre=false;

				nameMap[pos[0]][pos[1]]='O';

			}
			else if(keyClub && unit=='$')
			{
				if(pos[0]!=1 && pos[1]!=7)
					nameMap[1][7]='k';
				keyClub=false;

				nameMap[pos[0]][pos[1]+1]='*';
			}
			break;
		case 'd':
			if(nameMap[pos[0]][pos[1]+1]==' ' || nameMap[pos[0]][pos[1]+1]=='*'){

				if(unit=='*'){
					if(nameMap[posC[0]][posC[1]]!='O')
						nameMap[posC[0]][posC[1]]=' ';
					posC[0]=pos[0];
					posC[1]=pos[1]+1;
					nameMap[pos[0]][pos[1]+1]=unit;//Case the unit is * then it uses Ogre position
				}
				else{
					
					nameMap[pos[0]][pos[1]]=' ';
					pos[1]+=1;
					nameMap[pos[0]][pos[1]]=unit;
				}


			}
			else if(nameMap[pos[0]][pos[1]+1]=='k'){

				if(unit == 'H'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]][pos[1]+1]='K';
					pos[1]+=1;
					keyHero=true;	
				}
				else if(unit=='O'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]][pos[1]+1]='$'; //changed pos[1]+1, so that the Ogre goes to the top of key
					pos[1]+=1;
					keyOgre=true;
				}
				else
				{
					if(nameMap[posC[0]][posC[1]]!='O')
						nameMap[posC[0]][posC[1]]=' ';
					nameMap[pos[0]][pos[1]+1]='$';
					posC[0]=pos[0]+1;
					posC[1]=pos[1];
					keyClub=true;


				}

			}
			else if(unit=='*' && nameMap[posC[0]][posC[1]]!='O')
				nameMap[posC[0]][posC[1]]=' ';
			if(keyClub && unit=='$')
			{
				if(pos[0]!=1 && pos[1]!=7)
					nameMap[1][7]='k';
				keyClub=false;


			}
			break;

		}

		if(pos[1]==0){
			return 2;
		}

		return 0;
	};



	public static void main(String[] args) {
		char letter;
		int flag;
		int posGuard=0;

		//Initial Hero position
		posH=checkUnitPosition('H', gameMap);


		//Initial Guard position
		posG=checkUnitPosition('G', gameMap);



		
		do{
			printMap(gameMap);
			System.out.println("Enter a command:");
			letter=readInput();
			
			moveUnit(routeGuard[posGuard],'G',gameMap, posG);
			flag=moveUnit(letter,'H',gameMap, posH);
			posGuard++;
			if(posGuard == routeGuard.length)
				posGuard=0;
		}while(flag == 0);
		 

		//Initial Hero position
		posH=checkUnitPosition('H', ogreMap);
		

		//Initial Ogre position
		posO=checkUnitPosition('O', ogreMap);
		

		//Initial Club position (not visible in the initial map)
		posC=checkUnitPosition('C', ogreMap);
		

		//flag=2; //retirar isto!!!
		if(flag==2){
			//level Ogre
			System.out.println("Welcome to a new level! Now, you have an ogre in your direction so good luck!!");

			do{
				Random rn = new Random();
				int i;

				printMap(ogreMap);
				System.out.println("Enter a command:");
				letter=readInput();
				flag=moveUnit(letter,'H',ogreMap, posH);
				i=rn.nextInt(4);
				moveUnit(moves[i],'O',ogreMap, posO);
				i=rn.nextInt(4);
				moveUnit(moves[i], '*', ogreMap, posO); //Moves the club


			}while(flag == 0);
		}	

		if(flag==2){
			printMap(ogreMap);
			System.out.println("YOU WIN!");
		}
		else 
			System.out.println("GAME OVER!");

	}
}