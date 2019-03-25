

//Denna klass implementerar ett enkelt program i Java. En boll
//studsar inom en given rektangel. Din uppgift blir att utÃ¶ka detta
//program med mÃ¶jlighet till fler bollar, mÃ¶jlighet att Ã¤ndra
//storleken pÃ¥ rektangeln etc.

//Eftersom koden Ã¤r avsedd att lÃ¤sas av nybÃ¶rjare fÃ¶rklarar
//kommentarerna i koden Ã¤ven olika begrepp i programsprÃ¥ket.

//FÃ¶ljande rader Ã¤r till fÃ¶r att Java skall kunna fÃ¶rstÃ¥ och tolka
//anvÃ¤ndandet av standardklasser som Color och JFrame.
//
//Alla klasser och underpaket i paketen java.awt, java.awt.event samt
//javax.swing kan nu refereras direkt.

import java.awt.*;          
import java.awt.event.*;    
import javax.swing.*;       

//Ball. Denna klass beskriver konceptet boll. En boll har en position
//(givet av en koordinat (x,y)), en hastighet (givet av en
//differential (dx,dy)) och en fÃ¤rg.

class Ball {
 // Standardkonstanter (for alla instanser av Ball) 
 static int        defaultDiameter  = 10;
 static Color      defaultColor     = Color.yellow;
 static Rectangle  defaultBox       = new Rectangle(0,0,100,100);
 private boolean   size				= false;
 

 // Position 
 private int x, y;

 // Hastighet och riktning 
 private int dx, dy;

 // Diameter (storlek) 
 private int diameter;

 // FÃ¤rg 
 private Color color;

 // BegrÃ¤nsande rektangulÃ¤ra omrÃ¥de inom vilket bollen studsar 
 private Rectangle box;

 // Konstruktion av nya bollar krÃ¤ver position och riktning 
 public Ball( int x0, int y0, int dx0, int dy0 ) {
     x = x0;
     y = y0;
     dx = dx0;
     dy = dy0;

     color = defaultColor;
     diameter = defaultDiameter;
 }

 // SÃ¤tt ny fÃ¤rg 
 public void setColor( Color c ) {
     color = c;
 }
 //Sätt ny diameter
 public void setDiamter( int d ) {
     diameter = d;
 }

 // SÃ¤tt nytt begrÃ¤nsande rektangulÃ¤rt omrÃ¥de 
 public void setBoundingBox( Rectangle r ) {
     box = r;
 }

 // Rita ut en boll pÃ¥ givet grafiskt omrÃ¥de 
 public void paint( Graphics g ) {
     // Byt till bollens fÃ¤rg 
     g.setColor( color );

     // Bollen representeras som en fylld cirkel, dvs en ellips (oval)
     // med lika hÃ¶jd och bredd 
     g.fillOval( x, y, diameter, diameter );
  
 }

 // BegrÃ¤nsa bollen inom det rektangulÃ¤ra omrÃ¥det. Uppdatera hastigheten
 // om det behÃ¶vs.
 void constrain() {
     // Ge absoluta koordinater fÃ¶r det rektangulÃ¤ra omrÃ¥det
     int x0 = box.x;
     int y0 = box.y;
     int x1 = x0 + box.width - diameter;
     int y1 = y0 + box.height - diameter;
     
     if (diameter <= 5) size = true;
     if (diameter >=30) size = false;

     // Ã„ndra hastighet och riktning om bollen Ã¤r utanfÃ¶r det
     // rektangulÃ¤ra omrÃ¥det
     if (x < x0) {
         dx = Math.abs(dx);
     }
     if (x > x1) {
         dx = -Math.abs(dx);
     }
     if (y < y0) {
         dy = Math.abs(dy);
     }
     if (y > y1) {
         dy = -Math.abs(dy);
     }            
     
 }

 // Flytta bollen med aktuell riktning och hastighet ett steg 
 public void action() {
	 if (size == false) diameter--;
	 if (size == true) diameter++;
		 
    	 x = x + dx;
         y = y + dy;

     constrain();
 }
}

//Klassen BallPanel definierar en rityta dÃ¤r bollarna ritas upp. Den
//Ã¤rver klassen JPanel och implementerar ActionListener. Genom att
//implementera ActionListener kan man lÃ¥ta en Timer med jÃ¤mna
//mellanrum ge ett 'tick' dÃ¥ uppdatering av panelen ska gÃ¶ras.

class BallPanel extends JPanel implements ActionListener {
 // Bredd och hÃ¶jd  
 private int width, height;

