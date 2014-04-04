package heiderse.msu.edu.project1;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OpeningActivity extends Activity {

	private Service _service;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_opening);
		_service = new Service();
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
			//attempt to connect to server
			InputStream stream = _service.getUser();
			Log.i("TECH",stream.toString());
		}
		// TO DO:
		// Send the username and password to the server
		// Check if they exist
		// If they do exist, login and load the next screen
		// If they don't, show a Toast saying that their information was incorrect
	}

	
	
}
