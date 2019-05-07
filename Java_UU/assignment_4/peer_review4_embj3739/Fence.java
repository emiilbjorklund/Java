// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.List;

/**
*   Klass för staket
*/
public class Fence extends Entity
{
    // Instansvariabler
    // Denna Entitys bild
    private final ImageIcon image = new ImageIcon("fence.gif");
    // Var finns denna Entity
    private final Pasture pasture;
    
    /**
    *   Konstruktor
    */
    public Fence(Pasture pasture)
    {
        this.pasture = pasture;
    }
    
     /** 
     * Returns the icon of this entity, to be displayed by the pasture
     * gui. 
     * @see PastureGUI
     */
    public ImageIcon getImage()
    {
        return image;
    }
    
    /**
    *   tick-medtoden. Denna gör ingenting. Staketet är ett statiskt objekt som vare sig flyttar på sig eller åldras
    */
    public void tick()
    {}
    


    /**
    *   Metod för att kontrollera om denna entity är kompatibel med en annan.
    *   I detta fall eftersom det är ett staket returnerar den alltid false
    */
    public boolean isCompatible(Entity otherEntity)
    {
        return false;
    }
  
    
}
