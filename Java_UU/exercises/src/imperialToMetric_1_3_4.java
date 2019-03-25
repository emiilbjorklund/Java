import javax.swing.*;
import java.lang.Math.*;

public class imperialToMetric_1_3_4 {
	public static void main(String[] args) {
		
/*		String input;
		String output;
		double data;
		
		input = JOptionPane.showInputDialog("Ange bensinförbrukning i miles/gallon:");
		data = Double.parseDouble(input);
		data = 235.21/data;
		output = String.valueOf(data);
		JOptionPane.showMessageDialog(null, output);
*/
		String input;
		double data;
		double volym;
		double area;
		
		input = JOptionPane.showInputDialog("Ange radie:");
		data = Double.parseDouble(input);
		volym = (4 *Math.PI * Math.pow(data, 3))/3;
		area = 4 * Math.PI * Math.pow(data, 2);

		JOptionPane.showMessageDialog(null, "Area = " + String.valueOf(area) + " Volym = " + String.valueOf(volym));
		
	}

}
