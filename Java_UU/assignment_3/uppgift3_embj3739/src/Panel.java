// Emil Björklund - embj3739
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import java.io.File;

@SuppressWarnings("serial")
public class Panel extends JComponent implements MouseMotionListener,
MouseListener{
	// Array of images objects
	Image[] layers; 
	private boolean[] isBackside;
	private boolean isActive = false;

	// Create Image objects with desired images. Loops the length of files array.
	private  void initLayers(File files[]) {
		for (int i = 0; i <= (files.length - 1)/2; i++) {
			layers[i] = new Image(i*10,i*20,files[i],files[i+(files.length)/2]);
		}
	}
	// Paints the Image on the Panel
	public void paintComponent(Graphics g){
		for (int i = layers.length - 1; i >= 0; i--) {
			layers[i].paint(g,isBackside[i]);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Puts the clicked image at index 0 (top-layer)
		for (int i = 0; i <= layers.length - 1; i++) {
			if (layers[i].constraint(e.getX(), e.getY())){
				Image temp = layers[i];
				
				for (int j = i; j > 0; j--){
					layers[j] = layers[j-1];
				}

				layers[0] = temp;
				isBackside[0] = !isBackside[0];
				repaint();
				break;
			}
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Puts the pressed image at index 0 (top-layer)
		for (int i = 0; i <= layers.length - 1; i++) {
			if (layers[i].constraint(e.getX(), e.getY())){
				Image temp = layers[i];
				
				for (int j = i; j > 0; j--){
					layers[j] = layers[j-1];
					isBackside[j] = isBackside[j-1];
				}
				
				layers[0] = temp;
				isActive = true;
				break;
			} 
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isActive = false;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// Updates the x y coordinates for the image based on mouse possition
		if (isActive){
			layers[0].action(e.getX(), e.getY());
			repaint();
			}
		}

	public Panel(File files[]) {
		layers = new Image[files.length/2];
		isBackside = new boolean[files.length/2];
		initLayers(files);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}


}
