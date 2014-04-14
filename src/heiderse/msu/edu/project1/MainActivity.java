package heiderse.msu.edu.project1;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String PLAYER_1 = "player1";
	public final static String PLAYER_2 = "player2";
	
	public final static String USERNAME = "Player.username";
	public final static String PASSWORD = "Player.password";
	public final static String GAMEID = "Game.id";
	public final static String USERPLAYER = "Game.userplayer";
	
	
	public static Typeface broken;
	
	
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
		((TextView)findViewById(R.id.usernameTextLogin)).setTypeface(broken);
		((TextView)findViewById(R.id.passwordText)).setTypeface(broken);
		
		Log.i("fds ds", "sharedPrefs");
		SharedPreferences shared = getSharedPreferences("shared", MODE_PRIVATE);
		
		if(shared.contains("remember") && shared.getBoolean("remember", false)){
	        if(shared.contains("username") && shared.contains("password")){
	        	usernameEditText.setText(shared.getString("username", ""));
	        	passwordEditText.setText(shared.getString("password", ""));
	        }
	    }
		
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
	
	public void onNext(View view, int player, String gameId)
	{
		if (player==0)
			return;
		
		
		CheckBox rememberCheckbox = (CheckBox)findViewById(R.id.rememberCheckbox);
		
		SharedPreferences shared = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("username", usernameEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        editor.putBoolean("remember", rememberCheckbox.isChecked());
        editor.commit();
		
		Intent intent;
		if (player==1)
			intent = new Intent(this, WaitingActivity.class);
		else{
			intent = new Intent(this, StackerActivity.class);
			intent.putExtra(USERPLAYER, 1);
		}
			
		intent.putExtra(USERNAME, usernameEditText.getText().toString());
		intent.putExtra(PASSWORD, passwordEditText.getText().toString());
		intent.putExtra(GAMEID, gameId);
		
		startActivity(intent);
		//finish();
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
	    		//String stream;
	            @Override
	            public void run() {
	        		_service = new Service();
	        		_service.set_name(username);
	        		_service.set_password(password);
	        		InputStream stream = _service.getUser();
	        		
	    			if(stream == null) {
	                    /*
	                     * If we fail to save, display a toast 
	                     */
	                    // Please fill this in...
	                    // Error condition!
	                    _view.post(new Runnable() {

	                        @Override
	                        public void run() {
	                            Toast.makeText(getApplicationContext(), R.string.connect_fail, Toast.LENGTH_SHORT).show();
	                        }
	                    }); 
	                }
	    			else {
	    				int player = 0; /* Player order: 0 - login fail, 1: 1st player, 2: 2nd player */
	    				String gameId = "";
	    				/**
	    		         * Create an XML parser for the result
	    		         */
	    		        try {
	    		            XmlPullParser xml = Xml.newPullParser();
	    		            xml.setInput(stream, "UTF-8");
	    		            
	    		            xml.nextTag();      // Advance to first tag
	    		            xml.require(XmlPullParser.START_TAG, null, "stacker");
	    		            
	    		            String status = xml.getAttributeValue(null, "status");
	    		            if(status.equals("yes")) {
	    		            	player = Integer.parseInt(xml.getAttributeValue(null, "player"));
	    		            	gameId = xml.getAttributeValue(null, "gameId");
	    		            }
	    		            
	    		            // We are done
	    		        } catch(XmlPullParserException ex) {
    		            	
	    		        } catch(IOException ex) {
    		            	
	    		        } finally {
	    		            try {
	    		                stream.close();
	    		            } catch(IOException ex) {
	    		                
	    		            }
	    		        }

	    		        final int message = (player==0)? R.string.login_fail : R.string.login_success;	    				
	    				
	    				//load activity blank
	    				//Intent intent = new Intent(this, )
	                    _view.post(new Runnable() {
	                    	
	                        @Override
	                        public void run() {
	                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	                        }
	                    });
	                    
	                    onNext(_view, player, gameId);
	    				
	    			}
	            }
	        }).start();
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
