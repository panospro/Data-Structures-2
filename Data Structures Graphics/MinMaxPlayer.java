import java.util.ArrayList;
import java.util.Arrays;


//The MinMaxPlayer class for the smart player that will "count" some moves ahead
public class MinMaxPlayer extends Player{
		
	ArrayList<Integer[]> path;
	
	
	public MinMaxPlayer(){
		 path=new ArrayList<Integer[]>();
	}
	
	
	public MinMaxPlayer(int id, String name, Board board,int score, int x, int y) {
		super(id, name, board,score, x, y);
		path = new ArrayList<Integer[]>(5);
		int i;
		Integer t[] = new Integer[1];
		for(i = 0; i < path.size() ; i ++) {
			path.add(i,t);
		}
	}
	

	public MinMaxPlayer(Player p) {
		super(p.getPlayerId(), p.getName(), p.getBoard(),p.getScore(), p.getX(), p.getY());
	}

	//Function that checks if the move can be made with the current position of the player and the dice he brought, checking if he has walls in the corresponding direction
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
	
		
	//Function that finds if the opponent is close and how much, stores the minotaur's information and finds its distance (in absolute value) in x and y axes
	//after checking if the player can see how far away the minotaur is, if it doesn't find the minotaur return 0.
	public int nearOpponent(int currentPos,Player Minotaur) {
		//int NearOpponent=0;
		int MPX=Minotaur.getX(); 	//minotaur's x-axis position
		int MPY=Minotaur.getY(); //minotaur's y-axis position
		int TPX=super.getX(); //position on the x axis of Theseus
		int TPY=super.getY(); //position on the y axis of Theseus
		int OpponentDistX=TPY-MPY; //distance of opponents on x axis
		int OpponentDistY=TPX-MPX; //distance of opponents on y axis
			
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
				
				if(canSeeUp(currentPos)>=Math.abs(a)) {
					if(super.getY()==super.board.getSupplies()[i].getY()) {
						nearSupplies[0]+=1;
						nearSupplies[3]+=i;
						nearSupplies[4]+=i*i;
					
					}
				}
			}
			
			else {
				
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

//			if(nearSupplies[0]>0) {
//				nearSupplies[1]=Math.abs(super.getX()-super.board.getSupplies()[nearSupplies[3]].getX());
//				nearSupplies[2]=Math.abs(super.getY()-super.board.getSupplies()[nearSupplies[3]].getY());
//			}
			return nearSupplies;

	}


