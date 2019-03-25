package u2_1;

import javax.swing.JOptionPane;

public class Counter {
	static int defaultmax = 2147483647;
	static int defaultmin = -2147483647;
	static int defaultcounter = 0;
	
	private int min;
	private int max;
	private int counter;
	
	public void showCounter(){
		JOptionPane.showMessageDialog(null,"Värde: " + String.valueOf(counter),"Räknare", JOptionPane.WARNING_MESSAGE);
	}
	
	public void increaseCounter ()
	{
		counter++;
	}
	
	public void decreaseCounter ()
	{
		counter--;
	}
	
	public void readCounter ()
	{
		counter--;
	}
	
	public void setMax(int input) {
		max = input;
	}
	
	public void setMin(int input) {
		min = input;
	}
	
	public void setCounter(int input) {
		counter = input;
	}
	
	public Counter(){
		max = defaultmax;
		min = defaultmin;
		counter = defaultcounter;
		

	}




}
