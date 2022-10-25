public class Supply {
	int supplyId;
	int x;
	int y;
	int supplyTileId;
	
	public Supply() {
		supplyId = 1;
		x = 0; 
		y = 0;
		supplyTileId = 0;
	}
	
	public Supply(int sId, int sx, int sy, int sTId) {
		supplyId = sId;
		x = sx;
		y = sy;
		supplyTileId = sTId;
	}
	
	public Supply(Supply s) {
		supplyId = s.supplyId;
		x = s.x;
		y = s.y;
		supplyTileId = s.supplyTileId;
	}
	
	void setSupplyId(int id) {
		supplyId = id;
	}
	
	void setX(int x1) {
		x = x1;
	}
	
	void setY(int y1) {
		y = y1;
	}
	
	void setSupplyTileId(int tid) {
		supplyTileId = tid;
	}
	
	int getSupplyId() {
		return supplyId;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	int getSupplyTileId() {
		return supplyTileId;
	}
}