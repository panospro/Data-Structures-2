import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;


public class Game {
	static int round; //The round of the game
	static int gameIsOver=0; //Variable that takes the value 1 if the game is over
	static int size=700; //The size of the frame
	static Board board; //Static board object so that the MyFrame class can access it
	static int speed=1; //Game speed
	public Game() {
	round = 0;
	}
	
	public Game(int r) {
	round = r;
	}
	
	public static int getRound() {
	return round;
	}
		
		//All we messed up from the sample solution is changing the two Player class players to 1 MinMaxPlayer and 1 Player
		//Also in the for that it had to print player data for each round we simply changed it and wrote the same data to print
		//just twice so it can get the values of both players who are from different class
	public static void main(String args[]) {
	Player[] players = new Player[1];
	MinMaxPlayer[] minMaxPlayers=new MinMaxPlayer[1];
	HeuristicPlayer[] heuristicPlayers=new HeuristicPlayer[1];
	
	int N = 15;
	int n = 100;
	int numsup = 4;
	String winnerName = null;
	board = new Board(N, numsup, (N * N * 3 + 1) / 2 ); //, (N*N +1)/4
	board.createBoard();
	
		
		//4 THE DELIVERY
		
		
		
		//FRAME
		
		MyFrame frame = new MyFrame();
		frame.setTitle(" Theseus and Minotaur");
		frame.setSize(size,size-100);
		
		//USER INSTRUCTIONS
		MyFrame.ToDo.setBounds(20, -270, 700, 600);
		MyFrame.ToDo.setText("Select a player on the left(important), press the Generate Board button and the Play button, to start the game");
		MyFrame.ToDo.setForeground(Color.black);
		frame.add(MyFrame.ToDo);
		
		
		
		for (int i = 10;(i> 0&&((MyFrame.boardGenerated==0)&&(MyFrame.startedTheGame==0)&& (MyFrame.whichPlayer1==0))); i--) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}	
		}
		
		//SELECT PLAYER TYPE HEURISTICPLAYER
		
		if((MyFrame.boardGenerated==1)&&(MyFrame.startedTheGame==1)&& (MyFrame.whichPlayer1==1)){
		
		heuristicPlayers[0] = new HeuristicPlayer(0, "Theseus", board, 0, 0, 0);
		players[0] = new Player(1, "Minotaur", board, 0, N/2, N/2);
		int[] currentPosition = new int[players.length+heuristicPlayers.length];
		int newPosition = 0;
		currentPosition[0] = 0;
		currentPosition[1] = N * N / 2;
		frame.remove(MyFrame.ToDo);
		
		String[][]x = board.getStringRepresentation(currentPosition[0], currentPosition[1]);
		
		for(int i = 0; i< 2*N + 1; i++) {
			for(int j=0; j< N;j++) {
					if(j == N - 1) {
						System.out.println(x[i][j]);
		}	else {
				System.out.print(x[i][j]);
		}
		}
		}
		
		System.out.println("********* The game begins **********");
		System.out.println();
		Game.round = 0;
		boolean minFlag = false;
		boolean thFlag = false;
		
		for (;;) {
			Game.round++;
			System.out.println("******************************** Round " + Game.round + " * ************************************");
			
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Player: " + heuristicPlayers[0].getName() + " ! !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			int dice = 0;
			newPosition=heuristicPlayers[0].getNextMove(currentPosition[0], players[0]);
			
			//4th deliverable
			
			//Sets the speed of the game..eg if the speed is normal it will play one movement per second, if it is 2 each movement will be done in half a second etc.. If it is equal to 0 then it will take 10 seconds to play so give it time
			//to the reader to see the position of the game
			try {
				if(speed!=0) {
					Thread.sleep(1000/speed);}
				else if(speed==0) {
					Thread.sleep(10000);
			}
			}
			catch(InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
		
		
		
			//SPEED LABEL
			JLabel speedLabel = new JLabel();
			speedLabel.setText("Speed");
			speedLabel.setForeground(Color.black);
			speedLabel.setBounds(350, 440, 50, 50);
			frame.add(speedLabel);
			
			//SPEED
			MyFrame.speed.setBounds(150, 450, 400, 100);
			MyFrame.speed.setPreferredSize(new Dimension(400,20));
			MyFrame.speed.setPaintTicks(true);
			MyFrame.speed.setMinorTickSpacing(10);
			MyFrame.speed.setPaintTrack(true);
			MyFrame.speed.setMajorTickSpacing(1);
			MyFrame.speed.setPaintLabels(true);
			speed=MyFrame.speed.getValue(); //puts the value to be selected in speed
			frame.add(MyFrame.speed); //adds the speed to the frame
			
			//For the next 7 times, for every 2 lines of code, the same is done for all... I define the limits of the corresponding label and add it to the frame //
			
			//BOARD
			MyFrame.board.setBounds(Game.size/2-200, Game.size/2-300, 400, 400);
			frame.add(MyFrame.board);
			
			//THESEUS
			MyFrame.Theseus.setBounds((155+(int)(26.5*(currentPosition[0]%board.getN()))),(425-(int)(26.5*(currentPosition[0]/board.getN()))),17,22);
				frame.add(MyFrame.Theseus);
			
			
	 			
			//MINOTAUR
			MyFrame.Minotaur.setBounds((154+(int)(26.5*(currentPosition[1]%board.getN()))),(425-(int)(26.5*(currentPosition[1]/board.getN()))),17,22);
	 			frame.add(MyFrame.Minotaur);

			//SUPPLY 1
			MyFrame.Supply1.setBounds(154+(int)(((Game.board.getSupplies()[0].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)((( Game.board.getSupplies()[0].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
			frame.add(MyFrame.Supply1);

			//SUPPLY 2
			MyFrame.Supply2.setBounds(154+(int)(((Game.board.getSupplies()[1].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)((( Game.board.getSupplies()[1].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
			frame.add(MyFrame.Supply2);

			//SUPPLY 3
			MyFrame.Supply3.setBounds(154+(int)(((Game.board.getSupplies()[2].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)((( Game.board.getSupplies()[2].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
			frame.add(MyFrame.Supply3);

			//SUPPLY 4
			MyFrame.Supply4.setBounds(154+(int)(((Game.board.getSupplies()[3].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)((( Game.board.getSupplies()[3].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
			frame.add(MyFrame.Supply4);


			//For the next 7 times, for every 5 lines of code, the same thing happens to everything...I initialize the label, define the text it will have, the color it will have, the borders it will have and add it to the frame //

			//ROUND
			JLabel Round = new JLabel();
			Round.setText("Round: " + Game.getRound());
			Round.setForeground(Color.black);
			Round.setBounds(20, -100, 250, 250);
			frame.add(Round);


			//PLAYERNAME 1

			JLabel PlayerName1 = new JLabel();
			PlayerName1.setText(heuristicPlayers[0].getName());
			PlayerName1.setForeground(new Color(28,142,199));
			PlayerName1.setBounds(50, size-400, 250, 250);
			frame.add(PlayerName1);

			//PLAYERNAME 2
			JLabel PlayerName2 = new JLabel();
			PlayerName2.setText(players[0].getName());
			PlayerName2.setForeground(Color.black);
			PlayerName2.setBounds(size-100, size-400, 250, 250);
			frame.add(PlayerName2);



			//MOVE SCORE 1

			JLabel MoveScore1 = new JLabel();
			MoveScore1.setText("Move Score: " + heuristicPlayers[0].path.get(1)[Game.getRound()-1]);
			MoveScore1.setForeground(new Color(28,142,199));
			MoveScore1.setBounds(40, size-385, 250, 250);
			frame.add(MoveScore1);


			//MOVE SCORE 2
			JLabel MoveScore2 = new JLabel();
			MoveScore2.setText("Move Score: " +players[0].getScore());
			MoveScore2.setForeground(Color.black);
			MoveScore2.setBounds(size-110, size-385, 250, 250);
			frame.add(MoveScore2);


			//TOTAL SCORE 1

			JLabel TotalScore1 = new JLabel();
			TotalScore1.setText("Total Score: " +heuristicPlayers[0].getScore());
			TotalScore1.setForeground(new Color(28,142,199));
			TotalScore1.setBounds(40, size-370, 250, 250);
			frame.add(TotalScore1);


			//TOTAL SCORE 2
			JLabel TotalScore2 = new JLabel();
			TotalScore2.setText("Total Score");
			TotalScore2.setForeground(Color.black);
			TotalScore2.setBounds(size-110, size-370, 250, 250);
			frame.add(TotalScore2);



			frame.add(MyFrame.layeredPane); //Adds the layeredPane to the frame
			frame.repaint(); //Redraws the frame
			frame.setVisible(true); //Makes the frame visible
			MyFrame.layeredPane.setBounds(0,0,Game.size,Game.size); //Makes the sizes of the layeredPane
			MyFrame.layeredPane.add(MyFrame.Theseus,1); //Adds Theseus to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Minotaur,1); //Adds the Minotaur to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Supply1,2); //Adds Supply1 to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Supply2,2); //Adds Supply2 to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Supply3,2); //Adds Supply3 to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Supply4,2); //Adds Supply4 to the layeredPane	
				
				
				
				
							
			if(Game.gameIsOver!=1 &&Game.round!=200) {
				frame.remove(Round); //Removes the round from the frame
				frame.remove(PlayerName1); //Remove PlayerName1 from the frame
				frame.remove(PlayerName2); //Remove PlayerName2 from the frame
				frame.remove(MoveScore1); //Removes MoveScore1 from the frame
				frame.remove(MoveScore2); //Removes MoveScore2 from the frame
				frame.remove(TotalScore1); //Removes TotalScore1 from the frame
				frame.remove(TotalScore2); //Removes TotalScore2 from the frame
				}


			//END OF 4TH DELIVERY FOR THE HEURISTICPLAYER






			for(int i=0;i<board.getSupplies().length;i++) {
				if(newPosition==board.getSupplies()[i].getSupplyTileId()) {
				board.getSupplies()[i].setSupplyTileId(-1);
				board.getSupplies()[i].setX(-1);
				board.getSupplies()[i].setY(-1);
	
				heuristicPlayers[0].setScore(heuristicPlayers[0].getScore()+1);
				}
			}
			if (heuristicPlayers[0].getScore() == numsup) {
			winnerName=heuristicPlayers[0].getName() ;
			thFlag= true;
			break;
			}


			if(newPosition == currentPosition[1]) {
			winnerName=players[0].getName() ;
			minFlag = true;
			break;
			}


			heuristicPlayers[0].statistics();
			System.out.println("Current Position:" + currentPosition[0] + " New Position:" + newPosition + " Player Score:" + heuristicPlayers[0].getScore());


			currentPosition[0] = newPosition;
			heuristicPlayers[0].setX(newPosition/N);
			heuristicPlayers[0].setY(newPosition%N);

			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Player: " + players[0].getName() + " ! !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			dice = 0;
			do {
			dice = (int) (Math.random()*8);
			}while(dice % 2 != 1);
			newPosition = players[0].move(currentPosition[1], 1, dice)[0];


			if(newPosition == currentPosition[0]) {
			winnerName=players[0].getName() ;
			minFlag = true;
			break;
			}

			System.out.println("Player: " + players[0].getName() + "Current Position: " + currentPosition[1] + " New Position:" + newPosition);


			currentPosition[1] = newPosition;
			players[0].setX(newPosition/N);
			players[0].setY(newPosition%N);

			String[][]x2 = board.getStringRepresentation(currentPosition[0], currentPosition[1]);
			for(int ii = 0; ii< 2*N + 1; ii++) {
			for(int j=0; j< N;j++) {
			if(j == N - 1) {
			System.out.println(x2[ii][j]);
			}else {
			System.out.print(x2[ii][j]);
			}
			}
			}

			if (Game.round >= 2*n || minFlag || thFlag) {
			break;
			}

			}

			Game.gameIsOver=1;
			heuristicPlayers[0].statistics();
			System.out.println();
			System.out.println("********* The game is over *********");
			System.out.println();
			System.out.println("Rounds played: "+Game.round);
			if(Game.round >= 2*n) {
			System.out.println("The game is a tie!!!");
			}else {
			System.out.println(winnerName +" won the game!!!");
			}
			}

		
		
			//SELECT PLAYER TYPE MINMAXPLAYER
	
			if((MyFrame.boardGenerated==1)&&(MyFrame.startedTheGame==1)&& (MyFrame.whichPlayer1==2)) {
	
			minMaxPlayers[0] = new MinMaxPlayer(0, "Theseus", board, 0, 0, 0);
			players[0] = new Player(1, "Minotaur", board, 0, N/2, N/2);
			int[] currentPosition = new int[players.length+minMaxPlayers.length];
			int newPosition = 0;
			currentPosition[0] = 0;
			currentPosition[1] = N * N / 2;
			frame.remove(MyFrame.ToDo);
			String[][]x = board.getStringRepresentation(currentPosition[0], currentPosition[1]);
			for(int i = 0; i< 2*N + 1; i++) {
			for(int j=0; j< N;j++) {
			if(j == N - 1) {
			System.out.println(x[i][j]);
			}else {
			System.out.print(x[i][j]);
			}
			}
			}
			System.out.println();
			System.out.println("********* The game begins **********");
			System.out.println();
			Game.round = 0;
			boolean minFlag = false;
			boolean thFlag = false;
	
			for (;;) {
			Game.round++;
			System.out.println("******************************** Round " + Game.round + " * ************************************");
	
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Player: " + minMaxPlayers[0].getName() + " ! !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			int dice = 0;
			newPosition=minMaxPlayers[0].getNextMove(currentPosition[0],currentPosition[1], players[0])[0];
	
	
	
			//4th deliverable
	
			//Sets the speed of the game..eg if the speed is normal it will play one movement per second, if it is 2 each movement will be done in half a second etc.. If it is equal to 0 then it will take 10 seconds to play so give it time
			//to the reader to see the position of the game
			try {
			if(speed!=0) {
			Thread.sleep(1000/speed);}
			else if(speed==0) {
			Thread.sleep(10000);
			}
			}
			catch(InterruptedException ex)
			{
			Thread.currentThread().interrupt();
			}
	
	
	
			//SPEED LABEL
			JLabel speedLabel = new JLabel();
			speedLabel.setText("Speed");
			speedLabel.setForeground(Color.black);
			speedLabel.setBounds(350, 440, 50, 50);
			frame.add(speedLabel);
	
			//SPEED
			MyFrame.speed.setBounds(150, 450, 400, 100);
			MyFrame.speed.setPreferredSize(new Dimension(400,20));
			MyFrame.speed.setPaintTicks(true);
			MyFrame.speed.setMinorTickSpacing(10);
			MyFrame.speed.setPaintTrack(true);
			MyFrame.speed.setMajorTickSpacing(1);
			MyFrame.speed.setPaintLabels(true);
			speed=MyFrame.speed.getValue(); //puts the value to be selected in speed
			frame.add(MyFrame.speed); //adds the speed to the frame
	
			//For the next 7 times, for every 2 lines of code, the same is done for all... I define the limits of the corresponding label and add it to the frame //
	
			//BOARD
			MyFrame.board.setBounds(Game.size/2-200, Game.size/2-300, 400, 400);
			frame.add(MyFrame.board);
	
			//THESEUS
			MyFrame.Theseus.setBounds((155+(int)(26.5*(currentPosition[0]%board.getN()))),(425-(int)(26.5*(currentPosition[0]/board.getN()))),17,22);
 			frame.add(MyFrame.Theseus);
	
 			//MINOTAUR
			MyFrame.Minotaur.setBounds((154+(int)(26.5*(currentPosition[1]%board.getN()))),(425-(int)(26.5*(currentPosition[1]/board.getN()))),17,22);
 			frame.add(MyFrame.Minotaur);
 			
			//SUPPLY 1
			MyFrame.Supply1.setBounds(154+(int)(((Game.board.getSupplies()[0].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)((( Game.board.getSupplies()[0].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
			frame.add(MyFrame.Supply1);
	
			//SUPPLY 2
			MyFrame.Supply2.setBounds(154+(int)(((Game.board.getSupplies()[1].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)((( Game.board.getSupplies()[1].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
			frame.add(MyFrame.Supply2);
	 		
			
			//SUPPLY 3
			MyFrame.Supply3.setBounds(154+(int)(((Game.board.getSupplies()[2].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)((( Game.board.getSupplies()[2].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
			frame.add(MyFrame.Supply3);

			//SUPPLY 4
			MyFrame.Supply4.setBounds(154+(int)(((Game.board.getSupplies()[3].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)((( Game.board.getSupplies()[3].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
			frame.add(MyFrame.Supply4);


			//For the next 7 times, for every 5 lines of code, the same thing happens to everything...I initialize the label, define the text it will have, the color it will have, the borders it will have and add it to the frame //

			//ROUND
			JLabel Round = new JLabel();
			Round.setText("Round: " + Game.getRound());
			Round.setForeground(Color.black);
			Round.setBounds(20, -100, 250, 250);
			frame.add(Round);


			//PLAYERNAME 1

			JLabel PlayerName1 = new JLabel();
			PlayerName1.setText(minMaxPlayers[0].getName());
			PlayerName1.setForeground(new Color(28,142,199));
			PlayerName1.setBounds(50, size-400, 250, 250);
			frame.add(PlayerName1);

			//PLAYERNAME 2
			JLabel PlayerName2 = new JLabel();
			PlayerName2.setText(players[0].getName());
			PlayerName2.setForeground(Color.black);
			PlayerName2.setBounds(size-100, size-400, 250, 250);
			frame.add(PlayerName2);



			//MOVE SCORE 1

			JLabel MoveScore1 = new JLabel();
			MoveScore1.setText("Move Score: " + minMaxPlayers[0].path.get(1)[Game.getRound()-1]);
			MoveScore1.setForeground(new Color(28,142,199));
			MoveScore1.setBounds(40, size-385, 250, 250);
			frame.add(MoveScore1);


			//MOVE SCORE 2
			JLabel MoveScore2 = new JLabel();
			MoveScore2.setText("Move Score: " +players[0].getScore());
			MoveScore2.setForeground(Color.black);
			MoveScore2.setBounds(size-110, size-385, 250, 250);
			frame.add(MoveScore2);


			//TOTAL SCORE 1

			JLabel TotalScore1 = new JLabel();
			TotalScore1.setText("Total Score: " +minMaxPlayers[0].getScore());
			TotalScore1.setForeground(new Color(28,142,199));
			TotalScore1.setBounds(40, size-370, 250, 250);
			frame.add(TotalScore1);


			//TOTAL SCORE 2
			JLabel TotalScore2 = new JLabel();
			TotalScore2.setText("Total Score");
			TotalScore2.setForeground(Color.black);
			TotalScore2.setBounds(size-110, size-370, 250, 250);
			frame.add(TotalScore2);



			frame.add(MyFrame.layeredPane); //Adds the layeredPane to the frame
			frame.repaint(); //Redraws the frame
			frame.setVisible(true); //Makes the frame visible
			MyFrame.layeredPane.setBounds(0,0,Game.size,Game.size); //Makes the sizes of the layeredPane
			MyFrame.layeredPane.add(MyFrame.Theseus,1); //Adds Theseus to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Minotaur,1); //Adds the Minotaur to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Supply1,2); //Adds Supply1 to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Supply2,2); //Adds Supply2 to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Supply3,2); //Adds Supply3 to the layeredPane
			MyFrame.layeredPane.add(MyFrame.Supply4,2); //Adds Supply4 to the layeredPane





			if(Game.gameIsOver!=1 &&Game.round!=200) {
			frame.remove(Round); //Removes the round from the frame
			frame.remove(PlayerName1); //Remove PlayerName1 from the frame
			frame.remove(PlayerName2); //Remove PlayerName2 from the frame
			frame.remove(MoveScore1); //Removes MoveScore1 from the frame
			frame.remove(MoveScore2); //Removes MoveScore2 from the frame
			frame.remove(TotalScore1); //Removes TotalScore1 from the frame
			frame.remove(TotalScore2); //Removes TotalScore2 from the frame
			}
				
				
			//END OF 4TH DELIVERY


			for(int i=0;i<board.getSupplies().length;i++) {
			if(newPosition==board.getSupplies()[i].getSupplyTileId()) {
			board.getSupplies()[i].setSupplyTileId(-1);
			board.getSupplies()[i].setX(-1);
			board.getSupplies()[i].setY(-1);

			minMaxPlayers[0].setScore(minMaxPlayers[0].getScore()+1);
			}
			}
			if (minMaxPlayers[0].getScore() == numsup) {
			winnerName=minMaxPlayers[0].getName() ;
			thFlag= true;
			break;
			}

			if(newPosition == currentPosition[1]) {
			winnerName=players[0].getName() ;
			minFlag = true;
			break;
			}

			System.out.println("Player: " + minMaxPlayers[0].getName() + " Current Position:" + currentPosition[0] + " New Position:" + newPosition + " Player Score:" + minMaxPlayers[0]. getScore());


			currentPosition[0] = newPosition;
			minMaxPlayers[0].setX(newPosition/N);
			minMaxPlayers[0].setY(newPosition%N);

			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Player: " + players[0].getName() + " ! !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			dice = 0;
			do {
			dice = (int) (Math.random()*8);
			}while(dice % 2 != 1);
			newPosition = players[0].move(currentPosition[1], 1, dice)[0];


			if(newPosition == currentPosition[0]) {
			winnerName=players[0].getName() ;
			minFlag = true;
			break;
			}

			System.out.println("Player: " + players[0].getName() + " Current Position: " + currentPosition[1] + " New Position:" + newPosition);


			currentPosition[1] = newPosition;
			players[0].setX(newPosition/N);
			players[0].setY(newPosition%N);

			String[][]x2 = board.getStringRepresentation(currentPosition[0], currentPosition[1]);
			for(int ii = 0; ii< 2*N + 1; ii++) {
			for(int j=0; j< N;j++) {
			if(j == N - 1) {
			System.out.println(x2[ii][j]);
			}else {
			System.out.print(x2[ii][j]);
			}
			}
			}

			if (Game.round >= 2*n || minFlag || thFlag) {
			break;
			}

			}

			Game.gameIsOver=1;
			System.out.println();
			System.out.println("********* The game is over *********");
			System.out.println();
			System.out.println("Rounds played: "+Game.round);
			if(Game.round >= 2*n) {
			System.out.println("The game is a tie!!!");
			}else {
			System.out.println(winnerName +" won the game!!!");
			}


			}

			}


}