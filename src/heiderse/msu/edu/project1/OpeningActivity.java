package heiderse.msu.edu.project1;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OpeningActivity extends Activity {

	//private Service _service;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	public void onNewUser(View view)
	{
		// Load the new user activity
		Intent intent = new Intent(this, newUserActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void onLogin(View view)
	{
		// Get the username and password
		String username = ((EditText)findViewById(R.id.editUsernameLogin)).getText().toString();
		String password = ((EditText)findViewById(R.id.editPasswordLogin)).getText().toString();
		
		// Check to see if the username and password are filled out
		if(username.isEmpty() || password.isEmpty())
		{
			Toast.makeText(getApplicationContext(), R.string.username_password_empty, Toast.LENGTH_SHORT).show();
		}
		else
		{
			//final View _view = view;
			
	    	//new Thread(new Runnable() {

	          /*  @Override
	            public void run() {
	        		_service = new Service();
	    			/*if(!boolll) {
	    				Log.i("YES","yes");
	                    /*
	                     * If we fail to save, display a toast 
	                     */
	                    // Please fill this in...
	                    // Error condition!
	                    /*_view.post(new Runnable() {

	                        @Override
	                        public void run() {
	                            Toast.makeText(getApplicationContext(), R.string.login_fail, Toast.LENGTH_SHORT).show();
	                        }
	                    });
	                    
	                }
	    			else {
	                    _view.post(new Runnable() {

	                        @Override
	                        public void run() {
	                            Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
	                        }
	                    });
	    			}*/
	          //  }
	       // }).start();
			
			//attempt to connect to server


		}
		// TO DO:
		// Send the username and password to the server
		// Check if they exist
		// If they do exist, login and load the next screen
		// If they don't, show a Toast saying that their information was incorrect
	}

	
	
}
