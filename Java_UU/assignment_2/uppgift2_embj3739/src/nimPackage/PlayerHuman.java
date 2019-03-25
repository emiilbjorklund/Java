package nimPackage;
public class PlayerHuman extends Players {
	
	public int  removeSticks(int sticks) {
		
		System.out.println("Number of sticks to remove: ");
		move = input.nextInt();
		
		while (true) {
			if (ruleSet.rules(move, sticks) == true){
				sticks = sticks - move;
				break;
			}
			else {
				System.out.println("Illegal move, try again (between 1 and " + Integer.toString(sticks/2) +"):");
				move = input.nextInt();
			}
		}
		
		System.out.println("(Human) Player" + Integer.toString(id) + " removes " + Integer.toString(move) +" sticks");
		return sticks;
	}
	
	
	public PlayerHuman (int id) {
		this.id = id;
		
	}







}
