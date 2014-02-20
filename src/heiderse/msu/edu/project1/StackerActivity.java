package heiderse.msu.edu.project1;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class StackerActivity extends Activity {
	
	/**
	 * The stack view in this activity's view
	 */
	private StackView stackView;
	
	/**
	 * Players
	 */
	private ArrayList<Player> players;
	
	private TextView player1;
	private TextView player2;
	
	public final static int NUMBER_OF_PLAYERS = 2;
	public final static int FIRST_PLAYER_COLOR = R.drawable.brick_red1;
	public final static int SECOND_PLAYER_COLOR = R.drawable.brick_green1;
	public final static String PLAYER_1_SCORE = "player1score";
	public final static String PLAYER_2_SCORE = "player2score";
	
	
	/**
	 * Player who play first
	 */
	private int playFirst;
	
	/**
	 * The name of the bundle keys to save the stack
	 */
	public final static String PLAY_FIRST = "StackerActivity.playFirst";
	//private final static String WEIGHTS = "Stack.weights";
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_stacker);
		
		stackView = (StackView)this.findViewById(R.id.stackView);
		
		players = new ArrayList<Player>();
		
		if(bundle != null) {
			// We have saved state
			playFirst = bundle.getInt(PLAY_FIRST);
			
			stackView.loadInstanceState(bundle);
			
			// Create the players from the saved bundle
			players.add(new Player((String)bundle.get(MainActivity.PLAYER_1), FIRST_PLAYER_COLOR));
			players.add(new Player((String)bundle.get(MainActivity.PLAYER_2), SECOND_PLAYER_COLOR));
			
			// Set Scores of players
			players.get(0).setScore(bundle.getInt(PLAYER_1_SCORE));
			players.get(1).setScore(bundle.getInt(PLAYER_2_SCORE));
		}
		else {
			//First player is chosen randomly
			Random generator = new Random();
			playFirst = generator.nextInt(NUMBER_OF_PLAYERS);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        
	        // Parameterize the builder
	        builder.setTitle(R.string.begin_game);
	        builder.setMessage(R.string.begin_game_text);
	        builder.setPositiveButton(android.R.string.ok, null);
	     
	        // Create the dialog box and show it
	        AlertDialog alertDialog = builder.create();
	        alertDialog.show();
	        
	        //New game start new players name from intent of activity main
			players.add(new Player(getIntent().getStringExtra(MainActivity.PLAYER_1), FIRST_PLAYER_COLOR));
			players.add(new Player(getIntent().getStringExtra(MainActivity.PLAYER_2), SECOND_PLAYER_COLOR));
			
			//PLACEHOLDER test for score saving
			players.get(0).setScore(0);
			
			//remove extra from intent
			getIntent().removeExtra(MainActivity.PLAYER_1);
			getIntent().removeExtra(MainActivity.PLAYER_2);
		}
		
		// Set the players
		player1 = (TextView) findViewById(R.id.RedPlayerScore);
		setUpPlayerTextView(player1,0);

		
		player2 = (TextView) findViewById(R.id.GreenPlayerScore);
		setUpPlayerTextView(player2,1);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		//Save the player who play first
		bundle.putInt(PLAY_FIRST,  playFirst);
		
		// Save players info (name, score)
		bundle.putString(MainActivity.PLAYER_1, players.get(0).getName());
		bundle.putInt(PLAYER_1_SCORE, players.get(0).getScore());
		bundle.putString(MainActivity.PLAYER_2, players.get(1).getName());
		bundle.putInt(PLAYER_2_SCORE, players.get(1).getScore());
		
		//Save the stackView instance State
		stackView.saveInstanceState(bundle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stacker, menu);
		return true;
	}
	
	private int playerTurn() {
		return (stackView.getStack().getBrickStackSize() + playFirst) % NUMBER_OF_PLAYERS;
	}
	
	// Set a player text view with correct font and players(index) values
	public void setUpPlayerTextView(TextView tview, int index) {
		tview.setTypeface(MainActivity.broken);
		tview.setText(players.get(index).getName() +": " + players.get(index).getScore());
	}
	
	public void addBrick(int weight)
	{
		// TO DO: Check if the last player done with placing brick
		
		// Add brick
		int brickColor = players.get(playerTurn()).getBrickColor();
		stackView.addBrick( brickColor, weight);
	}
	
	// Set the current brick's weight to 1kg
	// Set state to placing brick
	public void onOneKg(View view) {
		int weight = 1;
		
		if(stackView.getStack().getCurrentBrick() == null)
		{
			addBrick(weight);
		}
		else
		{
			stackView.getStack().getCurrentBrick().setWeight(weight);
		}

	}
	
	// Set the current brick's weight to 2kg
	// Set state to placing brick
	public void onTwoKg(View view) {
		int weight = 2;
		
		if(stackView.getStack().getCurrentBrick() == null)
		{
			addBrick(weight);
		}
		else
		{
			stackView.getStack().getCurrentBrick().setWeight(weight);
		}
	}
	
	// Set the current brick's weight to 5kg
	// Set state to placing brick
	public void onFiveKg(View view) {
		int weight = 5;
		
		if(stackView.getStack().getCurrentBrick() == null)
		{
			addBrick(weight);
		}
		else
		{
			stackView.getStack().getCurrentBrick().setWeight(weight);
		}
	}
	
	// Set the current brick's weight to 10kg
	// Set state to placing brick
	public void onTenKg(View view) {
		int weight = 10;
		
		if(stackView.getStack().getCurrentBrick() == null)
		{
			addBrick(weight);
		}
		else
		{
			stackView.getStack().getCurrentBrick().setWeight(weight);
		}
	}
	
	public TextView getWinnerTextView(int index)
	{
		if (index % 2 == 0) {
			return player1;
		}
		else {
			return player2;
		}
	}
	
	// Place the brick
	// Allow physics to affect the brick
	// Set state to brick placed
	public void onEndTurn(View view) {
		// New brick has not appeared yet (need to press weight button)
		stackView.getStack().setCurrentBrick(null);
		if(stackView.getStack().isStable()) {
			
			Log.i("stable", "stack is stable");
		}
		else {
			players.get(playerTurn()).setScore(players.get(playerTurn()).getScore()+1);
			setUpPlayerTextView(getWinnerTextView(playerTurn()),playerTurn());
			stackView.getStack().Reset();
			stackView.invalidate();
			Log.i("unstable","stack is unstable");
		}
	}

}
