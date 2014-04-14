package heiderse.msu.edu.project1;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
	
	private int userPlayer; // Index of the user player
	private String username;
	private String password;
	private String gameId;
	
	private int turn;
	private int round;
	
	public final static int NUMBER_OF_PLAYERS = 2;
	public final static int FIRST_PLAYER_COLOR = R.drawable.brick_red1;
	public final static int SECOND_PLAYER_COLOR = R.drawable.brick_green1;
	public final static String PLAYER_1_SCORE = "player1score";
	public final static String PLAYER_2_SCORE = "player2score";
	public final static String TURN = "turncounter";
	public final static String ROUND = "roundcounter";
	
	private ImageButton onekg;
	private ImageButton twokg;
	private ImageButton fivekg;
	private ImageButton tenkg;
	private ImageButton set;

	
	/**
	 * Player who play first
	 */
	private int playFirst;
	
	/**
	 * The name of the bundle keys to save the stack
	 */
	public final static String PLAY_FIRST = "StackerActivity.playFirst";
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_stacker);

		stackView = (StackView)this.findViewById(R.id.stackView);
		
		players = new ArrayList<Player>();
		
		onekg = (ImageButton)findViewById(R.id.OneKgButton);
		twokg = (ImageButton)findViewById(R.id.TwoKgButton);
		fivekg = (ImageButton)findViewById(R.id.FiveKgButton);
		tenkg = (ImageButton)findViewById(R.id.TenKgButton);
		set = (ImageButton)findViewById(R.id.EndTurnButton);
		
		SharedPreferences shared = getSharedPreferences("shared", MODE_PRIVATE);		
		if(shared.contains("username") && shared.contains("password")){
			username = shared.getString("username", "");
			password = shared.getString("password", "");
		}
		
		if(bundle != null) {
			// We have saved state
			playFirst = bundle.getInt(PLAY_FIRST);
			
			stackView.loadInstanceState(bundle);
			
			userPlayer = bundle.getInt(MainActivity.USERPLAYER);
			gameId = bundle.getString(MainActivity.GAMEID);
			
			
			// Create the players from the saved bundle
			players.add(new Player((String)bundle.get(MainActivity.PLAYER_1), FIRST_PLAYER_COLOR));
			players.add(new Player((String)bundle.get(MainActivity.PLAYER_2), SECOND_PLAYER_COLOR));
			
			// Set Scores of players
			players.get(0).setScore(bundle.getInt(PLAYER_1_SCORE));
			players.get(1).setScore(bundle.getInt(PLAYER_2_SCORE));
			turn = bundle.getInt(TURN);
			round = bundle.getInt(ROUND);
			checkScore();
		}
		else {
			determineFirst();
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        
	        // Parameterize the builder
	        builder.setTitle(R.string.begin_game);
	        builder.setMessage(R.string.begin_game_text);
	        builder.setPositiveButton(android.R.string.ok, null);
	     
	        // Create the dialog box and show it
	        AlertDialog alertDialog = builder.create();
	        alertDialog.show();
	        
	        userPlayer = getIntent().getIntExtra(MainActivity.USERPLAYER, 0);
	        gameId = getIntent().getStringExtra(MainActivity.GAMEID);
	        
	        int player1Name=(userPlayer==0)?R.string.you:R.string.your_opponent;
	        int player2Name=(userPlayer!=0)?R.string.you:R.string.your_opponent;
	        
	        
	        //New game start new players name from intent of activity main
			players.add(new Player(getString(player1Name), FIRST_PLAYER_COLOR));
			players.add(new Player(getString(player2Name), SECOND_PLAYER_COLOR));
			
			//PLACEHOLDER test for score saving
			players.get(0).setScore(0);
			
			//remove extra from intent
			getIntent().removeExtra(MainActivity.PLAYER_1);
			getIntent().removeExtra(MainActivity.PLAYER_2);
			turn = 0;
			round = 1;
		}
		
		setButtonImages();
		switchButtonImages();
		
		// Set the players
		player1 = (TextView) findViewById(R.id.RedPlayerScore);
		setUpPlayerTextView(player1,0);

		
		player2 = (TextView) findViewById(R.id.GreenPlayerScore);
		setUpPlayerTextView(player2,1);
		
		startTurn();

	}

	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		//Save the player who play first
		bundle.putInt(PLAY_FIRST,  playFirst);
		bundle.putInt(TURN,turn);
		bundle.putInt(ROUND,round);
		
		// Save players info (name, score)
		bundle.putString(MainActivity.PLAYER_1, players.get(0).getName());
		bundle.putInt(PLAYER_1_SCORE, players.get(0).getScore());
		bundle.putString(MainActivity.PLAYER_2, players.get(1).getName());
		bundle.putInt(PLAYER_2_SCORE, players.get(1).getScore());
		
		bundle.putInt(MainActivity.USERPLAYER, userPlayer);
		bundle.putString(MainActivity.GAMEID, gameId);
	
		//Save the stackView instance State
		stackView.saveInstanceState(bundle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stacker, menu);
		return true;
	}
	
	public void determineFirst()
	{
		//First player is chosen randomly
		playFirst = 0;
	}

	
	private int playerTurn() {
		return (turn-playFirst + NUMBER_OF_PLAYERS)%NUMBER_OF_PLAYERS;
	}
	
	// Set a player text view with correct font and players(index) values
	public void setUpPlayerTextView(TextView tview, int index) {
		tview.setTypeface(MainActivity.broken);
		tview.setText(players.get(index).getName() +": " + players.get(index).getScore());
	}
	
	
	// Set the current brick's weight to 1kg
	// Set state to placing brick
	public void onOneKg(View view) {
		int weight = 1;
		onKg(weight);		
	}
	
	// Set the current brick's weight to 2kg
	// Set state to placing brick
	public void onTwoKg(View view) {
		int weight = 2;
		onKg(weight);		
	}
	
	// Set the current brick's weight to 5kg
	// Set state to placing brick
	public void onFiveKg(View view) {
		int weight = 5;		
		onKg(weight);		
	}
	
	// Set the current brick's weight to 10kg
	// Set state to placing brick
	public void onTenKg(View view) {
		int weight = 10;		
		onKg(weight);	
	}
	

	public void addBrick(int weight) {
		// Add brick
		int brickColor = players.get(playerTurn()).getBrickColor();
		stackView.addBrick( brickColor, weight);
	}
	
	public void addBrick(float x, int weight) {
		// Add brick
		int brickColor = players.get(playerTurn()).getBrickColor();
		stackView.addBrick( brickColor, x, weight);
	}
	
	private void onKg(int weight){
		if(stackView.getStack().getCurrentBrick() == null)
		{
			addBrick(weight);
		}
		else
		{
			stackView.getStack().getCurrentBrick().setWeight(weight);
		}
		stackView.getStack().setFlag(false);
	}
	
	/**
	 * Replace the button images with the current player turn color buttons
	 * @param playerTurn
	 */
	public void switchButtonImages()
	{
		if (playerTurn() % NUMBER_OF_PLAYERS != userPlayer){
			onekg.setEnabled(false);
			twokg.setEnabled(false);
			fivekg.setEnabled(false);
			tenkg.setEnabled(false);
			set.setEnabled(false);
			
			onekg.setAlpha(0.5f);
			twokg.setAlpha(0.5f);
			fivekg.setAlpha(0.5f);
			tenkg.setAlpha(0.5f);
			set.setAlpha(0.5f);
		}
		else{
			onekg.setEnabled(true);
			twokg.setEnabled(true);
			fivekg.setEnabled(true);
			tenkg.setEnabled(true);
			set.setEnabled(true);
			
			onekg.setAlpha(1f);
			twokg.setAlpha(1f);
			fivekg.setAlpha(1f);
			tenkg.setAlpha(1f);
			set.setAlpha(1f);
		}
	}
	
	public void setButtonImages(){
		if (userPlayer % NUMBER_OF_PLAYERS == 0) {
			onekg.setImageDrawable(getResources().getDrawable(R.drawable.onekgred));
			twokg.setImageDrawable(getResources().getDrawable(R.drawable.twokgred));
			fivekg.setImageDrawable(getResources().getDrawable(R.drawable.fivekgred));
			tenkg.setImageDrawable(getResources().getDrawable(R.drawable.tenkgred));
			set.setImageDrawable(getResources().getDrawable(R.drawable.setred));
		}
		else {
			onekg.setImageDrawable(getResources().getDrawable(R.drawable.onekggreen));
			twokg.setImageDrawable(getResources().getDrawable(R.drawable.twokggreen));
			fivekg.setImageDrawable(getResources().getDrawable(R.drawable.fivekggreen));
			tenkg.setImageDrawable(getResources().getDrawable(R.drawable.tenkggreen));
			set.setImageDrawable(getResources().getDrawable(R.drawable.setgreen));
		}
	}
	
	
	/**
	 * Get the player text edit to change score
	 * @param index
	 * @return
	 */
	public TextView getWinnerTextView(int index)
	{
		if (index % NUMBER_OF_PLAYERS == 0) {
			return player1;
		}
		else {
			return player2;
		}
	}
	
	// Place the brick
	// Allow physics to affect the brick
	// Set state to brick placed
	public void onEndTurn(final View view) {
		// New brick has not appeared yet (need to press weight button)
		if(stackView.getStack().getCurrentBrick() == null)	{
			//no brick fool
		}
		else {
			final Brick currentBrick = stackView.getStack().getCurrentBrick();
			
			new Thread(new Runnable() {

                @Override
                public void run() {
                	Service service = new Service();
	                service.set_name(username);
	                service.set_password(password);
	                
	                boolean f = false;
	                String msg="";
	                InputStream stream = service.addBrick(gameId, round, currentBrick);
<<<<<<< HEAD
	                
	                try {
                        XmlPullParser xml = Xml.newPullParser();
                        xml.setInput(stream, Service.UTF8);
                        
                        xml.nextTag();      // Advance to first tag
                        xml.require(XmlPullParser.START_TAG, null, "stacker");
                        
                        String status = xml.getAttributeValue(null, "status");
                        if(status.equals("no")) {
                        	f = true;
                        	msg = xml.getAttributeValue(null, "msg");
                        }
                        
                        // We are done
                    } catch(XmlPullParserException ex) {
                        //return false;
                    } catch(IOException ex) {
                        //return false;
                    }
	                
=======
	                
	                try {
                        XmlPullParser xml = Xml.newPullParser();
                        xml.setInput(stream, Service.UTF8);
                        
                        xml.nextTag();      // Advance to first tag
                        xml.require(XmlPullParser.START_TAG, null, "stacker");
                        
                        String status = xml.getAttributeValue(null, "status");
                        if(status.equals("no")) {
                        	f = true;
                        	msg = xml.getAttributeValue(null, "msg");
                        }
                        
                        // We are done
                    } catch(XmlPullParserException ex) {
                        //return false;
                    } catch(IOException ex) {
                        //return false;
                    }
	                
>>>>>>> 1a87f09c1326cf09217af6a15f5162333eeccfc7
	                final boolean fail = f;
	                final String msg_final = msg;
	                
	                view.post(new Runnable() {

	                    @Override
	                    public void run() {	                        
	                        if(fail) {
	                        	if (msg_final.equalsIgnoreCase("Insert fail"))
	                        		Toast.makeText(view.getContext(), msg_final, Toast.LENGTH_SHORT).show();
	                        	else
	                        		ExitGameAlert(msg_final);
	                        } else {
	                            // Success!
	                        	endTurn(view);	                        	
	                        }	                        
	                    }
	                
	                });
                    
                }
                
            }).start();
		}
	}
	
	private void endTurn(View view){
		turn+=1;
		stackView.getStack().setCurrentBrick(null);
		stackView.getStack().setFlag(true);
		if(stackView.getStack().isStable()) {
			switchButtonImages();
		}
		else {
			players.get(playerTurn()).setScore(players.get(playerTurn()).getScore()+1);
			endRound();
			stackView.invalidate();


			view.invalidate();
		}
		startTurn();
	}
	
	// used for rotate view
	public void checkScore()
	{	
		if(players.get(playerTurn()).getScore() >= 3)
		{
			setupMessage(playerTurn());
		}
	}
	
	public void setupMessage(int index)	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle(R.string.game_over);
		String text = String.format(getResources().getString(R.string.winner), players.get(playerTurn()).getName());
		builder.setMessage(text);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
					onOk();
				}
				});
		
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	public void endRound(){
		setUpPlayerTextView(getWinnerTextView(playerTurn()),playerTurn());
		
		checkScore();
		
		playFirst = ((playerTurn()+1)%NUMBER_OF_PLAYERS);
		turn = 0;
		round+=1;
	}
	
	public void startTurn(){
		if (playerTurn() != userPlayer){
			new Thread(new Runnable() {

	            @Override
	            public void run() {
	                // Create a cloud object and get the XML
	                Service service = new Service();
	                service.set_name(username);
	                service.set_password(password);
	                
	                boolean isPlayed = false;
	                int weight=0;
            		float x=0f;
            		
            		String msg = "";
	                
	                while (!isPlayed){
	                	// Send request every 2 seconds
	                	try {
	                		Thread.sleep(2000);
	                	}
	                	catch (InterruptedException ex){
	                	}
	                	
	                	InputStream stream = service.listBrick(gameId, round, turn);
	                	try {
	                        XmlPullParser xml = Xml.newPullParser();
	                        xml.setInput(stream, Service.UTF8);
	                        
	                        xml.nextTag();      // Advance to first tag
	                        xml.require(XmlPullParser.START_TAG, null, "stacker");
	                        
	                        String status = xml.getAttributeValue(null, "status");
	                        if(status.equals("no")) {
	                        	msg = xml.getAttributeValue(null, "msg");
	                        	break;
	                        }
	                        else{
	                        	int count = Integer.parseInt(xml.getAttributeValue(null, "count"));
	                        	if (count < 1){
	                        		
	                        	}
	                        	else{
	                        		isPlayed = true;
	                        		
	                        		while(xml.nextTag() == XmlPullParser.START_TAG) {
	                                    if(xml.getName().equals("brick")) {
	                                        weight = Integer.parseInt(xml.getAttributeValue(null, "weight"));
	                                        x = Float.parseFloat(xml.getAttributeValue(null, "x"));
	                                    }
	                                }
	                        	}
	                        		
	                        }
	                        
	                        // We are done
	                    } catch(XmlPullParserException ex) {
	                        //return false;
	                    } catch(IOException ex) {
	                        //return false;
	                    }
	                }
	                
	                if (isPlayed){
	                
		                final float x_final = x;
	            		final int weight_final = weight;
	            		
	            		stackView.post(new Runnable() {
	
		                    @Override
		                    public void run() {
		                    	if (weight_final > 0){
		                    		addBrick(x_final, weight_final);
		                    		endTurn(stackView);
		                        }
		                    }
		                
		                });
	                }
	                else{
	                	
	                	final String msg_final = msg;
	                	
	                	stackView.post(new Runnable() {
	                		
		                    @Override
		                    public void run() {
		                    	ExitGameAlert(msg_final);
		                    }
		                
		                });
	                }
	                
	            }
	        }).start();
		}
	}
	
	public void ExitGameAlert(String msg)	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle(R.string.game_over);
		builder.setMessage(msg + ". Exit game!");
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
					onOk();
				}
				});
		
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	public void onOk()
	{
        Intent intent = new Intent(this, MainActivity.class);     
        this.startActivity(intent);
	}

}
