import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
//The HeuristicPlayer class will represent the player playing with strategy. It inherits the Player class
public class HeuristicPlayer extends Player{

	/* ArrayList containing
	1) information about the dice
	2) the number of points offered by his move
	3) and if it is near an object as well
	4) and if he is close to his opponent.
	5) the number of supplies the player has collected */
	ArrayList<Integer[]> path;

	public HeuristicPlayer() {
		 path=new ArrayList<Integer[]>();
	}
	
	public HeuristicPlayer(int id, String name, Board board,int score, int x, int y) {
		super(id, name, board, score , x, y);
		path = new ArrayList<Integer[]>(5);
		int i;
		Integer t[] = new Integer[1];
		for(i = 0; i < path.size() ; i ++) {
			path.add(i,t);
		}
	}   
	
	boolean canMove(int dice,int currentPos) {
		if(dice > 8 || dice <= 0) {
			return false;
		}
		
		if (dice == 1 && board.getTiles()[currentPos].up == false) {
			return true;
		}
		
		else if (dice == 3 && board.getTiles()[currentPos].right == false) {
			return true;
		}
		
		else if (dice == 5 && board.getTiles()[currentPos].down == false) {
			return true;
		}
		
		else if (dice == 7 && board.getTiles()[currentPos].left == false) {
			return true;
	
		}
		else return false;
	}
	
	//functions that check if the player can see the next three squares up,down,left,right. 
	//If he has walls then he won't be able to see anything. The maximum number 
	//of squares beyond his own (that he can see) is 3.
	int canSeeUp(int currentPos) {
		int counterUp=0;
		
		for(int i=0;i<3;i++) {
			if((currentPos/board.getN())<(board.getN()-i)) {
				if(board.getTiles()[currentPos+(i*board.getN())].up==false) {
					counterUp++;
				}
			}
		}
		
		return counterUp;
	}
	
	int canSeeDown(int currentPos) {
		int counterDown=0;

		
		for(int i=0;i<3;i++) {
			if(currentPos/board.getN()>=i) {
				if(board.getTiles()[currentPos-(i*board.getN())].down==false) {
					counterDown++;
				}
			}	
		}
		
		return counterDown;
	}
	
	
	int canSeeLeft(int currentPos) {
		int counterLeft=0;

		for(int i=0;i<3;i++) {
			if(currentPos%board.getN()>=i) {
				if(board.getTiles()[currentPos-i].left==false) {
					counterLeft++;
				}
			}
		}
		return counterLeft;
	}
	
	int canSeeRight(int currentPos) {
		int counterRight=0;
		
		for(int i=0;i<3;i++) {
			if((currentPos%board.getN())<(board.getN()-i)) {
				if(board.getTiles()[currentPos+i].right==false) {
					counterRight++;
				}
			}	
		}
		return counterRight;
	}
	
	//function that finds if the opponent is close and how much, stores the minotaur's information and finds its distance (in absolute value) in x and y axes
	//after checking if the player can see how far away the minotaur is, if it doesn't find the minotaur return 0.	
	public int nearOpponent(int currentPos,Player Minotaur) {
		//int NearOpponent=0;
		int MPX=Minotaur.getX(); 	//minotaur's x-axis position
		int MPY=Minotaur.getY(); 	//minotaur's y-axis position
		int TPX=super.getX(); 		//position on the x axis of Theseus
		int TPY=super.getY(); 		//position on the y axis of Theseus
		int OpponentDistX=TPY-MPY;  //distance of opponents on x axis
		int OpponentDistY=TPX-MPX;  //distance of opponents on y axis
			
		if(OpponentDistX==0&&Math.abs(OpponentDistY)<=3) {
			if(OpponentDistY<0) {
				if(canSeeRight(currentPos)>=Math.abs(OpponentDistY)) {
					return Math.abs(OpponentDistY);
				}
			}
			else if(OpponentDistY>0) {
				if(canSeeLeft(currentPos)>=Math.abs(OpponentDistY)) {
					return Math.abs(OpponentDistY);
				}
			}
			
		}
		
		if(OpponentDistY==0&&Math.abs(OpponentDistX)<=3) {
			if(OpponentDistX<0) {
				if(canSeeUp(currentPos)>=Math.abs(OpponentDistX)) {
					return Math.abs(OpponentDistX);
				}
			}
			else if(OpponentDistY>0) {
				if(canSeeDown(currentPos)>=Math.abs(OpponentDistX)) {
					return Math.abs(OpponentDistX);
				}
			}
			
		}

		return 0;
	}
	
