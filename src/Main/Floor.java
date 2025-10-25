package Main;

import Data.Vector2D;
import Data.spriteInfo;

public class Floor {
	
	private spriteInfo[][] floorPtn = new spriteInfo[8][10];
	
	public Floor() {
		for (int i = 0; i < floorPtn.length; i++) {
			for (int j = 0; j < floorPtn[i].length; j++) {
				floorPtn[i][j] = new spriteInfo(new Vector2D(0, 0), "fp0");
			}
		}
	}
	
	public spriteInfo getFloor(int row, int col) {
		return floorPtn[row][col];
	}

}
