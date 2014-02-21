package heiderse.msu.edu.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class StackView extends View {
	
	/**
	 * The actual stack
	 */
	private Stack stack;
	
	public Stack getStack(){
		return stack;
	}

	public StackView(Context context) {
		super(context);
		init(context);
	}

	public StackView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public StackView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		stack = new Stack(context);		
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		stack.draw(canvas);
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return stack.onTouchEvent(this, event);
	}
	
	/**
	 * Save the puzzle to a bundle
	 * @param bundle The bundle we save to
	 */
	public void saveInstanceState(Bundle bundle) {
		stack.saveInstanceState(bundle);
	}
	
	/**
	 * Load the puzzle from a bundle
	 * @param bundle The bundle we save to
	 */
	public void loadInstanceState(Bundle bundle) {
		stack.loadInstanceState(bundle);
	}
	
	/**
	 * Add brick
	 * Call function add brick in the stack
	 * & invalidate the view
	 * @param imageId
	 * @param weight
	 */
	public void addBrick(int imageId, int weight){
		stack.addBrick(imageId, weight);
		this.invalidate();
	}
	
	

}
