package heiderse.msu.edu.project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class newUserActivity extends Activity {

	public static Typeface broken;
	
	private Service _service;
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText passwordVerifyEditText;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_new_user);
		

		// Set the font
		broken = Typeface.createFromAsset(getAssets(),"fonts/Broken.ttf");
		
		// Assign editTexts to variables
		usernameEditText = (EditText)findViewById(R.id.editUsernameNew);
		passwordEditText = (EditText)findViewById(R.id.editPasswordNew);
		passwordVerifyEditText = (EditText)findViewById(R.id.editVerifyPassword1);
				
		// Set font to edit views
		usernameEditText.setTypeface(broken);
		passwordEditText.setTypeface(broken);
		passwordVerifyEditText.setTypeface(broken);
		
		// Set font to text views
		((TextView)findViewById(R.id.usernametext)).setTypeface(broken);
		((TextView)findViewById(R.id.passwordtext)).setTypeface(broken);
		((TextView)findViewById(R.id.verifyPasswordText)).setTypeface(broken);
	}
	
	public void onCreateUser(View view)
	{
		// Get the username, password, and verified password
		final String username = usernameEditText.getText().toString();
		final String password = passwordEditText.getText().toString();
		String verifiedPassword = passwordVerifyEditText.getText().toString();
		
		if(username.isEmpty() || password.isEmpty() || verifiedPassword.isEmpty())
		{
			// Check to see if the username, password, and verified password are filled out
			Toast.makeText(getApplicationContext(), R.string.username_password_verify_empty, Toast.LENGTH_SHORT).show();
		}
		else if(!password.equals(verifiedPassword))
		{
			// Check to see if the password and verified password match
			Toast.makeText(getApplicationContext(), R.string.password_verify_mismatch, Toast.LENGTH_SHORT).show();
		}
		else
		{
			// TO DO:
			// Send the username and password to the server
			// Make sure that username does not already exist
			// If it does, make a toast saying that the username is taken
			// If not, create an entry in the table with this username and password
			
	    	new Thread(new Runnable() {

	            @Override
	            public void run() {
	        		_service = new Service();
	    			_service.set_name(username);
	    			_service.set_password(password);
	    			String val = _service.getUser(1);
	    			//get feedback from server
	    			if(val!= null)
	    				Log.i("success", "writing to db");
	    			else
	    				Log.i("failure", "writing to db");
	            }
	        }).start();
				
			
			// Return to the opening screen
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		
		
	}
	
	
	
}
