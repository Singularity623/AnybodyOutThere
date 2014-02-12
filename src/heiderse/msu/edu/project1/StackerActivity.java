package heiderse.msu.edu.project1;

import java.util.ArrayList;
import java.util.Random;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
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
	
	public final static int NUMBER_OF_PLAYERS = 2;
	public final static int FIRST_PLAYER_COLOR = R.drawable.brick_red1;
	public final static int SECOND_PLAYER_COLOR = R.drawable.brick_green1;
	
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
		players.add(new Player(getIntent().getStringExtra("player1"), FIRST_PLAYER_COLOR));
		players.add(new Player(getIntent().getStringExtra("player2"), SECOND_PLAYER_COLOR));
		
		
		TextView player1 = (TextView) findViewById(R.id.RedPlayerScore);
		player1.setText(players.get(0).getName() +": "+ players.get(0).getScore());
		
		TextView player2 = (TextView) findViewById(R.id.GreenPlayerScore);
		player2.setText(players.get(1).getName() +": "+ players.get(1).getScore());
		
		if(bundle != null) {
			// We have saved state
			playFirst = bundle.getInt(PLAY_FIRST);
			
			stackView.loadInstanceState(bundle);
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
		}
		


	}

	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		//Save the player who play first
		bundle.putInt(PLAY_FIRST,  playFirst);
		
		// TO DO: Save players info (name??, score)

		
		//Save the stackView instance State
		stackView.saveInstanceState(bundle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stacker, menu);
		return true;
	}
	
	private int playerTurn(){
		return (stackView.getStack().getBrickStackSize() + playFirst) % NUMBER_OF_PLAYERS;
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
	
	// Place the brick
	// Allow physics to affect the brick
	// Set state to brick placed
	public void onEndTurn(View view) {
		// New brick has not appeared yet (need to press weight button)
		stackView.getStack().setCurrentBrick(null);
	}

}
