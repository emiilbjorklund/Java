package u2_1;

import javax.swing.JOptionPane;


public class Interface {

	public static void main(String[] args) {
		int input;
		String[] options = {"Avsluta","Öka Räknare", "Minska Räknare", "Visa Räknare"};
		Counter counter = new Counter();
		
		input = JOptionPane.showOptionDialog(null, "Vill du ange startvärden?", "Välkommen", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

		if(input == JOptionPane.OK_OPTION)
		{
			try {
			counter.setMax(Integer.parseInt(JOptionPane.showInputDialog("Max:")));
			counter.setMin(Integer.parseInt(JOptionPane.showInputDialog("Min:")));
			counter.setCounter(Integer.parseInt(JOptionPane.showInputDialog("Start:")));
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Felaktig inmatning",null,JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		while (true) {
			input = JOptionPane.showOptionDialog(null, "Menu", "Räknare", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
			if (input == 0) System.exit(0);
			if (input == 1) counter.increaseCounter();
			if (input == 2) counter.decreaseCounter();
			if (input == 3) counter.showCounter();
			
		
		}


	}

}
