import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener {
	
	//private static final JComboBox[] TypesOfPlayer1 = null; //the type of the 1st player
	public static int whichPlayer1=0; //which player will play
	public static int whichPlayer2=0; //the minotaur
	public static int boardGenerated=0; //if dashboard created
	public static int startedTheGame=0; //if the game started
	public static int quitTheGame=0; //exit the game
	JButton GenerateBoard; //the button to create the dashboard
	JButton Play; //the button to create the play
	JButton Quit; //the button to create the quit
	JComboBox TypeOfPlayer1; //the type of player 1
	JComboBox TypeOfPlayer2; //the type of player 2
	static JLabel board = new JLabel(); //create and initialize the board
	static JLabel Theseus= new JLabel(); //create and initialize Theseus
	static JLabel Minotaur= new JLabel(); //create and initialize Minotaur
	static JLabel Supply1=new JLabel(); //create and initialize Supply1
	static JLabel Supply2=new JLabel(); //create and initialize Supply2
	static JLabel Supply3=new JLabel(); //create and initialize Supply3
	static JLabel Supply4=new JLabel(); //create and initialize Supply4
	static JLayeredPane layeredPane=new JLayeredPane(); //create and initialize the layeredPane
	static JLabel ToDo=new JLabel(); //create and initialize the ToDo for what the gamer needs to do to start the game
	static JSlider speed=new JSlider(0,4,1); //the game speed
	MyFrame(){

		//For the next 7 times, for every 2 series of codes, the same thing happens to all... I set the image of the corresponding label from the saved images I have //
	
		//BOARD
		ImageIcon maze = new ImageIcon(getClass().getResource("maze.png"));
		board.setIcon(maze);
	
		//THESEUS
		ImageIcon Theseus1 = new ImageIcon(getClass().getResource("Theseus.png"));
		Theseus.setIcon(Theseus1);
	
		//MINOTAUR
		ImageIcon Minotaur1 = new ImageIcon(getClass().getResource("Minotaur.png"));
		Minotaur.setIcon(Minotaur1);
	
		//FOOD 1
		ImageIcon S1= new ImageIcon(getClass().getResource("supply1.png"));
		Supply1.setIcon(S1);
	
		//FOOD 2
		ImageIcon S2= new ImageIcon(getClass().getResource("supply2.png"));
		Supply2.setIcon(S2);
	
		//FOOD 3
		ImageIcon S3= new ImageIcon(getClass().getResource("supply3.png"));
		Supply3.setIcon(S3);
	
		//FOOD 4
		ImageIcon S4= new ImageIcon(getClass().getResource("supply4.png"));
		Supply4.setIcon(S4);
	
		//For the next 2 times, for every 5 lines of code, the same is done for all ... I define the names of the types of players, initialize the combobox, define the size it will have and add it to the frame //
	
		//TYPE OF PLAYER
		String[] TypesOfPlayer1= {" Heuristic Player "," MinMax Player "};
		TypeOfPlayer1 = new JComboBox(TypesOfPlayer1);
		TypeOfPlayer1.addActionListener(this);
		TypeOfPlayer1.setBounds(0, Game.size-230, 130,50);
		this.add(TypeOfPlayer1);
	
		String[] TypesOfPlayer2= {" RandomPlayer "};
		TypeOfPlayer2 = new JComboBox(TypesOfPlayer2);
		TypeOfPlayer2.setBounds(Game.size-135, Game.size-230, 130, 50);
		TypeOfPlayer2.addActionListener(this);
		this.add(TypeOfPlayer2);
	
		//Create a button for the generate board, set the size, text, if it will be focusable and add it to the frame
	
		//GENERATE BOARD
		GenerateBoard = new JButton();
		GenerateBoard.setBounds(Game.size/2-150,Game.size-160,130,30);
		GenerateBoard.setText(" Generate Board ");
		GenerateBoard.setFocusable(false);
		GenerateBoard.addActionListener(this);
		this.add(GenerateBoard);
	
		//Create a button for play, set size, text, if it will be focusable and add it to the frame
	
		//PLAY
		Play=new JButton();
		Play.setBounds(Game.size/2-15,Game.size-160,70,30);
		Play.setText(" Play " );
		Play.addActionListener(this);
		Play.setFocusable(false);
		this.add(Play);
	
		//Create button for quit, set size, text, if it will be focusable and add it to the frame
		
		//QUIT
		Quit=new JButton();
		Quit.setBounds(Game.size/2+60,Game.size-160,70,30);
		Quit.setText(" Quit " );
		Quit.addActionListener(this);
		Quit.setFocusable(false);
		this.add(Quit);
		
		//All labels become opaque
		Theseus.setOpaque(true);
		Minotaur.setOpaque(true);
		Supply1.setOpaque(true);
		Supply2.setOpaque(true);
		Supply3.setOpaque(true);
		Supply4.setOpaque(true);
		board.setOpaque(true);


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the frame close when someone presses the "x" on the top right
		this.setLayout(null);
		this.setResizable(false); //makes the frame a fixed size, without resizing
		this.setVisible(true); //makes the frame visible




		}


		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		//What if someone clicks the Generate Board button

		if(e.getSource()==GenerateBoard) {
		boardGenerated=1; //declares that the dashboard has been created
		 GenerateBoard.setEnabled(false); //disables the button after it has been pressed once
		 layeredPane.setBounds(0,0,Game.size,Game.size); //sets the size of the layeredPane
		 
		 //For the next 7 times, for every 5 lines of code, the same thing happens to everything...I initialize the label, define the icon it will have, the borders it will have, add it to the frame and the layeredPane//
		 
		 //BOARD
		 JLabel board=new JLabel();
		 ImageIcon maze = new ImageIcon(getClass().getResource("maze.png"));
		 board.setIcon(maze);
		 board.setBounds(Game.size/2-200, Game.size/2-300, 400, 400);
		 this.add(board);
		 layeredPane.add(board);
		 
		 
		 //THESEUS
		 ImageIcon Theseus1 = new ImageIcon(getClass().getResource("Theseus.png"));
		Theseus.setIcon(Theseus1);
		Theseus.setBounds(155,423,20,25);
		layeredPane.add(Theseus);
		 this.add(Theseus);
		 
		 this.repaint();
		this.setVisible(true);


		//MINOTAUR
		 ImageIcon Minotaur1= new ImageIcon(getClass().getResource("Minotaur.png"));
		Minotaur.setIcon(Minotaur1);
		Minotaur.setBounds(340,239,20,23);
		layeredPane.add(Minotaur);
		 this.add(Minotaur);
		 this.repaint();
		this.setVisible(true);



		//FOOD 1
		ImageIcon S1= new ImageIcon(getClass().getResource("supply1.png"));
		Supply1.setIcon(S1);
		Supply1.setBounds(154+(int)(((Game.board.getSupplies()[0].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)(((Game. board.getSupplies()[0].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
		layeredPane.add(Supply1);
		 this.add(Supply1);
		 this.repaint();
		this.setVisible(true);

		 //FOOD 2
		ImageIcon S2= new ImageIcon(getClass().getResource("supply2.png"));
		Supply2.setIcon(S2);
		Supply2.setBounds(154+(int)(((Game.board.getSupplies()[1].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)(((Game. board.getSupplies()[1].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
		layeredPane.add(Supply2);
		 this.add(Supply2);
		 this.repaint();
		this.setVisible(true);

		 //FOOD 3
		ImageIcon S3= new ImageIcon(getClass().getResource("supply3.png"));
		Supply3.setIcon(S3);
		Supply3.setBounds(154+(int)(((Game.board.getSupplies()[2].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)(((Game. board.getSupplies()[2].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
		layeredPane.add(Supply3);
		 this.add(Supply3);
		 this.repaint();
		this.setVisible(true);

		 //FOOD 4
		ImageIcon S4= new ImageIcon(getClass().getResource("supply4.png"));
		Supply4.setIcon(S4);
		Supply4.setBounds(154+(int)(((Game.board.getSupplies()[3].getSupplyTileId()%Game.board.getN())*26.5)),425-(int)(((Game. board.getSupplies()[3].getSupplyTileId()/Game.board.getN())*26.5)),17,22);
		layeredPane.add(Supply4);
		 this.add(Supply4);
		 this.repaint();
		this.setVisible(true);
			
			
			
		//Creating walls with two for ...one scans all the tiles if they have walls below and one (in case none escaped) scans the tiles if they have walls above...if they have then adds them with an icon defined in the frame
		 //HORIZONTAL WALLS
		 for(int i=25;i<Game.board.getN()*Game.board.getN();i++) {
			if(Game.board.getTiles()[i].getDown()==true) {
				ImageIcon wallO = new ImageIcon(getClass().getResource("horizontal_line.png"));
				JLabel wall1= new JLabel();
				wall1.setIcon(wallO);
				wall1.setBounds(151+(int)(((i%Game.board.getN())*26.5)),447-(int)(((i/Game.board.getN())*26.5)), 27, 4);
				layeredPane.add(wall1);
				this.add(wall1);
			}
		 }
		 
		 for(int i=0;i<Game.board.getN()*Game.board.getN()-25;i++) {
			if(Game.board.getTiles()[i].getUp()==true) {
				if(Game.board.getTiles()[i+Game.board.getN()].getDown()==true) {
				continue;
			}
			else {
				ImageIcon wallO = new ImageIcon(getClass().getResource("horizontal_line.png"));
				JLabel wall1= new JLabel();
				wall1.setIcon(wallO);
				wall1.setBounds(151+(int)(((i%Game.board.getN())*26.5)),421-(int)(((i/Game.board.getN())*26.5)), 27, 4);
				layeredPane.add(wall1);
				this.add(wall1);
				}
			}

		 }
		 
		 
		 //Creating walls with two for ...one scans all the tiles if they have walls on the left and one (in case none are missed) scans the tiles if they have walls on the right...if they have then adds them with an icon set to the frame
		 
		 //VERTICAL WALL LEFT
		 for(int i=0;i<Game.board.getN()*Game.board.getN();i++) {
			if(Game.board.getTiles()[i].getLeft()==true&&(i%Game.board.getN()!=0)) {
				ImageIcon wallV = new ImageIcon(getClass().getResource("vertical_line.png"));
				JLabel wall2= new JLabel();
				wall2.setIcon(wallV);
				wall2.setBounds(150+(int)(((i%Game.board.getN())*26.5)) , 421-(int)(((i/Game.board.getN())*26.5)) , 4 , 27);
				layeredPane.add(wall2);
				this.add(wall2);
	
			}
		 }
		 
		 
		 //VERTICAL WALL RIGHT DON'T LET ANYONE ESCAPE
		 for(int i=0;i<Game.board.getN()*Game.board.getN()-1;i++) {
			 if(Game.board.getTiles()[i+1].getLeft()==true) {
				 continue;
		}
		 else if(Game.board.getTiles()[i].getRight()==true&&(i%Game.board.getN()!=(Game.board.getN()-1))) {
			ImageIcon wallV = new ImageIcon(getClass().getResource("vertical_line.png"));
			JLabel wall2= new JLabel();
			wall2.setIcon(wallV);
			wall2.setBounds(176+(int)(((i%Game.board.getN())*26.5)) , 421-(int)(((i/Game.board.getN())*26.5)) , 4 , 27);
			layeredPane.add(wall2);
			this.add(wall2);
		}
		 }
		 
		 
		 this.add(layeredPane);
		 this.repaint();
		this.setVisible(true);
		}


		//What if someone clicks the Generate Play button

		else if(e.getSource()==Play) {
		startedTheGame=1; //declares that the game is starting
		}

		//What happens if someone clicks the button Generate Quit

		else if(e.getSource()==Quit) {
		quitTheGame=1;
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); //closes the frame if clicked
		}

		//What if someone clicks the combobox typeofplayer1

		else if(e.getSource()==TypeOfPlayer1) {
		whichPlayer1=TypeOfPlayer1.getSelectedIndex()+1; //sets the corresponding player
		}

		//What if someone clicks the combobox typeofplayer2

		else if(e.getSource()==TypeOfPlayer2) {
		whichPlayer2=TypeOfPlayer2.getSelectedIndex()+1; //sets the corresponding player
		}

		}

}