	//array function that finds if a supply is nearby and how much, stores its distance in x,y axis, and how many supplies are in Theseus' field of vision
	//and returns them, if they are not in the field of visibility or there are walls, it will return the value 0. In the 3rd and 4th position of the table we have put some counters that will help us in
	//evaluate function below
	public int[] nearSupplies(int currentPos) {
		
		int[] nearSupplies=new int[5];
		nearSupplies[0]=0;
		nearSupplies[1]=0;
		nearSupplies[2]=0;
		nearSupplies[3]=0;
		nearSupplies[4]=0;
	
		
		
		for(int i=0;i<super.board.getS();i++) {
			int a=0;
			int b=0;
			a=super.getX()-super.board.getSupplies()[i].getX();
			b=super.getY()-super.board.getSupplies()[i].getY();
			if(a<0) {
				//if the supply is above Theseus
				if(canSeeUp(currentPos)>=Math.abs(a)) {
					if(super.getY()==super.board.getSupplies()[i].getY()) {
						nearSupplies[0]+=1;
						nearSupplies[3]+=i;
						nearSupplies[4]+=i*i;
					
					}
				}
			}
			
			else {
				//if the supply is under Theseus
				if(canSeeDown(currentPos)>=Math.abs(a)) {
					if(super.getY()==super.board.getSupplies()[i].getY()) {
						nearSupplies[0]+=1;
						nearSupplies[3]+=i;
						nearSupplies[4]+=i*i;
						
					}
				}
				
			}
			if(b<0) {
				if(canSeeRight(currentPos)>=Math.abs(b)) {
					if(super.getX()==super.board.getSupplies()[i].getX()) {
						nearSupplies[0]+=1;
						nearSupplies[3]+=i;
						nearSupplies[4]+=i*i;
					
					}
				}
			}
			else {
				if(canSeeLeft(currentPos)>=Math.abs(b)) {
					if(super.getX()==super.board.getSupplies()[i].getX()) {
						nearSupplies[0]+=1;
						nearSupplies[3]+=i;
						nearSupplies[4]+=i*i;
					
					}
				}
				
			}
		}

//		if(nearSupplies[0]>0) {
//			nearSupplies[1]=Math.abs(super.getX()-super.board.getSupplies()[nearSupplies[3]].getX());
//			nearSupplies[2]=Math.abs(super.getY()-super.board.getSupplies()[nearSupplies[3]].getY());
//		}
			return nearSupplies;

	}
		
