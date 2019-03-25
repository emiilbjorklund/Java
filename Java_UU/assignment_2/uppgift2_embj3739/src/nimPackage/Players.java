package nimPackage;
import java.util.Scanner;

public abstract class Players {
	Scanner input = new Scanner(System.in);
	Rules ruleSet = new Rules();
	public int move;
	public int id;
	public abstract int removeSticks(int sticks);
	
	

}
