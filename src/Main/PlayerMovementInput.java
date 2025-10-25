package Main;

public class PlayerMovementInput {
	private static MovementState mvState = MovementState.STOPPED;
	public static void setDirection(MovementState nextMvState) {
		mvState = nextMvState;
	}
	public static MovementState getState() {
		return mvState;
	}
}