	//This function evaluates the player's movement when he has the dice, given that he is at currentPos.
	//The function returns the motion evaluation, according to the objective function we defined.
	public double evaluate(int currentPos, int dice,Player Minotaur) {

		double evaluate=0;
		int MPX= Minotaur.getX();		//minotaur's x-axis position
		int MPY=Minotaur.getY(); //minotaur's y-axis position
		int TPX=super.getX(); //position on the x axis of Theseus
		int TPY=super.getY(); //position on the y axis of Theseus
		int OpponentDistX=TPX-MPX; //distance of opponents on x axis
		int OpponentDistY=TPY-MPY; //distance of opponents on y axis

		//in case 2 supplies or more are found near Theseus, we made a for and a switch to make him decide which one to go to.
		//initially it will find using the case and the counters we had in the nearSupplies function above, which are the supplies that are near it.. If for example
		//is supply 1 and supply 2 will check the distance of these 2 from Theseus by absolute value and store the closest one in the nearSupplies function. Similarly for all the rest
		if(nearSupplies(currentPos)[0]>=2) {
			switch(nearSupplies(currentPos)[4]) {
				case 1:		if((Math.abs(super.getX()-super.board.getSupplies()[0].getX()))<=(Math.abs(super.getX()-super.board.getSupplies()[1].getX()))) {		
								if((Math.abs(super.getY()-super.board.getSupplies()[0].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[1].getY()))) {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[0].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[0].getY();
								}
								else {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[0].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[1].getY();
								}
							}
							
							else {
								if((Math.abs(super.getY()-super.board.getSupplies()[0].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[1].getY()))) {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[1].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[1].getY();
								}
								else {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[1].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[0].getY();
								}
							}
							break;
				
				
				case 4:		if((Math.abs(super.getX()-super.board.getSupplies()[0].getX()))<=(Math.abs(super.getX()-super.board.getSupplies()[2].getX()))) {		
								if((Math.abs(super.getY()-super.board.getSupplies()[0].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[2].getY()))) {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[0].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[0].getY();
								}
								else {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[0].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[2].getY();
								}
							}
							
							else {
								if((Math.abs(super.getY()-super.board.getSupplies()[0].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[2].getY()))) {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[2].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[2].getY();
								}
								else {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[2].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[0].getY();
								}
							}		
							break;
							
				case 9:		if((Math.abs(super.getX()-super.board.getSupplies()[0].getX()))<=(Math.abs(super.getX()-super.board.getSupplies()[3].getX()))) {		
								if((Math.abs(super.getY()-super.board.getSupplies()[0].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[3].getY()))) {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[0].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[0].getY();
								}
								else {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[0].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[3].getY();
								}
							}
							
							else {
								if((Math.abs(super.getY()-super.board.getSupplies()[0].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[3].getY()))) {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[3].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[3].getY();
								}
								else {
									nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[3].getX();
									nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[0].getY();
								}
							}				
							break;
							
				case 5:  		if((Math.abs(super.getX()-super.board.getSupplies()[1].getX()))<=(Math.abs(super.getX()-super.board.getSupplies()[2].getX()))) {		
									if((Math.abs(super.getY()-super.board.getSupplies()[1].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[2].getY()))) {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[1].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[1].getY();
									}
									else {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[1].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[2].getY();
									}
								}
								
								else {
									if((Math.abs(super.getY()-super.board.getSupplies()[1].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[2].getY()))) {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[2].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[2].getY();
									}
									else {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[2].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[1].getY();
									}
								}			
							break;
							
				case 10:		if((Math.abs(super.getX()-super.board.getSupplies()[1].getX()))<=(Math.abs(super.getX()-super.board.getSupplies()[3].getX()))) {		
									if((Math.abs(super.getY()-super.board.getSupplies()[1].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[3].getY()))) {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[1].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[1].getY();
									}
									else {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[1].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[3].getY();
									}
								}
								
								else {
									if((Math.abs(super.getY()-super.board.getSupplies()[1].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[3].getY()))) {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[3].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[3].getY();
									}
									else {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[3].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[1].getY();
									}
								}			
							break;
							
				case 13:		if((Math.abs(super.getX()-super.board.getSupplies()[2].getX()))<=(Math.abs(super.getX()-super.board.getSupplies()[3].getX()))) {		
									if((Math.abs(super.getY()-super.board.getSupplies()[2].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[3].getY()))) {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[2].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[2].getY();
									}
									else {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[2].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[3].getY();
									}
								}
								
								else {
									if((Math.abs(super.getY()-super.board.getSupplies()[2].getY()))<=(Math.abs(super.getY()-super.board.getSupplies()[3].getY()))) {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[3].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[3].getY();
									}
									else {
										nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[3].getX();
										nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[2].getY();
									}
								}			
							break;
			}
		} 
		//If there is 1 supply near Theseus store the x,y distance by absolute value in the nearSupplies function
		if(nearSupplies(currentPos)[0]==1) {
			nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[nearSupplies(currentPos)[3]].getX();
			nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[nearSupplies(currentPos)[3]].getY();
		}
		//If the minotaur is close (in Theseus' field of vision) return a large negative value
		if(nearOpponent(currentPos,Minotaur)>0) {
			
			if(Math.abs(nearOpponent(currentPos,Minotaur))>0) {
				
				if(OpponentDistX==0&&OpponentDistY<0&&dice==3) {
					evaluate=-10000;
					return evaluate;
				} 
				
				if(OpponentDistX==0&&OpponentDistY>0&&dice==7) {
					evaluate=-10000;
					return evaluate;
				}
				
				if(OpponentDistY==0&&OpponentDistX<0&&dice==1) {
					evaluate=-10000;
					return evaluate;
				}
				
				if(OpponentDistY==0&&OpponentDistX>0&&dice==5) {
					evaluate=-10000;
					return evaluate;
				}
				
			}
		}
		//If there is a supply near Theseus return a value that grows according to how close he is to it
		if(nearSupplies(currentPos)[0]==1) {	
			
			if(nearSupplies(currentPos)[2]==3&&dice==7) {
				evaluate=100;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[2]==-3&&dice==3) {
				evaluate=100;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[1]==3&&dice==5) {
				evaluate=100;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[1]==-3&&dice==1) {
				evaluate=100;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[2]==2&&dice==7) {
				evaluate=1000;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[2]==-2&&dice==3) {
				evaluate=1000;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[1]==2&&dice==5) {
				evaluate=1000;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[1]==-2&&dice==1) {
				evaluate=1000;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[2]==1&&dice==7) {
				evaluate=10000;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[2]==-1&&dice==3) {
				evaluate=10000;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[1]==1&&dice==5) {
				evaluate=10000;
				return evaluate;
			}
			
			if(nearSupplies(currentPos)[1]==-1&&dice==1) {
				evaluate=10000;
				return evaluate;
			}
			
		}
		
		if(nearSupplies(currentPos)[0]>=2) {
			if(nearSupplies(currentPos)[1]<nearSupplies(currentPos)[2]) {
				
				if(nearSupplies(currentPos)[2]==3&&dice==7) {
					evaluate=100;
					return evaluate;
				}
				
				if(nearSupplies(currentPos)[2]==-3&&dice==3) {
					evaluate=100;
					return evaluate;
				}
				
				
				if(nearSupplies(currentPos)[2]==2&&dice==7) {
					evaluate=1000;
					return evaluate;
				}
				
				if(nearSupplies(currentPos)[2]==-2&&dice==3) {
					evaluate=1000;
					return evaluate;
				}
				
			
				if(nearSupplies(currentPos)[2]==1&&dice==7) {
					evaluate=10000;
					return evaluate;
				}
				
				if(nearSupplies(currentPos)[2]==-1&&dice==3) {
					evaluate=10000;
					return evaluate;
				}		
			}
			
			else {
				
				if(nearSupplies(currentPos)[1]==3&&dice==5) {
					evaluate=100;
					return evaluate;
				}
				
				if(nearSupplies(currentPos)[1]==-3&&dice==1) {
					evaluate=100;
					return evaluate;
				}			
				
				if(nearSupplies(currentPos)[1]==2&&dice==5) {
					evaluate=1000;
					return evaluate;
				}
				
				if(nearSupplies(currentPos)[1]==-2&&dice==1) {
					evaluate=1000;
					return evaluate;
				}
				
				if(nearSupplies(currentPos)[1]==1&&dice==5) {
					evaluate=10000;
					return evaluate;
				}
				
				if(nearSupplies(currentPos)[1]==-1&&dice==1) {
					evaluate=10000;
					return evaluate;
				}
			}
		}		
		
		return evaluate;
	}
	
	
	
	
	/*This function is responsible for choosing the player's final move. The function should include the following:
	
	1) Creation of a structure (Dynamic or non-array) that will store the possible moves of the player as well as the evaluation of each of them.
	2)Evaluating each possible move using the double evaluate function and storing the evaluation in the structure you made.
	3) Choosing the best move based on the evaluation you made.
	4) Update the path class variable.
	
	
	5) Return the player's new position.
	*/
	int getNextMove(int currentPos,Player Minotaur) {
		
		if(path == null) {
			path = new ArrayList<Integer[]>(5);
		}
			
		//Create a HashMap with key key and value value
		HashMap<Integer,Double> moveEvaluate = new HashMap<Integer, Double>();
		int dice = 1;  
		Integer key=0; 
		Double value=Double.valueOf(0);
		Double maximum= Double.valueOf(- 100000);
		
		//Store items in HashMap (so it's easy to return evaluate function items with one use of System.out.)
		//The function puts on the dice roll the most efficient die that can be played according to the functions evaluate and canMove respectively
		for(int i = 1 ; i <=8; i+=2) {
			
			if(canMove(i,currentPos) == true){
				key = Integer.valueOf(i);
				value = evaluate(currentPos,i,Minotaur);
				moveEvaluate.put(key, value);
				if(maximum < evaluate(currentPos,i,Minotaur)) {
					maximum = evaluate(currentPos,i,Minotaur);
					dice = i;			
				}
			}
			else continue;
		}
		
		
	//	System.out.println(moveEvaluate);  
		
		int supplyCollected=0; //if it collected any object
		Integer [] dices = new Integer[Game.getRound()]; //the dice
		Integer [] points = new Integer[Game.getRound()];//the total points he has collected
		Integer [] supplies = new Integer[Game.getRound()];//closest supply to it if any
		Integer [] enemies = new Integer[Game.getRound()];//if he sees the minotaur near him
		Integer [] suppliesCollected = new Integer[Game.getRound()];//if collected any supplies
		
		if(Game.getRound()>2) {
			if(super.getScore()>path.get(4)[Game.getRound()-2]) {
				supplyCollected++;
			}
		}
		suppliesCollected[Game.getRound() - 1] = Integer.valueOf(supplyCollected);
		points[Game.getRound() - 1] = Integer.valueOf(super.getScore());
		dices[Game.getRound() - 1] = Integer.valueOf(dice);
		if(nearSupplies(currentPos)[1]<=nearSupplies(currentPos)[2]) {
			supplies[Game.getRound() - 1] = Integer.valueOf(nearSupplies(currentPos)[1]);
		}
		else if(nearSupplies(currentPos)[1]>=nearSupplies(currentPos)[2]) {
			supplies[Game.getRound() - 1] = Integer.valueOf(nearSupplies(currentPos)[2]);
		}
		enemies[Game.getRound() - 1] = Integer.valueOf(nearOpponent(currentPos,Minotaur));
		
		
		//Store the rounds information in the ArrayList path
		if(Game.getRound() == 1) {
			path.clear();
			path.add(0,dices);
			path.add(1,suppliesCollected);	
			path.add(2,supplies);
			path.add(3,enemies);
			path.add(4,points);
		}
		else {
			for(int i = 0; i < Game.getRound() - 1 ; i++) {
				dices[i] = path.get(0)[i];
				suppliesCollected[i] = path.get(1)[i];
				supplies[i] = path.get(2)[i];
				enemies[i] = path.get(3)[i];
				points[i] = path.get(4)[i];
			}
			path.clear();
			path.add(0,dices);
			path.add(1,suppliesCollected);
			path.add(2,supplies);
			path.add(3,enemies);
			path.add(4,points);

			
			
			
		}
		
		//Return the player's next position depending on what die they chose
		if(dice==1) {
			return currentPos + board.getN();
		}
		
		else if(dice==3) {
			return currentPos + 1;
		}
		
		else if(dice==5) {
			return currentPos - board.getN();
		}
		
		else if(dice==7) {
			return currentPos - 1;
		}
		
		return 0;
		
	}
			
		
		
	
	
