package heiderse.msu.edu.project1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class StackerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stacker);
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
