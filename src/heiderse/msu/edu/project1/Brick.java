package heiderse.msu.edu.project1;

import android.graphics.Bitmap;

public class Brick {

	private Bitmap image;
	
	private float xPos;
	
	private float yPos;
	
	private int weight;
	
	// Set the weight to xPos, yPos, and weight to 0 for now
	public Brick() 
	{
		xPos = 0;
		yPos = 0;
		weight = 0;
	}

	// Get the value of the brick's x position
	public float getxPos() {
		return xPos;
	}

	// Set the value of the brick's x position
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	

}
