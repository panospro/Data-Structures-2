import java.util.ArrayList;

//The Node class that we will need for the MinMax algorithm
public class Node {
	private Node parent;					//The node-father of the node(Node)
	
	private ArrayList<Node> children; //Variable of type ArrayList<Node>, dynamic array containing the children of the Node node
	
	private int nodeDepth; 		//Variable that measures the "depth" at which the particular Node is. This is the depth of the parent + 1 starting
								//the count from the Node that is "parent" of the movements we have for the specific position
	
	private int[] nodeMove; //Move representing the Node, as an array of integers containing the x, y of the tile of the current position and the number of the dice.
	private Board nodeBoard;				//The game board for the given move-node.

	private double nodeEvaluation; //Value of the motion evaluation function represented by this node
	
	
	private int Width; //Variable that represents the available moves ie the maximum number of children the Node can have
	
	private double evaluation; //Variable with the evaluation only for the specific movement represented by the Node
	

	public Node() {
		
	}
	
	public Node(Node parent,int nodeDepth,int[] nodeMove , Board nodeBoard, double nodeEvaluation, int Width , double evaluation) {
		this.parent = parent;
		this.nodeDepth = nodeDepth;
		this.nodeMove = nodeMove;
		this.nodeBoard = new Board(nodeBoard);
		this.nodeEvaluation = nodeEvaluation;
		this.Width = Width;
		this.evaluation = evaluation;			
		children = new ArrayList<Node>(Width);
		
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public int getNodeDepth() {
		return nodeDepth;
	}

	public void setNodeDepth(int nodeDepth) {
		this.nodeDepth = nodeDepth;
	}

	public int[] getNodeMove() {
		return nodeMove;
	}

	public void setNodeMove(int[] nodeMove) {
		this.nodeMove = nodeMove;
	}

	public Board getNodeBoard() {
		return nodeBoard;
	}

	public void setNodeBoard(Board nodeBoard) {
		this.nodeBoard = nodeBoard;
	}

	public double getNodeEvaluation() {
		return nodeEvaluation;
	}

	public void setNodeEvaluation(double nodeEvaluation) {
		this.nodeEvaluation = nodeEvaluation;
	}

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public double getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}



}
