package nimPackage;

public class Rules {
	
	public boolean rules(int move, int sticks){
		if (move < 1) return false;
		else if (Double.valueOf(sticks)/Double.valueOf(move) < 2) return false;
		else return true;
	}
	
	public Rules () {
		
	}

}
