package heiderse.msu.edu.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OpeningActivity extends Activity {

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		//setContentView(R.layout.);
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
	}
	
	public void onLogin(View view)
	{
		
	}

	
	
}
