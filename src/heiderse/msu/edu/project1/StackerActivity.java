package heiderse.msu.edu.project1;

import java.util.ArrayList;
import java.util.Random;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

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
		
		//
		//TO DO: Get player name from Intent ( or load from bundle??)
		//
		String player1Name = "Jason";
		String player2Name = "Bob";
		
		players = new ArrayList<Player>();
		players.add(new Player(player1Name, FIRST_PLAYER_COLOR));
		players.add(new Player(player2Name, SECOND_PLAYER_COLOR));
		
		if(bundle != null) {
			// We have saved state
			playFirst = bundle.getInt(PLAY_FIRST);
			
			stackView.loadInstanceState(bundle);
		}
		else{
			//First player is chosen randomly
			Random generator = new Random();
			playFirst = generator.nextInt(NUMBER_OF_PLAYERS);
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

		/**
		 * TEST ONLY!!
		 */ 
		int weight = 1;
		addBrick(weight);

	}
	
	// Set the current brick's weight to 2kg
	// Set state to placing brick
	public void onTwoKg(View view) {
		
	}
	
	// Set the current brick's weight to 5kg
	// Set state to placing brick
	public void onFiveKg(View view) {
		
	}
	
	// Set the current brick's weight to 10kg
	// Set state to placing brick
	public void onTenKg(View view) {
		
	}
	
	// Place the brick
	// Allow physics to affect the brick
	// Set state to brick placed
	public void onEndTurn(View view) {
			
	}

}
