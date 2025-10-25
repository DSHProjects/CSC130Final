package Main;

import java.util.ArrayList;
import java.util.EnumMap;

import Data.spriteInfo;

public class Player {
	
	private int xPos, yPos, animationIdx;
	private EnumMap<MovementState, ArrayList<spriteInfo>> animationFrames = new EnumMap<>(MovementState.class);
	private MovementState pastState = MovementState.STOPPED;
	
	public Player() {
		
	}
	
	public void update(MovementState currentState) {
		
		if (currentState == MovementState.UP) yPos--;
		if (currentState == MovementState.LEFT) xPos--;
		if (currentState == MovementState.DOWN) yPos++;
		if (currentState == MovementState.RIGHT) xPos++;
		
		if (pastState == currentState && currentState != MovementState.STOPPED && animationIdx < animationFrames.get(currentState).size() - 1) {
			animationIdx++;
		}
		else {
			animationIdx = 0;
		}
		pastState = currentState;
		
	}
	
	public void addFrames(MovementState state, ArrayList<spriteInfo> frames) {
		animationFrames.put(state, frames);
	}
	
	public spriteInfo getSpriteInfo() {
		return animationFrames.get(pastState).get(animationIdx);
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
}
