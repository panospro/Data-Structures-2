public class Tile {
	int tileId;
	int x;
	int y;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
		
	public Tile() {
		tileId = 1;
		x = 0; 
		y = 0;
		up = false;
		down = false;
		left = false;
		right = false;
	}
		
	public Tile(int tId, int wx, int wy, boolean wup, boolean wdown, boolean wleft, boolean wright) {
		tileId = tId;
		x = wx; 
		y = wy;
		up = wup;
		down = wdown;
		left = wleft;
		right = wright;
	}
		
	public Tile(Tile t) {
		tileId = t.tileId;
		x = t.x; 
		y = t.y;
		up = t.up;
		down = t.down;
		left = t.left;
		right = t.right;
	}
		
	void setTileId(int id) {
		tileId = id;
	}
		
	void setX(int x1) {
		x = x1;
	}
		
	void setY(int y1) {
		y = y1;
	}
		
	void setUp(boolean bup) {
		up = bup;
	}
		
	void setDown(boolean bdown) {
		down = bdown;
	}
	
	void setLeft(boolean bleft) {
		left = bleft;
	}
	
	void setRight(boolean bright) {
		right = bright;
	}
		
	int getTileId() {
		return tileId;
	}
		
	int getX() {
		return x;
	}
		
	int getY() {
		return y;
	}
		
	boolean getUp() {
		return up;
	}
		
	boolean getDown() {
		return down;
	}
		
	boolean getLeft() {
		return left;
	}
		
	boolean getRight() {
		return right;
	}
}