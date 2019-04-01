//Leo Blumenberg
import java.awt.*;
import javax.swing.*;

public class CardFrame extends JFrame {

	// Skapa en panel
	
	
	private CardPanel panel = new CardPanel(600, 700);

	public CardFrame() {
		//Lgger Kortpanelen i mitten av fnstret
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		
		//Lgger in 7 kort i Kortpanelen
		panel.add(new Card(50, 50, new ImageIcon("katt.gif"), new ImageIcon("back.gif")));
		panel.add(new Card(300, 50, new ImageIcon("blab.gif"), new ImageIcon("pear.gif")));
		panel.add(new Card(50, 400, new ImageIcon("gira.gif"), new ImageIcon("peli.gif")));
		panel.add(new Card(70, 100, new ImageIcon("stef.gif"),new ImageIcon("stru.gif")));
		panel.add(new Card(500, 100, new ImageIcon("stef.gif"),new ImageIcon("black.gif")));
		panel.add(new Card(40, 550, new ImageIcon("katt.gif"),new ImageIcon("stru.gif")));
		panel.add(new Card(300, 550, new ImageIcon("gira.gif"),new ImageIcon("blab.gif")));
		//Stter storlek och position p fnstret
		setSize(600, 700);
		setLocation(100, 100);
		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	//Main metod
	public static void main(String args[]) {

		CardFrame world = new CardFrame();
	}
}