// Emil Björklund - embj3739
import java.awt.Color;
import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.Arrays;


@SuppressWarnings({ "unused", "serial" })
public class Window extends JFrame {
	
	public Window(File files[]) {
		// Creating a panel for the images
		Panel panel = new Panel(files);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add panel to window
		Container c = getContentPane();
		c.add(panel);
		getContentPane().setBackground( Color.white );
	
		setTitle("Uppgift 3 - embj3739");
		setSize(500,500);
		setVisible(true);
		
	}

	public static void main(String[] args){
		JFileChooser chooser = new JFileChooser();
		JFrame frame = new JFrame();
		
		File[] files = null;
		boolean isEven = false;
		
		
		// For user input, a window will allow the user to select desired images.
		while (!isEven){
			JOptionPane.showMessageDialog(
				frame, "Please select an even number of images", "Info", JOptionPane.INFORMATION_MESSAGE);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
			
			chooser.setMultiSelectionEnabled(true);
			int choice = chooser.showOpenDialog(new JDialog());

			if (choice == JFileChooser.CANCEL_OPTION){
				System.exit(0);
			}
			
			files = chooser.getSelectedFiles();
			if (files.length % 2 == 0){
				isEven = true;
			}
		}
		// Creating a new window with the files as argument
		Window window = new Window(files);
	}
}
