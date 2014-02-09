package heiderse.msu.edu.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class StackView extends View {
	
	/**
	 * The actual stack
	 */
	private Stack stack;

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
	
	

}