 // En boll 
 private Ball ball1;
 //private Ball ball2;
 private int speed = 0;
 private Timer timer;
 // Timer. Skickar en signal var 50e millisekund till panelen som
 // skickas med som ActionListener.

 


 // Initiera attributen
 public BallPanel (int width, int height, int speed, Color input, int diameter) {
     // Ta reda pÃ¥ bredd och hÃ¶jd fÃ¶r ritytan
     this.width = width;
     this.height = height;
     timer = new Timer(speed,this);

     // Skapa en ny boll
     ball1 = new Ball( width / 10, height / 5, 5, 5 );
     //ball2 = new Ball( width / 10, height / 5, 5, 5 );
     // SÃ¤tt bollens rektangulÃ¤ra begrÃ¤nsande omrÃ¥de (bounding box)
     ball1.setBoundingBox( new Rectangle( 0, 0, width, height ) );
     //ball2.setBoundingBox( new Rectangle( 0, 0, width, height ) );
     // Skriver över defaultcolor med ball.SetColor med input BLUE! Uppgift 1.
     ball1.setColor(input);
     ball1.setDiamter(diameter);
     //ball2.setColor(Color.white);
     //ball2.setDiamter(20);
     
     // Starta timern;
     timer.start();
 }

 // Uppdatera (anropas vid omritning, repaint())
 public void paintComponent( Graphics g ) {
     // Rensa hela ritytan (med svart fÃ¤rg)
     
     g.setColor( Color.black );
     g.fillRect( 0, 0, width, height );

     // Rita ut bollen (pÃ¥ svart bakgrund)
     ball1.paint( g );
     //ball2.paint( g );
 }

 // NÃ¤r vi fÃ¥r en signal frÃ¥n timern... 
 public void actionPerformed(ActionEvent e) {
     if(width != getWidth() || height != getHeight())
         wasResized(getWidth(),getHeight());
     ball1.action();
     //ball2.action();
     // GÃ¶r vad som Ã¤r relevant med bollen
     repaint();      // GÃ¶r automatiskt ett anrop till
                     // paintComponent()
 }
 

 // Anropas om fÃ¶nstret Ã¤ndrar storlek
 public void wasResized( int newWidth, int newHeight ) {
     width = newWidth;
     height = newHeight;
     // Uppdaterar boxen om wasResized körs, det vill säga if statsen i actionPerformed är uppfyld.
     ball1.setBoundingBox( new Rectangle( 0, 0, width, height ) );
     //ball2.setBoundingBox( new Rectangle( 0, 0, width, height ) );
 }
}


//Denna klass definierar det fÃ¶nster som skapas av programmet. Ett
//fÃ¶nster (JFrame) skapas dÃ¤r en instans av BallPanel (ritytan)
//placeras.

public class BallWorld extends JFrame {
	
	
 
 // Skapa en panel 
 private BallPanel panel1 = new BallPanel (180, 180, 50, Color.blue, 30);
 private BallPanel panel2 = new BallPanel (180, 180, 25, Color.green, 10);
 public JPanel panel;
 public BallWorld () {
	 


     // LÃ¤gg till bollpanelen i mitten pÃ¥ ramen.
     Container c1 = getContentPane();
     setPreferredSize(new Dimension(500, 500));
     c1.setLayout(new BorderLayout());
     panel = new JPanel(new GridBagLayout());
     GridBagConstraints c = new GridBagConstraints();
     c.insets = new Insets(1,1,1,1);
     panel.add(panel1,c);
     c.gridwidth = GridBagConstraints.REMAINDER;
     panel.add(panel2,c);
     c.weighty = 1.0;
     panel.add(Box.createGlue(), c);
     add(panel, BorderLayout.LINE_START);
    
     setSize(200, 200);     // Ramens storlek.
     setLocation(100, 100);
     //c1.setOpaque(false);// SÃ¥ att ramen inte hamnar i ett hÃ¶rn.
     setVisible(true);
     //c2.setVisible(true);// GÃ¶r sÃ¥ att ramen syns.

     setDefaultCloseOperation(EXIT_ON_CLOSE);
     
 }

 // Denna metod startas av Javas virtuella maskin vid anropet java
 // BallWorld
 public static void main(String args[]) {
     // Detta kommando ger bÃ¤ttre animering i en del
     // OS. Avkommentera om bollen rÃ¶r sig ryckigt.
     // System.setProperty("sun.java2d.opengl", "true");
	 
     BallWorld world = new BallWorld();
    
     
 }
}


	