package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static EZFileRead ezr = new EZFileRead("Dialog.txt");
	public static HashMap<String, String> map = new HashMap<>();
	public static stopWatchX timer_dialog = new stopWatchX(4000);
	
	// Testing Floor class
	public static Floor floor = new Floor();
	
	// Testing Player class
	public static Player player = new Player();
	
	public static Vector2D currentVec = new Vector2D(-100, -100);
	public static Queue<Vector2D> vecs1 = new LinkedList<>();
	public static Queue<Vector2D> vecs2 = new LinkedList<>();
	public static stopWatchX timer = new stopWatchX(10);
	public static Color txtColor = new Color(50, 255, 50);
	
	// Dialog fields (Note: For checkpoint #4 to cycle through map, Implement better method later)
	public static Queue<String> mapKeys = new LinkedList<>();
	
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
		
		// Put sprite dialog in map
		for (int i = 0; i < ezr.getNumLines(); i+=2) {
			String raw = ezr.getLine(i);
			StringTokenizer st = new StringTokenizer(raw, "*");
			String Key = st.nextToken();
			String Value = st.nextToken();
			map.put(Key, Value);
			mapKeys.add(Key);
		}
		
		// Add sprite frames to list
		for (int i = 0; i < 10; i++) {
			sprites.add(new spriteInfo(new Vector2D(0, 0), "sf"+i));
		}
		
		// Setting up Player (WIP)
		player.addFrames(MovementState.UP, new ArrayList<>(sprites));
		player.addFrames(MovementState.LEFT, new ArrayList<>(sprites));
		player.addFrames(MovementState.RIGHT, new ArrayList<>(sprites));
		player.addFrames(MovementState.DOWN, new ArrayList<>(sprites));
		player.addFrames(MovementState.STOPPED, new ArrayList<>(sprites));
		
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
		
		// Sprite Dialog text
		ctrl.drawString(150, 250, map.get(mapKeys.peek()), Color.red);
		
		// Dialog Timer Cycle dialog
		if (timer_dialog.isTimeUp()) {
			mapKeys.add(mapKeys.poll());
			timer_dialog.resetWatch();
		}
		
		// Sprite Animation Position and Render
		ctrl.addSpriteToFrontBuffer(player.getXPos(), player.getYPos(), player.getSpriteInfo().getTag());
		
		
		// Temp for floor pattern (WIP)
//		for (int i = 0; i < 8; i++) {
//			for (int j = 0; j < 10; j++) {
//				ctrl.addSpriteToFrontBuffer(j, i, null);
//			}
//		}
//		
		
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
			player.update(PlayerMovementInput.getState());
			timer.resetWatch();
		}
		
		tmpDebugHelper(ctrl, false); // Temporary Debug Method
		
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
				"Sprite Index = " + currentSpriteIndex ,
				"Input Key = "
		};
		for (int i = 0; i < infoStrings.length; i++) {
			ctrl.drawString(xPos, yPos + i * lineSpacing, infoStrings[i], Color.red);
		}
	}
}
