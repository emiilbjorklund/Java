//Leo Blumenberg
import java.awt.*;
import javax.swing.*;

public class Card {
	//Rektangulrt omrde som Ritar upp kort
	private Rectangle box;
	//Position p kort
	private int x, y;
	//Strolek p kort
	private int width, height;
	//Bestmmer den aktuella sidan av kort
	private Boolean side = true;
	//Bilder som kort ritar upp
	private ImageIcon frontside, backside;
	//Konstruktorn av kort
	public Card(int x0, int y0, ImageIcon frontside, ImageIcon backside) {
		this.width = frontside.getIconWidth();
		this.height = frontside.getIconHeight();
		this.frontside = frontside;
		this.backside = backside;
		x = x0;
		y = y0;
		side = true;
		box = new Rectangle(x, y, width, height);

	}
	//Stt framsida p kort
	public boolean getSide() {
		return side;
	}
	//hmta Rektangeln p kort
	public Rectangle getRect() {
		return box;
	}
	//Hmta bredden p kort
	public int getWidth() {
		return width;
	}
	//Hmta hjden p kort
	public int getHeight() {
		return height;
	}
	//Stt Positionen p Kort
	public void setPos(Point p) {
		box.x = p.x;
		box.y = p.y;
	}
	//Stt Framsidan  Kort
	public void setSide(Boolean b) {
		side = b;
	}

	//Ritar upp den aktuella sidan nr den anropas
	public void paint(Graphics g, Component c) {
		if (side) {
			frontside.paintIcon(c, g, box.x, box.y);
		} else {
			backside.paintIcon(c, g, box.x, box.y);
		}

	}
	//ndrar strolek p rektangeln beroende p storleken p .gif.
	public void action() {
		if (side) {
			box = new Rectangle(box.x,box.y,frontside.getIconWidth(),frontside.getIconHeight());
		} else {
			box = new Rectangle(box.x,box.y,backside.getIconWidth(),backside.getIconHeight());
		}
	}

}
