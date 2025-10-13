package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import Data.spriteInfo;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	
	public static Vector2D currentVec = new Vector2D(-100, -100);
	public static Queue<Vector2D> vecs1 = new LinkedList<>();
	public static Queue<Vector2D> vecs2 = new LinkedList<>();
	public static stopWatchX timer = new stopWatchX(10);
	public static Color txtColor = new Color(50, 255, 50);
	
		// Sprite fields
	public static ArrayList<spriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;
		
	
	// End Static fields...
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		
		// Add sprite frames to list
		for (int i = 0; i < 10; i++) {
			sprites.add(new spriteInfo(new Vector2D(0, 0), "sf"+i));
		}
		
		// Add frames
		int stepSize = 2;
		int xStart = -200;
		int xEnd = 1300;
		for (int i = 0; i < Math.abs(xEnd - xStart) / stepSize; i++) {
			vecs1.add(new Vector2D(xStart + i * stepSize, 150));
		}
		currentVec = vecs1.remove();
	}
	
	
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		
		// Sprite Animation Position and Render
		String spriteFrameTag = sprites.get(currentSpriteIndex).getTag();
		int spriteX = currentVec.getX() + sprites.get(currentSpriteIndex).getCoords().getX();
		int spriteY = currentVec.getY() + sprites.get(currentSpriteIndex).getCoords().getY();
		ctrl.addSpriteToFrontBuffer(spriteX, spriteY, spriteFrameTag);
		
		tmpDebugHelper(ctrl, false); // Temporary DebugMethod
		
		// Animation timer
		if (timer.isTimeUp()) {
			if (!vecs1.isEmpty()) {
				currentVec = vecs1.remove();
				vecs2.add(currentVec);
			} else {
				addAllAndClear(vecs1, vecs2);;
			}
			
			// Cycle sprite index
			if (currentSpriteIndex++ >= sprites.size() - 1) currentSpriteIndex = 0;
			
			
			timer.resetWatch();
		}
	}
	
	
	
	
	
	// Additional Static methods below...(if needed)

	private static <T> void addAllAndClear(Queue<T> queue1, Queue<T> queue2) {
		while (!queue2.isEmpty()) {
			queue1.add(queue2.remove());
		}
	}
	
		//	(Testing purposes)
	private static void tmpDebugHelper(Control ctrl, boolean active) {
		if (!active) return;
		int xPos = 50;
		int yPos = 300;
		int lineSpacing = 20;
		String[] infoStrings = {
				"vecs1Count = " + vecs1.size(),
				"vecs2Count = " + vecs2.size(),
				"Sprite Orgin = [" + currentVec.getX() + ", " + currentVec.getY() + "]",
				"Sprite Index = " + currentSpriteIndex 
		};
		for (int i = 0; i < infoStrings.length; i++) {
			ctrl.drawString(xPos, yPos + i * lineSpacing, infoStrings[i], Color.red);
		}
	}
}
