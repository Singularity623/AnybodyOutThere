package heiderse.msu.edu.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;

public class WaitingActivity extends FragmentActivity {
	
	String gameId="";

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_waiting);
		
		gameId = getIntent().getStringExtra(MainActivity.GAMEID);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.waiting, menu);
	
		return true;
	}



	@Override
	protected void onStart() {
		super.onStart();
		
		WaitingDlg dlg = new WaitingDlg();
        dlg.setGameId(gameId);
        dlg.show(getSupportFragmentManager(), "wait");
	}
	
	public void joinGame(){
		Intent intent = new Intent(this, StackerActivity.class);
		intent.putExtra(MainActivity.USERPLAYER, 0);
		intent.putExtra(MainActivity.GAMEID, gameId);
		// Put gameId, username, password??
		startActivity(intent);
		finish();
	}
	

}
