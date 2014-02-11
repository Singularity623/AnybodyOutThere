package heiderse.msu.edu.project1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class StackerActivity extends Activity {

	/**
	 * The stack view in this activity's view
	 */
	StackView stackView;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_stacker);
		
		stackView = (StackView)this.findViewById(R.id.stackView);
		
		if(bundle != null) {
			// We have saved state
			stackView.loadInstanceState(bundle);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		stackView.saveInstanceState(bundle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stacker, menu);
		return true;
	}
	
	// Set the current brick's weight to 1kg
	// Set state to placing brick
	public void onOneKg(View view) {

		/**
		 * TEST ONLY!!
		 */ 
		stackView.addBrick(R.drawable.brick_red1, 1);

	}
	
	// Set the current brick's weight to 2kg
	// Set state to placing brick
	public void onTwoKg(View view) {
		
	}
	
	// Set the current brick's weight to 5kg
	// Set state to placing brick
	public void onFiveKg(View view) {
		
	}
	
	// Set the current brick's weight to 10kg
	// Set state to placing brick
	public void onTenKg(View view) {
		
	}
	
	// Place the brick
	// Allow physics to affect the brick
	// Set state to brick placed
	public void onEndTurn(View view) {
			
		}

}
