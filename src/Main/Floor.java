package Main;

import Data.Vector2D;
import Data.spriteInfo;
import logic.Control;

public class Floor {
	
	private spriteInfo[][] floorPtn = new spriteInfo[6][10];
	
	public Floor() {
		int temp = 0;
		for (int i = 0; i < floorPtn.length; i++) {
			for (int j = 0; j < floorPtn[i].length; j++) {
				floorPtn[i][j] = new spriteInfo(new Vector2D(0, 0), "fp"+temp);
				temp++;
			}
		}
	}
	
	public void render(Control ctrl) {
		for (int i = 0; i < floorPtn.length; i++) {
			for (int j = 0; j < floorPtn[i].length; j++) {
				ctrl.addSpriteToFrontBuffer(j * 128, i * 128, floorPtn[i][j].getTag());
			}
		}
	}
}
