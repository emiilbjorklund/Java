/**
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class PastureGUI extends JFrame implements ActionListener, FocusListener {
    public Object syncObject = new Object();

    private final ImageIcon II_EMPTY = new ImageIcon("img/empty.gif");
    private final Engine engine;

    private final JLabel[][] grid;
    private final Map<Point, java.util.List<ImageIcon>> icons = new HashMap<Point, java.util.List<ImageIcon>>();

    public Map<Integer, Integer> inputCustom = new HashMap<Integer, Integer>();

    ArrayList<String> placeholder = new ArrayList<String>();
    ArrayList<JTextField> textfield = new ArrayList<JTextField>();

    private final JLabel clockLabel = new JLabel("Time: 0");
    private final JLabel entitiesLabel = new JLabel("Entities: 0");
    private final JLabel monitorPlant = new JLabel("Plants: 0");
    private final JLabel monitorSheep = new JLabel("Sheeps: 0");
    private final JLabel monitorWolf = new JLabel("Wolves: 0");

    private final JTextField noFences = new JTextField(15);
    private final JTextField noPlants = new JTextField(15);
    private final JTextField noSheeps = new JTextField(15);
    private final JTextField noWolves = new JTextField(15);
    private final JTextField matureSheep = new JTextField(15);
    private final JTextField matureWolves = new JTextField("Mature (time) Wolves");
    private final JTextField delaySheep = new JTextField("Reproduce (time) Sheep");
    private final JTextField delayWolves = new JTextField("Reproduce (time) Wolves");
    private final JTextField speedSheep = new JTextField("Speed Sheep");
    private final JTextField speedWolves = new JTextField("Speed Wolves");
    private final JTextField fovSheep = new JTextField("FOV Sheep");
    private final JTextField fovWolves = new JTextField("FOV Wolves");

    private final JButton startButton = new JButton("Start");
    private final JButton stopButton = new JButton("Stop");
    private final JButton exitButton = new JButton("Exit");
    private final JButton defaultButton = new JButton("Default");
    private final JButton customButton = new JButton("Custom");

    private final int SCALE = 30;
    private final int height;
    private final int width;
    private int size = 0;
    private int plants;
    private int sheeps;
    private int wolves;

    /**
     * Creates a new instance of this class with the specified settings for the
     * pasture to display.
     * @param width Max width of pasture
     * @param height Max height of pasture
     * @param engine The engine declared in Pasture.
     */
    public PastureGUI(int width, int height, Engine engine) {
    
        placeholder.add("Width");
        placeholder.add("Height");
        placeholder.add("No. Fence");
        placeholder.add("No. Plants");
        placeholder.add("No. Sheeps");
        placeholder.add("No. Wolves");
        placeholder.add("Mature (time) Sheep");
        placeholder.add("Mature (time) Wolves");
        placeholder.add("Reproduce (time) Sheep");
        placeholder.add("Reproduce (time) Wolves");
        placeholder.add("Death (time)");
        placeholder.add("Death (time)");
        placeholder.add("Speed Sheep");
        placeholder.add("Speed Wolves");
        placeholder.add("FOV Sheep");
        placeholder.add("FOV Wolves");
        
        for (int i = 0; i <= placeholder.size()-1; i++){
            textfield.add(new JTextField());
            textfield.get(i).setSize(10, 10);
            textfield.get(i).setText(placeholder.get(i));
            textfield.get(i).setForeground(Color.gray);
            textfield.get(i).addFocusListener(this);
        }

        this.height = height;
        this.width = width;

        this.engine = engine;
        
        setSize(width * SCALE, height * SCALE);
       
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        exitButton.addActionListener(this);
        defaultButton.addActionListener(this);
        customButton.addActionListener(this);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,5));
        buttons.add(clockLabel);
        buttons.add(entitiesLabel);
        buttons.add(monitorPlant);
        buttons.add(monitorSheep);
        buttons.add(monitorWolf);
        buttons.add(startButton);
        buttons.add(stopButton);
        buttons.add(exitButton);
        buttons.add(defaultButton);
        buttons.add(customButton);

        JPanel field = new JPanel();
        field.setBackground(new Color(27,204,89));
        field.setLayout(new GridLayout(height, width));
        grid = new JLabel[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[x][y] = new JLabel(II_EMPTY);
                grid[x][y].setVisible(true);
                field.add(grid[x][y]);

            }
        }

        JPanel config = new JPanel();
        config.setLayout(new GridLayout(textfield.size(),1,2, 2));

        for (JTextField i : textfield){
            config.add(i);
        }

        Container display = getContentPane();
        display.setBackground(new Color(27,204,89));
        display.setLayout(new BorderLayout());
        setTitle("Simulation");
        display.add(field,BorderLayout.CENTER);
        display.add(buttons,BorderLayout.SOUTH);
        display.add(config,BorderLayout.EAST);


        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        exitButton.setEnabled(true);

        JOptionPane.showMessageDialog(this,"Max dimentions: Width = 50 and Height = 35.\nHighest speed is 1 and a bigger value will slow down the animal.");

        
        update(0,0,0);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Action tridded when user press buttons
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                exitButton.setEnabled(true);
                defaultButton.setEnabled(false);
                customButton.setEnabled(false);
                engine.start();
            
        }
        else if (e.getSource() == stopButton) {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            exitButton.setEnabled(true);
            engine.stop();
        }
        else if (e.getSource() == exitButton) {
            int option = JOptionPane.showOptionDialog(  
                    this,  
                    "Save Result?",  
                    "Exit", JOptionPane.YES_NO_OPTION,  
                    JOptionPane.WARNING_MESSAGE, null, null,  
                    null );  
            if( option == JOptionPane.YES_OPTION ) {
            try
            {
                String filename= "result.log";
                FileWriter fw = new FileWriter(filename,true);
                fw.write("Time: " + engine.getTime() + "\nPlants: " + plants + "\nSheeps: " + sheeps + "\nWolves: " + wolves + "\n\n");
                fw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            System.exit(0);
        }
        if( option == JOptionPane.NO_OPTION ) {
            System.exit(0);
        }
        }
        else if (e.getSource() == defaultButton) {
            synchronized(syncObject) {
                syncObject.notify();
            }
            startButton.setEnabled(true);
        }
        else if (e.getSource() == customButton){
            for (int i = 0; i <= textfield.size()-1; i++){
                if(isChar(textfield.get(i).getText())){
                    inputCustom.put(i, -1);
                }
                else if (!textfield.get(i).getText().isEmpty()){
                    int input = Integer.parseInt(textfield.get(i).getText());
                    if ((i == 0) &&(input > 50)){
                        JOptionPane.showMessageDialog(this,"Dimention out of bounds. Width MAX 50");
                        return;
                    }
                    if ((i == 1) &&(input > 35)){
                        JOptionPane.showMessageDialog(this,"Dimention out of bounds. Height MAX 35");
                        return;
                    }
                    inputCustom.put(i,input);
                }
            }
            synchronized(syncObject) {
                syncObject.notify();
            }
            startButton.setEnabled(true);
        }
        
    
    }

    /**
     * The method addEntity is called to notify the GUI that an entity
     * has been added to a position. The icon of the added entity is
     * displayed at the position.
     * @param e An entity that is added.
     * @param p Position of that entity.
     */
    public void addEntity(Entity e, Point p) {
        ImageIcon icon = e.getImage();

        java.util.List<ImageIcon> l = icons.get(p);
        if (l==null) {
            l = new ArrayList<ImageIcon>();
            icons.put(p, l);
        }
        l.add(icon);

        grid[p.x][p.y].setIcon(icon);
        
        size++;
    }

    /**
     * This method moves an existing entity.
     * @param e An entity in the pasture.
     * @param old Position of that entity.
     * @param ny A new position of that entity.
     */
    public void moveEntity(Entity e, Point old, Point ny) {
        removeEntity(e, old);
        addEntity(e, ny);
    }

    /**
     * The method removeEntity is called to notify the GUI that an
     * entity has been removed from a position. One icon among the
     * icons of the remaining entities is displayed at the position.
     * @param e An entity that is added.
     * @param p Position of that entity.
     */
    public void removeEntity(Entity e, Point p) {
        
        ImageIcon icon0 = e.getImage();

        java.util.List<ImageIcon> l = icons.get(p);
        l.remove(icon0);

        ImageIcon icon;
        if (l.isEmpty()) icon = II_EMPTY ;
            else icon = l.get(0);
        
        grid[p.x][p.y].setIcon(icon);
        
        size--;
    }
    /**
     * Checks if the user inputs contains characters.
     * @param input
     * @return A boolean, true if the input has a char.
     */
    private boolean isChar(String input){
    for (int i = 0; i < input.length();i++) {
        if ((Character.isLetter(input.charAt(i)) == true)) {
           return true;
        }
        
     }
     return false;
    }

    /**
     * Updates the GUI, called by Pasture.
     * @param plants Number of plants in the pasture.
     * @param sheeps Number of sheeps in the pasture.
     * @param wolves Number of wolves in the pasture.
     */
    public void update(int plants, int sheeps, int wolves) {
        this.plants = plants;
        this.sheeps = sheeps;
        this.wolves = wolves;

        clockLabel.setText("Time: " + engine.getTime());
        entitiesLabel.setText("Entities: " + size);
        monitorPlant.setText("Plants: " + this.plants);
        monitorSheep.setText("Sheeps: " + this.sheeps);
        monitorWolf.setText("Wolves: " + this.wolves);
    }
    /**
     * Listner trigegd by focus event.
     */
    @Override
    public void focusGained(FocusEvent e) {

        for (int i = 0; i <= textfield.size()-1; i++){
            if((e.getSource() == textfield.get(i))){
                textfield.get(i).setForeground(Color.black);
                textfield.get(i).setText("");
            }
        }

    }
    
    /**
     * Listner trigegd by focus event.
     */
    @Override
    public void focusLost(FocusEvent e) {

        for (int i = 0; i <= textfield.size()-1; i++){
            if((e.getSource() == textfield.get(i)) && (textfield.get(i).getText().isEmpty() == true)){
                textfield.get(i).setText(placeholder.get(i));
                textfield.get(i).setForeground(Color.gray);
            }
        }
        
    }
}

