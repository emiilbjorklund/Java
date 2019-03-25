import javax.swing.JOptionPane;

public class forsakring_1_5 {

	public static void main(String[] args) {
		String input;
		int currYear;
		int carYear;
		currYear = Integer.parseInt(JOptionPane.showInputDialog("Ange nuvarande årtal (fromat: XXXX):"));
		carYear = Integer.parseInt(JOptionPane.showInputDialog("Ange bilens årtal (fromat: XXXX):"));
		
		if (currYear - carYear <= 5) JOptionPane.showMessageDialog(null,"Helförsäkring rekomenderas");
		if ((currYear - carYear) > 5 && (currYear - carYear) < 25) JOptionPane.showMessageDialog(null,"Halvförsäkring rekomenderas");
		if (currYear - carYear >= 25) JOptionPane.showMessageDialog(null,"Veteranförsäkring rekomenderas");

	}

}
