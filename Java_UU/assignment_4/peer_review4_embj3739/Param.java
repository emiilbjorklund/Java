// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4

// A simple program to illustrate how one can input a bunch of
// parameters without writing too much code. 
// Version 1. Create a JFrame.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
import javax.swing.JOptionPane;

/**
*   Klass för att läsa in start-parametrar från användaren.
*   Enbart små förändringar från kursens exempelklass.
*/
public class Param extends JPanel implements ActionListener
{
    private static JFrame f;
    private static Parameters parameters = new Parameters();
    
    Param() {
    }

    void init () {
	setLayout(new GridLayout(m.size()+1, 2));
    }

    private Map<String, JTextField> m = new HashMap<String, JTextField> ();
    // The values for all parameters are stored in the map m
    
    public void add (String param, String description, String _default) {
	JLabel label = new JLabel(description);
	JTextField jtf = new JTextField(_default);

	add(label);
	add(jtf);

	m.put(param, jtf);

	setLayout(new GridLayout(m.size()+1, 2, 10, 0));
    }

    // Simply print the parameters. If you use this code you should
    // course write a method that copies the values of the parameters
    // into your simulation.

    /**
    /   Användaren har klickat klar
    /   kopierar in värden från textrutorna in i parameterklassen
    /   Använder try för att fånga eventuella felaktiga inmatningar
    */
    public void actionPerformed(ActionEvent e) {
    try
    {
        parameters.ageSheepDuplicate = Integer.parseInt(m.get("ageSheepDuplicate").getText());
        parameters.sheepduplicateRate = Integer.parseInt(m.get("sheepduplicateRate").getText());
        parameters.numberOfSheep = Integer.parseInt(m.get("numberOfSheep").getText());
        parameters.sheepSpeed = Integer.parseInt(m.get("sheepSpeed").getText());
        parameters.sheepMaxTimeEat = Integer.parseInt(m.get("sheepMaxTimeEat").getText());
        parameters.sheepLengthOfVision = Integer.parseInt(m.get("sheepLengthOfVision").getText());

        parameters.ageWolfDuplicate = Integer.parseInt(m.get("ageWolfDuplicate").getText());
        parameters.wolfduplicateRate = Integer.parseInt(m.get("wolfduplicateRate").getText());
        parameters.numberOfWolf = Integer.parseInt(m.get("numberOfWolf").getText());
        parameters.wolfSpeed = Integer.parseInt(m.get("wolfSpeed").getText());
        parameters.wolfMaxTimeEat = Integer.parseInt(m.get("wolfMaxTimeEat").getText());
        parameters.wolfLengthOfVision = Integer.parseInt(m.get("wolfLengthOfVision").getText());

        parameters.agePlantDuplicate = Integer.parseInt(m.get("agePlantDuplicate").getText());
        parameters.plantDuplicateRate = Integer.parseInt(m.get("plantDuplicateRate").getText());
        parameters.numberOfPlants = Integer.parseInt(m.get("numberOfPlants").getText());

        parameters.width = Integer.parseInt(m.get("width").getText());
        parameters.height = Integer.parseInt(m.get("height").getText());
    
    }
    catch(Exception q)
    {
        JOptionPane.showMessageDialog(null, "Felaktigt värde", "Felaktigt värde", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (!parameters.validate())
    {
        JOptionPane.showMessageDialog(null, "Felaktigt värde", "Felaktigt värde", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    f.setVisible(false);        // Dölj inmatningsfönstret
    f.dispose();                // Ta bort inmatningsfönstret
    new Pasture(parameters);    // Starta simuleringen
    }
    
    /**
    /   Main-metoden som sätter upp inmatningsfälten
    /   och sätter en lyssnar på klarknappen
    */
    public static void main (String [] arg)
    {
	f = new JFrame();
	Param p = new Param();
	p.add("ageSheepDuplicate", "Tid innan Får förökar sig 1-900", Integer.toString(parameters.ageSheepDuplicate));
    p.add("sheepduplicateRate", "Hur fort förökar sig Får 1-900", Integer.toString(parameters.sheepduplicateRate));
	p.add("numberOfSheep", "Antal Får vid start 1-900", Integer.toString(parameters.numberOfSheep));
	p.add("sheepSpeed", "Fårs hastighet 1-900", Integer.toString(parameters.sheepSpeed));
	p.add("sheepMaxTimeEat", "Tid innan Får svälter 1-900", Integer.toString(parameters.sheepMaxTimeEat));
	p.add("sheepLengthOfVision", "Hur långt får ser 1-5", Integer.toString(parameters.sheepLengthOfVision));

	p.add("ageWolfDuplicate", "Tid innan Vargar förökar sig 1-900", Integer.toString(parameters.ageWolfDuplicate));
    p.add("wolfduplicateRate", "Hur fort förökar sig Vargar 1-900", Integer.toString(parameters.wolfduplicateRate));
	p.add("numberOfWolf", "Antal Vargar vid start 1-900", Integer.toString(parameters.numberOfWolf));
	p.add("wolfSpeed", "Vargars hastighet 1-900", Integer.toString(parameters.wolfSpeed));
	p.add("wolfMaxTimeEat", "Tid innan Vargar svälter 1-900", Integer.toString(parameters.wolfMaxTimeEat));
	p.add("wolfLengthOfVision", "Hur långt vargar ser 1-5", Integer.toString(parameters.wolfLengthOfVision));

	p.add("agePlantDuplicate", "Tid innan Gräs förökar sig 1-900", Integer.toString(parameters.agePlantDuplicate));
    p.add("plantDuplicateRate", "Hur fort förökar sig Gräs 1-900", Integer.toString(parameters.plantDuplicateRate));
	p.add("numberOfPlants", "Antal Gräs vid start 1-900", Integer.toString(parameters.numberOfPlants));
    
    p.add("width", "Hagens bredd 5-50", Integer.toString(parameters.width));
    p.add("height", "Hagens höjd 5-50", Integer.toString(parameters.height));

	JButton klar = new JButton("Klar!");
    
	p.add(klar);
	klar.addActionListener(p);

	f.add(p);
	f.pack();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	f.setLocation(screenSize.width/2 - f.getWidth()/2,
		      screenSize.height/2 - f.getHeight()/2);


	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}