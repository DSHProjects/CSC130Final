/* This class is used to give you a handy way to pass a pair of 2D coordinates as a single object. The behavior (methods) of the class should allow for robust options in the future. */

package Data;

public class Vector2D {
	// Fields
	private int xPos, yPos;
	
	// Constructor
	public Vector2D(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}
	
	// Methods
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public void setX(int newX){
		this.xPos = newX; 
	}
	
	public void setY(int newY){
		this.yPos = newY;
	}
	
	public void adjustX(int adjustment){
		this.xPos += adjustment;
	}
	
	public void adjustY(int adjustment){
		this.yPos += adjustment;
	}
}
