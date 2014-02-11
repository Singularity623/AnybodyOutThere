package heiderse.msu.edu.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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
	
	/**
	 * Draw the puzzle piece
	 * @param canvas Canvas we are drawing on
	 * @param marginX Margin x value in pixels
	 * @param marginY Margin y value in pixels
	 * @param stackSize Size we draw the stack in pixels
	 * @param scaleFactor Amount we scale the puzzle pieces when we draw them
	 */
	public void draw(Canvas canvas, int marginX, int marginY, int stackSize, float scaleFactor) {
		canvas.save();
		
		// Convert x,y to pixels and add the margin, then draw
		canvas.translate(marginX + xPos * stackSize, marginY + yPos * stackSize);
		
		// Scale it to the right size
		canvas.scale(scaleFactor, scaleFactor);
		
		// This magic code makes the center of the image at 0, 0
		canvas.translate(-image.getWidth() / 2, -image.getHeight() / 2);
		
		// Draw the bitmap
		canvas.drawBitmap(image, 0, 0, null);
		canvas.restore();
	}
	
	

}
