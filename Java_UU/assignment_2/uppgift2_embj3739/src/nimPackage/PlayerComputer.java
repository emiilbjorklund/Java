package nimPackage;
import java.util.Random;

public class PlayerComputer extends Players {
	private Random rand = new Random();
	
	public int removeSticks(int sticks) {
		
		try {
			move = rand.nextInt(sticks/2) + 1;
			System.out.println("(Computer) Player" + Integer.toString(id) +" removes " + Integer.toString(move) +" sticks");
			return sticks = sticks - move;
			}
		catch (Exception e){
			System.out.println("OPS! Computer cant make a move..");
			return sticks;
		}
	}
	
	
	public PlayerComputer (int id) {
		this.id = id;
	}

}
