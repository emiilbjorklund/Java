
//Leo Blumenberg
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CardPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
	// Storlek av kortpanel
	private int width, height;
	// Timmer till actionListener
	private Timer timer = new Timer(50, this);
	// ArrayList fr att lagra flera kort
	private ArrayList<Card> cardList = new ArrayList<>();

	// Konstruktor till KortPanel
	public CardPanel(int width, int height) {
		// Ta reda p bredd och hjd fr ritytan
		this.width = width;
		this.height = height;

		// Skapar lysnare till musen
		addMouseListener(this);
		addMouseMotionListener(this);

		// Starta timern
		timer.start();
	}

	// Lgger in nya kort i listan
	public void add(Card c) {
		cardList.add(c);
	}

	// Kallas varje gng vi fr en signal frn Timern
	public void actionPerformed(ActionEvent e) {
		if (width != getWidth() || height != getHeight())
			wasResized(getWidth(), getHeight());
		// ritar om allt
		repaint();
		// Genomfr alla fr�dringar p kort i Listan
		for (int i = 0; i < cardList.size(); i++) {
			cardList.get(i).action();
		}	

	}

	// Uppdaterar Storleken p KortPanelen
	private void wasResized(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;

	}

	// Uppdatera (anropas vid omritning, repaint())
	public void paintComponent(Graphics g) {
		// Rensa hela ritytan (med svart frg)

		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);

		// Ritar upp alla Kort i listan
		for (int i = 0; i < cardList.size(); i++) {
			cardList.get(i).paint(g, this);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Gr igenom listan av kort baklgnes fr att plocka kortet som r mlat lngst
		// fram.

		for (int i = cardList.size() - 1; i > -1; i--) {

			// Placerar kortet som klickades p lngst fram
			if (e.getClickCount() == 1 && cardList.get(i).getRect().contains(e.getPoint())
					&& SwingUtilities.isLeftMouseButton(e)) {
				Card c = cardList.get(i);
				cardList.remove(i);
				cardList.add(c);
				
			}
			// Byter sida p kortet som dubbelklickades p
			if (e.getClickCount() == 2 && cardList.get(i).getRect().contains(e.getPoint())
					&& SwingUtilities.isLeftMouseButton(e)) {

				if (cardList.get(i).getSide()) {
					cardList.get(i).setSide(false);
				} else {
					cardList.get(i).setSide(true);
				}
				// Bryter loopen s att man bara kan trycka p det versta kortet.
				i = -1;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Gr igenom listan av kort baklgnes fr att plocka kortet som r m�lat l�ngst
		// fram.

		for (int i = cardList.size() - 1; i > -1; i--) {
			// ndrar positionen p kortet efter att musen drar kortet tills musen slpper
			// kortet.
			if (SwingUtilities.isLeftMouseButton(e) && cardList.get(i).getRect().contains(e.getPoint())) {

				Point p1 = e.getPoint();
				p1 = new Point(p1.x - (cardList.get(i).getWidth() / 2), p1.y - (cardList.get(i).getHeight() / 2));

				cardList.get(i).setPos(p1);

				// placera kortet som dras runt lngst fram
				Card c = cardList.get(i);
				cardList.remove(i);
				cardList.add(c);
				// Bryter loopen s att man bara kan dra det �versta kortet.
				i = -1;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
