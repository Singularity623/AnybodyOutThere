package heiderse.msu.edu.project1;

import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String PLAYER_1 = "player1";
	public final static String PLAYER_2 = "player2";
	
	public static Typeface broken;
	
	private EditText redEditText;
	private EditText greenEditText;
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	
	private Service _service;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		setContentView(R.layout.activity_opening);
		
		//setContentView(R.layout.activity_main);
		
		// Set the font
		broken = Typeface.createFromAsset(getAssets(),"fonts/Broken.ttf");
		
		// Assign editTexts to variables
		usernameEditText = (EditText)findViewById(R.id.editUsernameLogin);
		passwordEditText = (EditText)findViewById(R.id.editPasswordLogin);
		
		// Set font to edit views
		usernameEditText.setTypeface(broken);
		passwordEditText.setTypeface(broken);
		
		// Set font to text views
		((TextView)findViewById(R.id.usernameText)).setTypeface(broken);
		((TextView)findViewById(R.id.passwordText)).setTypeface(broken);
		
		// Assign textview's to variables
		/*redEditText = (EditText)findViewById(R.id.player_red_name);
		greenEditText = (EditText)findViewById(R.id.player_green_name);
		
		// Set font to text views
	    redEditText.setTypeface(broken);
	    greenEditText.setTypeface(broken);
	    
	    if (bundle !=null)
	    {
	    	redEditText.setText(getIntent().getStringExtra(PLAYER_1));
	    	greenEditText.setText(getIntent().getStringExtra(PLAYER_2));
	    }*/
	}

	
	@Override
	public void onSaveInstanceState(Bundle bundle)
	{
		super.onSaveInstanceState(bundle);
		
	}
	
	public void onNewUser(View view)
	{
		// Load the new user activity
		Intent intent = new Intent(this, newUserActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void onNext(View view)
	{
		Intent intent = new Intent(this, OpeningActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void onLogin(View view)
	{
		// Get the username and password
		final String username = usernameEditText.getText().toString();
		final String password = passwordEditText.getText().toString();
		
		
		
		// Check to see if the username and password are filled out
		if(username.isEmpty() || password.isEmpty())
		{
			Toast.makeText(getApplicationContext(), R.string.username_password_empty, Toast.LENGTH_SHORT).show();
		}
		else
		{
			final View _view = view;
			
	    	new Thread(new Runnable() {
	    		String stream;
	            @Override
	            public void run() {
	        		_service = new Service();
	        		_service.set_name(username);
	        		_service.set_password(password);
	        		stream = _service.getUser(0);
	    			if(stream == null) {
	                    /*
	                     * If we fail to save, display a toast 
	                     */
	                    // Please fill this in...
	                    // Error condition!
	                    _view.post(new Runnable() {

	                        @Override
	                        public void run() {
	                            Toast.makeText(getApplicationContext(), R.string.login_fail, Toast.LENGTH_SHORT).show();
	                        }
	                    }); 
	                }
	    			else {
	    				//load activity blank
	    				//Intent intent = new Intent(this, )
	                    _view.post(new Runnable() {
	                    	
	                        @Override
	                        public void run() {
	                            Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
	                        }
	                    });
	                    onNext(_view);
	    				
	    			}
	            }
	        }).start();
	    
		// TO DO:
		// Send the username and password to the server
		// Check if they exist
		// If they do exist, login and load the next screen
		// If they don't, show a Toast saying that their information was incorrect
		}
	}
	
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	// Bring up the help Dialog Box
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
			Intent intent = new Intent(this, OpeningActivity.class);

			//Add the entered string to the intent to be used by stacker Activity
			intent.putExtra(PLAYER_1, redEditText.getText().toString());
			intent.putExtra(PLAYER_2, greenEditText.getText().toString());
			
			startActivity(intent);
		}
	}*/
}
