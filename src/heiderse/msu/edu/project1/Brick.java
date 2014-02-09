package heiderse.msu.edu.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Brick {

	private Bitmap image;
	
	private float xPos;
	
	private float yPos;
	
	private int weight;
	
	// Set the w to xPos, yPos, and weight to 0 for now
	public Brick(Context context, int id, float x, float y, int w) 
	{
		xPos = x;
		yPos = y;
		weight = w;
		
		image = BitmapFactory.decodeResource(context.getResources(), id);
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
