package heiderse.msu.edu.project1;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Stack {
	/**
	* Context - where to find things like resources
	*/
	private Context context;
	
	private boolean flag;
	
	/**
	* StackView - which contains this stack
	*/
	private StackView stackView;
	
	private int wid;
	private int hit;
	
	/**
	* Collection of bricks
	*/
	public ArrayList<Brick> bricks;
	
	/**
	* Getter for brick stack size
	* @return brick stack size
	*/
	public int getBrickStackSize(){
		return bricks.size();
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
    
    /**
     * The current brick on top of the stack
     */
    private Brick currentBrick;

    public Stack(Context ct, StackView sV) {
    	context = ct;
    	stackView = sV;

    	// Create an empty brick array
    	bricks = new ArrayList<Brick>();

    	// Get the brick size
    	Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_green1);
    	brickWidth = b.getWidth();
    	brickHeight = b.getHeight();
    	currentBrick = null;
    	flag = false;

    }


    float angle=0;
    long lastTime= -1;
    
    public void draw(Canvas canvas) {

		wid = canvas.getWidth();
		hit = canvas.getHeight();
		
		// Determine the minimum of the two dimensions
		// and assign to stackSize
		stackSize = wid < hit ? wid : hit;
		
		// Compute the margins
		// horizontal : center
		// vertical : bottom
		marginX = (wid - stackSize) / 2;
		marginY = (hit - stackSize);
		
		scaleFactor = (float) stackSize * SCALE_IN_VIEW / brickWidth;
		
		// Translate yOffset
		canvas.save();
		canvas.translate(0, yOffset*stackSize);
		if (lastStableBrick > 0) {
			if (lastTime <= 0) {
				angle = 0;
				lastTime = SystemClock.uptimeMillis();;
			}
			// Determine the time step
			long time = SystemClock.uptimeMillis();
			float delta = (time - lastTime) * 0.001f;
			lastTime = time;
			// Animation updates
			angle += delta * 5 * Math.PI * fallingCoefficient;

			for(int i=0; i<lastStableBrick; i++){
				bricks.get(i).draw(canvas, marginX, marginY, stackSize, scaleFactor);
			}

			canvas.translate(rotationCenterX*stackSize + marginX, rotationCenterY*stackSize + marginY);
			canvas.rotate(angle);
			canvas.translate(-rotationCenterX*stackSize - marginX, -rotationCenterY*stackSize-marginY);

			for(int i=lastStableBrick; i<bricks.size(); i++){
				bricks.get(i).draw(canvas, marginX, marginY, stackSize, scaleFactor);
			}

			stackView.postInvalidate();
			if (angle * fallingCoefficient > 2 * 5 * Math.PI)
			{
				Reset();
				stackView.invalidate();
			}
		}
		else{
			for(Brick brick : bricks) {
				brick.draw(canvas, marginX, marginY, stackSize, scaleFactor);
			}
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
		if (lastStableBrick == 0){
			if (getBrickStackSize() == 0) {
				// First brick placed at the bottom of the view
				// & horizontal centered
				x = 0.5f;
				y = 1 - scaledBrickHeight/2;
			}
			else {
				// The new brick will be on top of the last brick
				Brick lastBrick = bricks.get(bricks.size() - 1);

				x = lastBrick.getxPos();
				y = lastBrick.getyPos() - scaledBrickHeight;
			}

			addBrick(imageId, x, y ,w);
		}
    }

    private void addBrick(int imageId, float x, float y, int w) {
    	Brick newBrick = new Brick(context, imageId, x, y, w);
    	bricks.add(newBrick);
    	currentBrick = newBrick;
    	
    }

    public void setFlag(boolean value) {
    	flag = value;
    }
    
    public boolean getFlag() {
    	return flag;
    }
    
	/**
	* The name of the bundle keys to save the stack
	*/
	private final static String LOCATIONS = "Stack.locations";
	private final static String WEIGHTS = "Stack.weights";
	private final static String LASTTIME = "Stack.lastTime";
	private final static String ANGLE = "Stack.angle";
	private final static String COEF= "Stack.coefficient";
	private final static String LASTSTABLEBRRICK= "Stack.lastStableBrick";
	private final static String SETBRICK="setbrick";
	private final static String BRICKCOLORS="colorids";


	/**
	* Save the brick stack to a bundle
	* @param bundle The bundle we save to
	*/
	public void saveInstanceState(Bundle bundle) {
		float [] locations = new float[bricks.size() * 2];
		int [] weights = new int[bricks.size()];
		int [] ids = new int[bricks.size()];
 
		for(int i=0; i<bricks.size(); i++) {
			Brick brick = bricks.get(i);
			locations[i] = brick.getxPos();
			weights[i] = brick.getWeight();
			ids[i] = brick.getID();
		}
 
		bundle.putIntArray(BRICKCOLORS,ids);
		bundle.putFloatArray(LOCATIONS, locations);
		bundle.putIntArray(WEIGHTS, weights);
		bundle.putFloat(ANGLE, angle);
		bundle.putLong(LASTTIME, lastTime);
		bundle.putInt(LASTSTABLEBRRICK, lastStableBrick);
		bundle.putBoolean(SETBRICK, flag);
	}

	/**
	* Read the brick stack from a bundle
	* @param bundle The bundle we save to
	*/
	public void loadInstanceState(Bundle bundle) {

		angle = bundle.getFloat(ANGLE);
		lastTime = -1;
		fallingCoefficient = bundle.getInt(COEF);
		lastStableBrick = bundle.getInt(LASTSTABLEBRRICK);
		flag = bundle.getBoolean(SETBRICK);

		float [] locations = bundle.getFloatArray(LOCATIONS);
		int [] weights = bundle.getIntArray(WEIGHTS);
		int [] ids = bundle.getIntArray(BRICKCOLORS);

		int numberOfBricks = weights.length;

		float scaledBrickHeight = (float)brickHeight*SCALE_IN_VIEW/brickWidth;
		float y = 1- scaledBrickHeight/2;

		for (int i=0; i < numberOfBricks; i++){	
			/*
			 * create images from saved id
			 */
			int imageId = ids[i];
			addBrick(imageId, locations[i], y, weights[i]);
			y -= scaledBrickHeight;
		}
		if (flag)
			currentBrick=null;

		updateLastStableBrick();
		

	}
	
	public void creatBricks() {
		
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
	* Handle a touch message. This is when we get an initial touch
	* @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
	* @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
	* @return true if the touch is handled
	*/
    private boolean onTouched(float x, float y) {
        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
     if(currentBrick != null)
     {
     lastRelX = x;
     }

        lastRelY = y;
        return true;
        //return false;
    }
    
    
	/**
	* Handle a touch event from the view.
	* @param view The view that is the source of the touch
	* @param event The motion event describing the touch
	* @return true if the touch is handled.
	*/
    public boolean onTouchEvent(View view, MotionEvent event) {
    	//
        // Convert an x,y location to a relative location in the stackview.
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
            if((currentBrick != null ))
            {
        		if(currentBrick.hit(relX, lastRelY - yOffset, stackSize, scaleFactor)) {
        			currentBrick.move(relX - lastRelX);
        			lastRelX = relX;
        		}
            }
            else
            {
            	yOffset += relY - lastRelY;
             	yOffset = (yOffset < 0)? 0 : yOffset;
             	lastRelY = relY;
            }
            view.invalidate();
            return true;
        }
        
        return false;
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

    public Brick getCurrentBrick() {
    	return currentBrick;
    }

    public void setCurrentBrick(Brick currentBrick) {
    	this.currentBrick = currentBrick;
    }

	/**
	* lastStableBrick
	* Used for determine which bricks to have falling animation
	*/
    private int lastStableBrick;

	/**
	* falling coefficient
	* -1 for left and +1 for right
	*/
	private int fallingCoefficient;
	private float rotationCenterX;
	private float rotationCenterY;

	/**
	* Check stability from the top of the stack
	* @return index of the last stable brick
	*/
	private int updateLastStableBrick(){
		int size = bricks.size();
		float topStackX = 0;
		int topStackWeight = 0;
		while (size > 1){
			// Get 1 more brick and recalculate new center of bricks on top
			Brick nextBrick = bricks.get(--size);
			topStackX = (topStackX*topStackWeight + nextBrick.getxPos()*nextBrick.getWeight())/(topStackWeight + nextBrick.getWeight());

			// Check if the new center is over brick below
			float brickBelowX = bricks.get(size - 1).getxPos();
			if ((topStackX < brickBelowX - SCALE_IN_VIEW/2 ) ||(topStackX > brickBelowX + SCALE_IN_VIEW/2)){
				if (topStackX < brickBelowX - SCALE_IN_VIEW/2 )
					fallingCoefficient = -1;
				else
					fallingCoefficient = 1;

				//Calcualte falling center
				rotationCenterX = brickBelowX + fallingCoefficient * SCALE_IN_VIEW/2;
				rotationCenterY = nextBrick.getyPos() + brickHeight*SCALE_IN_VIEW/brickWidth;

				return size;
			}
			else{
				//Update top bricks weight
				topStackWeight += nextBrick.getWeight();
			}	
		}
		return 0;
	}

	/**
	* Update the last stable brick and check stability
	* @return whether the stack is stable
	*/
	public boolean isStable(){
		lastStableBrick = updateLastStableBrick();
		return (lastStableBrick == 0);
	}

	public void Reset() {
		lastStableBrick = 0;
		angle = 0;
		lastTime = 0;
		rotationCenterX = 0;
		rotationCenterY = 0;
		yOffset = 0;
		if(currentBrick!=null)
			currentBrick=null;
		bricks.clear();	
	}
}