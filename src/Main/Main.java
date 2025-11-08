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
	
	public static stopWatchX timer = new stopWatchX(10);
	public static Color txtColor = new Color(50, 255, 50);
	
	// Dialog fields (Note: For checkpoint #4 to cycle through map, Implement better method later)
	public static Queue<String> mapKeys = new LinkedList<>();
	
	// Sprite fields
	public static ArrayList<spriteInfo> spritesRight = new ArrayList<>();
	public static ArrayList<spriteInfo> spritesLeft = new ArrayList<>();
	public static ArrayList<spriteInfo> spritesForward = new ArrayList<>();
	public static ArrayList<spriteInfo> spritesDown = new ArrayList<>();
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
			spritesRight.add(new spriteInfo(new Vector2D(0, 0), "sf"+(i)));
			spritesLeft.add(new spriteInfo(new Vector2D(0, 0), "sf"+(i+10)));
			spritesForward.add(new spriteInfo(new Vector2D(0, 0), "sf"+(i+20)));
			spritesDown.add(new spriteInfo(new Vector2D(0, 0), "sf"+(i+30)));	
		}
		
		// Setting up Player (WIP)
		player.addFrames(MovementState.UP, new ArrayList<>(spritesForward));
		player.addFrames(MovementState.LEFT, new ArrayList<>(spritesLeft));
		player.addFrames(MovementState.RIGHT, new ArrayList<>(spritesRight));
		player.addFrames(MovementState.DOWN, new ArrayList<>(spritesDown));
		player.addFrames(MovementState.STOPPED, new ArrayList<>(spritesForward));
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
		
		// Floor testing (WIP)
//		floor.render(ctrl);
		
		// Animation timer
		if (timer.isTimeUp()) {
			// Cycle sprite index
			if (currentSpriteIndex++ >= spritesDown.size() - 1) currentSpriteIndex = 0;
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
				"Sprite Index = " + currentSpriteIndex ,
				"Input Key = "
		};
		for (int i = 0; i < infoStrings.length; i++) {
			ctrl.drawString(xPos, yPos + i * lineSpacing, infoStrings[i], Color.red);
		}
	}
}
