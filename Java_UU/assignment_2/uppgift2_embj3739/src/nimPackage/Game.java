/*
 * Emil Björklund embj3739
 * Uppgift 2 - Nim 
 */

package nimPackage;
import java.util.Scanner;

public class Game {
	Game game;
	// Initialize a scanner for enabling console input.
	Scanner input = new Scanner(System.in);
	
	// Initialize variables for game.
	private int sticks;
	private int[] playerID = {1,2};
	private boolean turn = false;
	private boolean gameOver = false;
	
	// Initialize Players.
	PlayerHuman player1 = new PlayerHuman(playerID[0]);
	PlayerComputer player2 = new PlayerComputer((playerID[1]));
	
	// Constructor for class Game
	public Game (){ 
		initGame();
	}
	
	// Initialize Game and asks player to insert number of sticks.
	public void initGame() {
		System.out.println("Welcome Nim");
		System.out.println("Enter number of sticks:");
		sticks = input.nextInt();
		run();
	}
	
	// This function will run as long as the game is active.
	// Controls the game
	public void run(){
		while (!gameOver){
			System.out.println("Sticks left: " + Integer.toString(sticks));
			
			
			if (sticks == 1){
				if (turn  == true) System.out.println("Player"+ Integer.toString(playerID[0]) + " wins!");
				else if (turn  == false) System.out.println("Player"+ Integer.toString(playerID[1]) + " wins!");
				gameOver = true;
			}
			else if (turn == false){
				sticks = player1.removeSticks(sticks);
				turn = true;
				continue;
			}
			else if (turn == true){
				sticks = player2.removeSticks(sticks);
				turn = false;
				continue;
			}
		}
		System.out.println("Play again? Y/N");
		if(input.next().charAt(0) == 'Y') {
			game = new Game();
		}
		
		
		
	}
	public static void main(String[] args) {
		// Creating a new game.
		 new Game();
	}

}
