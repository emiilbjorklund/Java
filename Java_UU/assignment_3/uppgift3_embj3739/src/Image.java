// Emil Björklund - embj3739
import java.io.File; 
import java.io.IOException; 
import java.awt.image.BufferedImage; 
import java.awt.Graphics;
import javax.imageio.ImageIO;

public class Image extends IOException {
	private BufferedImage image = null;
	private BufferedImage image_backside = null;
	private int size = 100;
	private int x1= 0;
	private int y1= 0;
	private int offsetX;
	private int offsetY;

	// Reads image from file path
	public  void readImage(File file1,File file2) {
        try
        { 
			image = new BufferedImage(100, 100,BufferedImage.TYPE_INT_ARGB);
			image_backside = new BufferedImage(100, 100,BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(file1); 
			image_backside = ImageIO.read(file2); 
  
            System.out.println("Reading complete."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Error: "+e); 
        } 
	}
	// Checks if mouse position is inside the image coordinates
	public boolean constraint (int mx, int my){
		if (((mx >= x1) && (mx <= x1+size)) && ((my >= y1) && (my <= y1+size))){
			offsetX  = mx - x1;
		    offsetY = my - y1;
			return true;
		}
		return false;
	}
	// Updates the image position
	public void action (int mx, int my){
		x1 = mx - offsetX;
	    y1 = my - offsetY;
	}
	// Paints image, depends on if Backside is true or not which image that shows
	public void paint(Graphics g, boolean backside){
		if (backside) g.drawImage(image_backside, x1, y1, size,size,null);
		else g.drawImage(image, x1, y1, size,size,null);
	}
	
	public Image(int x1, int y1, File file1,File file2) {
		this.x1 = x1;
		this.y1 = y1;
		readImage(file1,file2);
	}

}
