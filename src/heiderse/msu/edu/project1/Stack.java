package heiderse.msu.edu.project1;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.graphics.Paint;

public class Stack {
	/**
	 * Context - where to find things like resources
	 */
	private Context context;
	
	/**
	 * Collection of bricks
	 */
	public ArrayList<Brick> bricks;
	
	/**
	 * Getter for brick stack size
	 * @return brick stack size
	 */
	public int getBrickStackSize(){
		return  bricks.size();
	}
	
	/**
	 * Current y-offset
	 */
	private float yOffset = 0;
	
	/**
     * The size of the stack in pixels
     */
    private int stackSize;
    
    /**
	 * Ratio of brick width and the view
	 */
	final static float SCALE_IN_VIEW = 0.5f;
    
    /**
     * How much we scale the bricks
     */
    private float scaleFactor = 1;
    
    /**
     * Left margin in pixels
     */
    private int marginX;
     
    /**
     * Top margin in pixels
     */
    private int marginY;
    
    /**
     * Brick image size in pixel
     */
    private int brickWidth;
    private int brickHeight;

	public Stack(Context ct) {
		context = ct;
		
		// Create an empty brick array
		bricks = new ArrayList<Brick>();
		
		// Get the brick size
		Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_green1);
		brickWidth = b.getWidth();
		brickHeight = b.getHeight();
	}
	
	public void draw(Canvas canvas) {
		
		int wid = canvas.getWidth();
		int hit = canvas.getHeight();
		
		// Determine the minimum of the two dimensions
		// and assign to stackSize
		stackSize = wid < hit ? wid : hit;
		
		// Compute the margins
		// horizontal : center
		// vertical : bottom
		marginX = (wid - stackSize) / 2;
		marginY = (hit - stackSize);
		
		//Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//fillPaint.setColor(0xffcccccc);
		
		//canvas.drawRect(marginX, marginY, marginX + stackSize, marginY + stackSize, fillPaint);
		
		scaleFactor = (float) stackSize * SCALE_IN_VIEW / brickWidth;
		
		// Translate yOffset
		canvas.save();
		canvas.translate(0, yOffset*stackSize);
		
		for(Brick brick : bricks) {
			brick.draw(canvas, marginX, marginY, stackSize, scaleFactor);
		}
		canvas.restore();		
	}
	
	/**
	 * Add a brick into the collection on top of the last brick
	 * @param imageId Image of the brick
	 * @param w Weight of the brick
	 */
	public void addBrick(int imageId, int w){
		float x, y;
		
		float scaledBrickHeight = (float)brickHeight*SCALE_IN_VIEW/brickWidth;
		
		if (getBrickStackSize() == 0){
			// First brick placed at the bottom of the view
			// & horizontal centered
			x = 0.5f;
			y = 1 - scaledBrickHeight/2;
		}
		else {
			// The new brick will be on top of the last brick
			Brick lastBrick = bricks.get(bricks.size() - 1);
			
			Bitmap b = BitmapFactory.decodeResource(context.getResources(), imageId);
			
			x = lastBrick.getxPos();
			y = lastBrick.getyPos() - scaledBrickHeight;
		}
		
		addBrick(imageId, x, y ,w);
	}
	
	private void addBrick(int imageId, float x, float y, int w){
		bricks.add(new Brick(context, imageId, x, y ,w));
	}
	
	/**
	 * The name of the bundle keys to save the stack
	 */
	private final static String LOCATIONS = "Stack.locations";
	private final static String WEIGHTS = "Stack.weights";
	
	/**
	 * Save the brick stack to a bundle
	 * @param bundle The bundle we save to
	 */
	public void saveInstanceState(Bundle bundle) {
		float [] locations = new float[bricks.size() * 2];
 		int [] weights = new int[bricks.size()];
 		
 		for(int i=0;  i<bricks.size(); i++) {
			Brick brick = bricks.get(i);
			locations[i*2] = brick.getxPos();
			locations[i*2+1] = brick.getyPos();
			weights[i] = brick.getWeight();
		}
 		
 		bundle.putFloatArray(LOCATIONS, locations);
		bundle.putIntArray(WEIGHTS,  weights);
	}
	
	/**
	 * Read the brick stack from a bundle
	 * @param bundle The bundle we save to
	 */
	public void loadInstanceState(Bundle bundle) {
		float [] locations = bundle.getFloatArray(LOCATIONS);
		int [] weights = bundle.getIntArray(WEIGHTS);
		
		int playFirst = bundle.getInt(StackerActivity.PLAY_FIRST);

		
		for (int i=0; i < weights.length; i++){	
			/*
			 *  Decide the image id for the brick
			 *  Based on the player who player first and current index
			 */
			int imageId;
			switch ((i + playFirst)% StackerActivity.NUMBER_OF_PLAYERS){
				case 0:
					imageId = StackerActivity.FIRST_PLAYER_COLOR;
					break;
				default:
					imageId = StackerActivity.SECOND_PLAYER_COLOR;
					break;
			}
						
			addBrick(imageId, locations[2*i], locations[2*i+1], weights[i]);
		}
	}
	
	/**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;
    
    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;
	
	/**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
		//
        // Convert an x,y location to a relative location in the puzzle.
        //
        
        float relX = (event.getX() - marginX) / stackSize;
        float relY = (event.getY() - marginY) / stackSize;
        
        switch(event.getActionMasked()) {
        
        case MotionEvent.ACTION_DOWN:
            return onTouched(relX, relY);
            
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            return onReleased(view, relX, relY);
            
        case MotionEvent.ACTION_MOVE:
        	// If we are dragging, move the piece and force a redraw
            
            yOffset += relY - lastRelY;
            yOffset = (yOffset < 0)? 0 : yOffset;
            lastRelX = relX;
            lastRelY = relY;
            view.invalidate();
            return true;
        }
        
        return false;
    }
    
    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {
    	lastRelX = x;
        lastRelY = y;
        
        return true;
    }
    
    /**
     * Handle a release of a touch message.
     * @param x x location for the touch release, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch release, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onReleased(View view, float x, float y) {

        return false;
    }

}
