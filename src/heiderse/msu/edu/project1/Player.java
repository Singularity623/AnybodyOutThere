package heiderse.msu.edu.project1;


public class Player {
	
	// Int to store the brick color (0 = Red, 1 = Green...something like that)
	// We can change this to an Enum later if we want to
	private int brickColor;

	// Player name
	private String name;
	
	// Player score
	private int score;

	// Player has to have a name and a "color"
	public Player(String playerName, int brickInt) {
		name = playerName;
		brickColor = brickInt;
		score = 0;
	}

	// Get the value of the the player's name
	public String getName() {
		return name;
	}

	// Get/Set the value of the player's score
	public int getScore() {
		return score;
	}
	
	public void setScore(int value) {
		score = value;
	}

	// Get the value of the player's brick color
	public int getBrickColor() {
		return brickColor;
	}
	
}
