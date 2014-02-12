package heiderse.msu.edu.project1;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String PLAYER_1 = "player1";
	public final static String PLAYER_2 = "player2";
	
	private EditText redEditText;
	private EditText greenEditText;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	public void onHelp(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        
        // Parameterize the builder
        builder.setTitle(R.string.how_to);
        builder.setMessage(R.string.help_text);
        builder.setPositiveButton(android.R.string.ok, null);
     
        // Create the dialog box and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
	}
	
	// Start the game
	// Check to make sure that the names are filled out
	public void onStart(View view) {
		redEditText = (EditText)findViewById(R.id.player_red_name);
		greenEditText = (EditText)findViewById(R.id.player_green_name);
		
		if((redEditText.getText().toString().trim().equals("")) || (greenEditText.getText().toString().trim().equals(""))){
			// Instantiate a dialog box builder
			AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
			
			// Parameterize the builder
			builder.setTitle(R.string.alert);
            builder.setMessage(R.string.names_empty);
            builder.setPositiveButton(android.R.string.ok, null);
            
            // Create the dialog box and show it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
		}
		else{
			// TO DO:
			// 
			//Create the players
			
			// Load the stacker activity
			Intent intent = new Intent(this, StackerActivity.class);

			//Add the entered string to the intent to be used by stacker Activity
			intent.putExtra(PLAYER_1, redEditText.getText().toString());
			intent.putExtra(PLAYER_2, greenEditText.getText().toString());
			
			startActivity(intent);
		}
	}
}