	//This function evaluates the player's movement when he has the dice, given that he is at currentPos.
	//The function returns the motion evaluation, according to the objective function we defined.
	public double evaluate(int currentPos, int dice,Board board,Player Minotaur) {	
		double evaluate=0;
		int MPX=Minotaur.getX();		
		int MPY=Minotaur.getY();		
		int TPX=super.getX();			
		int TPY=super.getY();		
		int OpponentDistX=TPX-MPX;		
		int OpponentDistY=TPY-MPY;		
		if(board==null) {
			board=new Board();
		}
		//In the event that 2 or more supplies are found near Theseus, we made a for and a switch to make him decide which one to go to.
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
		 
		if(nearSupplies(currentPos)[0]==1) {
			nearSupplies(currentPos)[1]=super.getX()-super.board.getSupplies()[nearSupplies(currentPos)[3]].getX();
			nearSupplies(currentPos)[2]=super.getY()-super.board.getSupplies()[nearSupplies(currentPos)[3]].getY();
		}
		 
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
	
	
	
	//This function implements the MinMax algorithm to find the best available move and returns the index of the best move.
	public int chooseMinMax(Node root) {
		
		int i,j,dice = 0;
		double max;
		double min = 100000;
		double[][] array = new double[root.getWidth()][2];
		
		for(i = 0; i < root.getWidth() ; i++) {
			max = root.getChildren().get(i).getNodeEvaluation();
			
			for(j = 0 ; j < root.getChildren().get(i).getWidth() ; j++) {
				
				if(min > root.getChildren().get(i).getNodeEvaluation()) {
					
					min = root.getChildren().get(i).getEvaluation();
				}
			}
			
			array[i][0] = max - min;
			array[i][1] = root.getChildren().get(i).getNodeMove()[2];
			
		}
		max = - 100000;
	
		for(i = 0; i < root.getWidth(); i++)  {
			if(max < array[i][0]) {
				
				max = array[i][0];
				dice = (int)array[i][1];
			}	
		}
		return dice;
	}

	
	//Function that moves the player by making the best move according to the chooseMinMax function.
	public int[] getNextMove (int currentPos, int opponentCurrentPos,Player Minotaur) {
	
		int[] nodeMove = new int[3];
		nodeMove[0] = opponentCurrentPos/board.getN();
		nodeMove[1] = opponentCurrentPos%board.getN();
		
		Node root = new Node(null,1,nodeMove,super.getBoard(),0,0,0);
		int[] array = new int[1];
		createMySubtree(currentPos,opponentCurrentPos,root,root.getNodeDepth() + 1,Minotaur);
		int dice=chooseMinMax(root); 
		
		
		if(dice==1) {
			array[0]= currentPos + board.getN();
		}
		
		else if(dice==3) {
		array[0]=  currentPos + 1;
		}
		
		else if(dice==5) {
			array[0]=  currentPos - board.getN();
		}
		
		else if(dice==7) {
			array[0]= currentPos - 1;
		}
		
		int supplyCollected=0;  //if it collected any object
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
	
		//Delete the elements so they don't take up memory space
		root = null;
		//Return the movement
		return array;
	}
	
	
	//Function to create the Node for our movement
	public void createMySubtree(int currentPos, int opponentCurrentPos, Node root, int depth,Player Minotaur) {
		int i,j,width = 0;
		int [] dice = new int [8];
		Player p = new Player(Minotaur);
		p.setX(opponentCurrentPos/board.getN());
		p.setY(opponentCurrentPos%board.getN());;
		int XY=0;
		double ev;
		for(i = 1; i <= 8; i++) {
			if(canMove(i,currentPos) == true) {
				width++;
			}
		}
		int[][] nodeMove = new int [width][3];
		Node [] child = new Node[width];
		width = 0;
		MinMaxPlayer temp = new MinMaxPlayer(super.getPlayerId(),super.getName(),root.getNodeBoard(),super.getScore(),currentPos/board.getN(),currentPos%board.getN());
		for(i = 1; i <= 8 ; i++) {
			for( j = i - 1 ; j < 8 ; j++) {
				dice[j] = dice[j] + 1;
			}
			
			if(temp.canMove(i,currentPos) == true) {
				width = root.getWidth();
				++width;
				root.setWidth(width);
				
				//Return the player's next position depending on what die they chose
				if(i==1) {
					XY= currentPos + board.getN();
				}
				
				else if(i==3) {
					XY=  currentPos + 1;
				}
				
				else if(i==5) {
					XY=  currentPos - board.getN();
				}
				
				else if(i==7) {
					XY= currentPos - 1;
				}
				
				nodeMove[width -1][0] = XY/board.getN();
				nodeMove[width -1][1] = XY%board.getN();
				nodeMove[width - 1][2] = dice[i - 1];//dice[i]?

				ev = temp.evaluate(currentPos,dice[i -1],super.getBoard(),p); 

				
				//Initialize node
				child[width -1] = new Node(root,depth,nodeMove[width - 1],root.getNodeBoard(),ev + root.getEvaluation(),width,ev);
				createOpponentSubtree(temp.getX()*board.getN()+temp.getY(),opponentCurrentPos,child[width -1] ,depth+1,0.0,Minotaur);
					
			}	
			
		}
		
		 j = 0;
		 root.getChildren().clear();
		 Node[] tempNode = new Node[root.getWidth()];
		 for(i = 0; i < root.getWidth() ; i++) {
			tempNode[i] = child[i];
			root.getChildren().add(i,tempNode[i]);
		 }
		
	}
	

	//Function to create a Node in the movement of the opponent player.
	void createOpponentSubtree(int currentPos, int opponentCurrentPos, Node parent, int depth, double parentEva,Player Minotaur) {

		int i,j,width=0;
		int [] dice = new int[8];
		double ev;
		MinMaxPlayer p = new MinMaxPlayer(Minotaur);
		p.setX(opponentCurrentPos/board.getN());
		p.setY(opponentCurrentPos%board.getN());;
		int XY=0;

		for(i = 1; i <= 8; i++) {
			if(canMove(i,currentPos) == true) {
				width++;
			}
		}
		Node[] child = new Node [width];
		int[][] nodeMove = new int[width][3];
		width = 0;
		MinMaxPlayer temp = new MinMaxPlayer(super.getPlayerId(),super.getName(),parent.getNodeBoard(),super.getScore(),currentPos/board.getN(),currentPos%board.getN());
		
		for( i = 1; i <= 8; i++) {
			
			for( j = i - 1 ; j < 8 ; j++) {
				dice[j] = dice[j] + 1;
			}
			width=0;
			if(temp.canMove(i, currentPos) == true) {
				width = parent.getWidth()-1;
				width++;
				parent.setWidth(width);
				//Return the player's next position depending on what die they chose
				if(i==1) {
					XY= currentPos + board.getN();
				}
				
				else if(i==3) {
					XY=  currentPos + 1;
				}
				
				else if(i==5) {
					XY=  currentPos - board.getN();
				}
				
				else if(i==7) {
					XY= currentPos - 1;
				}						
				nodeMove[width -1][0] = XY/board.getN();
				nodeMove[width -1][1] = XY%board.getN();
				nodeMove[width - 1][2] = dice[i - 1];//dice[i]?
				ev = temp.evaluate(currentPos,dice[i -1],super.getBoard(),p); //currentPos??
				if(parent.getEvaluation() >= 1000) {
					ev = 10000;
				}
				
				
				child[width - 1] = new Node(parent,depth,nodeMove[width - 1],parent.getNodeBoard(),ev + parent.getNodeEvaluation(),width,ev);	
			}
		
		}
		j = 0;
		parent.getChildren().clear();
		Node [] tempNode = new Node[parent.getWidth()];
		for(i = 0 ; i < parent.getWidth()-1 ; i++) {

			tempNode[i] = child[i];
			parent.getChildren().add(i,tempNode[i]);
			
		}
		
	}
	
	
	
	
	/*void statistics() {
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
			System.out.println(super.getName() + " in round " + (Game.getRound()-1) + " set the dice to " + path.get(0)[Game.getRound()-1]+ " ");
			if(path.get(2)[Game.getRound()-1]>0) {
				System.out.println("approached a loot");
			}
			
			System.out.println(Arrays.deepToString(path.toArray()));
			
			if(path.get(1)[Game.getRound()-1]==1) {
				System.out.println("collected a loot ");
			}	
			if(path.get(3)[Game.getRound()-1]>0) {
				System.out.println("Ï " +super.getName()+ " sees the minotaur approaching");
			}
			
			
			
			System.out.println("The number of supplies the player has collected "+ super.getName() + " are " + path.get(4)[Game.getRound()-1]);
			
			if(path.get(2)[Game.getRound()-1]>0) {
				System.out.println("The distance in tiles from the nearest supply is " + path.get(2)[Game.getRound()-1]);
			}
			
		}
		
		
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
	
	} */
}
