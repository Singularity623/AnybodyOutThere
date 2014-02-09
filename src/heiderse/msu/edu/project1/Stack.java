package heiderse.msu.edu.project1;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;

public class Stack {
	/*
	 * Context - where to find things like resources
	 */
	private Context context;
	
	public ArrayList<Brick> bricks; 

	public Stack(Context ct) {
		context = ct;
		
		// Create an empty brick array
		bricks = new ArrayList<Brick>();
	}
	
	public void draw(Canvas canvas) {
		
	}
	
	public void addBrick(int imageId, float x, float y, int w){
		bricks.add(new Brick(context, imageId, x, y ,w));
	}
	
	/**
	 * The name of the bundle keys to save the puzzle
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
			locations[i*2+1] = brick.getxPos();
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
		
		
		for (int i=0; i < weights.length; i++){	
			/*
			 *  TO DO: Decide the image id for the brick
			 *  Based on the player who player first (which is in Stacker Activity) 
			 *  and current index
			 */
			int imageId = 0;
			addBrick(imageId, locations[2*i], locations[2*i+1], weights[i]);
		}
	}

}