	/*This function prints information about the player's moves in each round of the game (dice he chose and actions he took
	eg "the player in round 1 rolled the die equal to 4 and approached a loot"), as well as statistics for his total moves.
	Specifically, at the end it is requested to print:

	For each round:
		1) The number of provisions he has collected (Theseus).
		2) Its distance (in tiles) from the nearest supply. As long as he can see it, that is, there are no walls between
		3)His distance (in tiles) from the opponent. As long as he can see it, that is, there are no walls between
	  Once in total:
		1) The times the player chose to move forward (1).
		2) The times the player chose to move right (3).
		3) The times the player chose to move back (5).
		4) The times the player chose to move left (7).
		*/
	
	void statistics() {
		int counter1=0; 	 //counter that stores how many times 1 was played
		int counter3=0; //counter that stores how many times it was played 3
		int counter5=0; //counter that stores how many times it was played 5
		int counter7=0; //counter that stores how many times it was played 7
		
		
		if(path == null) {
			path = new ArrayList<Integer[]>(5);
			
		}


		if(Game.gameIsOver==0) {
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("Statistics:");
			System.out.println("The player " + super.getName() + " in round " + (Game.getRound()-1) + " set the die equal to " + path.get(0)[Game.getRound()-1]+ " ");
			if(path.get(2)[Game.getRound()-1]>0) {
				System.out.println("approached the supply");
			}
			
			System.out.println(Arrays.deepToString(path.toArray()));
			
			if(path.get(1)[Game.getRound()-1]==1) {
				System.out.println("and gathered a supply ");
			}	
			if(path.get(3)[Game.getRound()-1]>0) {
				System.out.println(super.getName()+ " sees the minotaur approaching");
			}
			
			
			
			System.out.println("The number of supplies the player has collected "+ super.getName() + " are " + path.get(4)[Game.getRound()-1]);
			
			if(path.get(2)[Game.getRound()-1]>0) {
				System.out.println("The distance in tiles from the nearest supply is " + path.get(2)[Game.getRound()-1]);
			}
			
		}
		
		//If the game ended
		if(Game.gameIsOver==1) {
			for(int i=0;i<path.get(0).length;i++) {
				if(path.get(0)[i]==1) {
					counter1++;
				}
				
				if(path.get(0)[i]==3) {
					counter3++;
				}
				
				if(path.get(0)[i]==5) {
					counter5++;
				}
				
				if(path.get(0)[i]==7) {
					counter7++;
				}
			}
			System.out.println("The times the player chose to move forward (1) are "+ counter1);
			System.out.println("The times the player chose to move right (3) are "+ counter3);
			System.out.println("The times the player chose to move back (5) are "+ counter5);
			System.out.println("The times the player chose to move left (7) are "+ counter7);
		}
	
	}
		
	

	public ArrayList<Integer[]> getPath() {
		return path;
	}


	public void setPath(ArrayList<Integer[]> path) {
		this.path = path;
	}
	
	
	
	
	
	
	
}



