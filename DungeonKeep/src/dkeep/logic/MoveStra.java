package dkeep.logic;

public class MoveStra {

	


	/*
	boolean moveStra(Unit unit, char command){

		 switch(command){
			case 'w':
				if(isFree(pos[0]-1],[pos[1])


				if(nameMap[pos[0]-1][pos[1]]!='X' && nameMap[pos[0]-1][pos[1]]!='I' 
				&& nameMap[pos[0]-1][pos[1]]!='G'&&nameMap[pos[0]-1][pos[1]]!='k' && nameMap[pos[0]-1][pos[1]]!='H'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]-1][pos[1]]=unit;
					pos[0]-=1;
				}
				else if(nameMap[pos[0]-1][pos[1]]=='k'){
					nameMap[pos[0]][pos[1]]=' ';
					if(unit == 'H'){
						nameMap[pos[0]-1][pos[1]]='K';
						keyHero=true;	
					}
					else {
						nameMap[pos[0]-1][pos[1]]='$';
						keyOgre=true;
					}

				}
				break;
			case 's':
				if(nameMap[pos[0]+1][pos[1]]!='X' && nameMap[pos[0]+1][pos[1]]!='I'
				&&nameMap[pos[0]+1][pos[1]]!='G'&&nameMap[pos[0]+1][pos[1]]!='k' && nameMap[pos[0]+1][pos[1]]!='H'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]+1][pos[1]]=unit;
					pos[0]+=1;
				}
				if(keyOgre && unit!='H'){
					nameMap[1][7]='k';
					keyOgre=false;

					nameMap[pos[0]][pos[1]]='O';
				}
				break;
			case 'a':
				if(nameMap[pos[0]][pos[1]-1]!='X' && nameMap[pos[0]][pos[1]-1]!='I' 
				&&nameMap[pos[0]][pos[1]-1]!='G' && nameMap[pos[0]][pos[1]-1]!='k' && nameMap[pos[0]][pos[1]-1]!='H'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]][pos[1]-1]=unit;
					pos[1]-=1;
				}
				else if(nameMap[pos[0]][pos[1]-1]=='k'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]][pos[1]-1]=unit;
					nameMap[5][0]='S';
					nameMap[6][0]='S';
				}else if(nameMap[pos[0]][pos[1]-1]=='I' && nameMap[pos[0]][pos[1]]=='K'){
					nameMap[1][0]='S';
					nameMap[pos[0]][pos[1]]='H';
					keyHero=false;

				}
				if(keyOgre && unit!='H'){
					nameMap[1][7]='k';				
					keyOgre=false;

					nameMap[pos[0]][pos[1]]='O';

				}
				break;
			case 'd':
				if(nameMap[pos[0]][pos[1]+1]!='X' && nameMap[pos[0]][pos[1]+1]!='I' 
				&&nameMap[pos[0]][pos[1]+1]!='G'&&nameMap[pos[0]][pos[1]+1]!='k' && nameMap[pos[0]][pos[1]+1]!='H'){
					nameMap[pos[0]][pos[1]]=' ';
					nameMap[pos[0]][pos[1]+1]=unit;
					pos[0]+=1;
				}
				else if(nameMap[pos[0]][pos[1]+1]=='k'){
					nameMap[pos[0]][pos[1]]=' ';
					if(unit == 'H'){
						nameMap[pos[0]][pos[1]+1]='K';
						keyHero=true;	
					}
					else {
						nameMap[pos[0]][pos[1]+1]='$'; //changed pos[1]+1, so that the Ogre goes to the top of key
						keyOgre=true;
					}

				}
				break;

			}



		 return true;


	 }
	/*
	public static int moveUnit(char command,char unit,char nameMap[][]){

		if(keyHero && unit=='H'){
			unit='K';
		}
		else if(keyOgre && unit=='O') {
			unit='$';
		}

		int [] pos=checkUnitPosition(unit,nameMap);

		if (nameMap[pos[0]-1][pos[1]]=='G'||nameMap[pos[0]+1][pos[1]]=='G'||nameMap[pos[0]][pos[1]-1]=='G'||nameMap[pos[0]][pos[1]+1]=='G'
				||nameMap[pos[0]-1][pos[1]]=='O'||nameMap[pos[0]+1][pos[1]]=='O'||nameMap[pos[0]][pos[1]-1]=='O'||nameMap[pos[0]][pos[1]+1]=='O'){
			return 1;
		}	

		switch(command){
		case 'w':
			if(nameMap[pos[0]-1][pos[1]]!='X' && nameMap[pos[0]-1][pos[1]]!='I' 
			&& nameMap[pos[0]-1][pos[1]]!='G'&&nameMap[pos[0]-1][pos[1]]!='k' && nameMap[pos[0]-1][pos[1]]!='H'){
				nameMap[pos[0]][pos[1]]=' ';
				nameMap[pos[0]-1][pos[1]]=unit;
				pos[0]-=1;
			}
			else if(nameMap[pos[0]-1][pos[1]]=='k'){
				nameMap[pos[0]][pos[1]]=' ';
				if(unit == 'H'){
					nameMap[pos[0]-1][pos[1]]='K';
					keyHero=true;	
				}
				else {
					nameMap[pos[0]-1][pos[1]]='$';
					keyOgre=true;
				}

			}
			break;
		case 's':
			if(nameMap[pos[0]+1][pos[1]]!='X' && nameMap[pos[0]+1][pos[1]]!='I'
			&&nameMap[pos[0]+1][pos[1]]!='G'&&nameMap[pos[0]+1][pos[1]]!='k' && nameMap[pos[0]+1][pos[1]]!='H'){
				nameMap[pos[0]][pos[1]]=' ';
				nameMap[pos[0]+1][pos[1]]=unit;
				pos[0]+=1;
			}
			if(keyOgre && unit!='H'){
				nameMap[1][7]='k';
				keyOgre=false;

				nameMap[pos[0]][pos[1]]='O';
			}
			break;
		case 'a':
			if(nameMap[pos[0]][pos[1]-1]!='X' && nameMap[pos[0]][pos[1]-1]!='I' 
			&&nameMap[pos[0]][pos[1]-1]!='G' && nameMap[pos[0]][pos[1]-1]!='k' && nameMap[pos[0]][pos[1]-1]!='H'){
				nameMap[pos[0]][pos[1]]=' ';
				nameMap[pos[0]][pos[1]-1]=unit;
				pos[1]-=1;
			}
			else if(nameMap[pos[0]][pos[1]-1]=='k'){
				nameMap[pos[0]][pos[1]]=' ';
				nameMap[pos[0]][pos[1]-1]=unit;
				nameMap[5][0]='S';
				nameMap[6][0]='S';
			}else if(nameMap[pos[0]][pos[1]-1]=='I' && nameMap[pos[0]][pos[1]]=='K'){
				nameMap[1][0]='S';
				nameMap[pos[0]][pos[1]]='H';
				keyHero=false;

			}
			if(keyOgre && unit!='H'){
				nameMap[1][7]='k';				
				keyOgre=false;

				nameMap[pos[0]][pos[1]]='O';

			}
			break;
		case 'd':
			if(nameMap[pos[0]][pos[1]+1]!='X' && nameMap[pos[0]][pos[1]+1]!='I' 
			&&nameMap[pos[0]][pos[1]+1]!='G'&&nameMap[pos[0]][pos[1]+1]!='k' && nameMap[pos[0]][pos[1]+1]!='H'){
				nameMap[pos[0]][pos[1]]=' ';
				nameMap[pos[0]][pos[1]+1]=unit;
				pos[0]+=1;
			}
			else if(nameMap[pos[0]][pos[1]+1]=='k'){
				nameMap[pos[0]][pos[1]]=' ';
				if(unit == 'H'){
					nameMap[pos[0]][pos[1]+1]='K';
					keyHero=true;	
				}
				else {
					nameMap[pos[0]][pos[1]+1]='$'; //changed pos[1]+1, so that the Ogre goes to the top of key
					keyOgre=true;
				}

			}
			break;

		}

		if(pos[1]==0){
			return 2;
		}

		return 0;
};
	 */
}
