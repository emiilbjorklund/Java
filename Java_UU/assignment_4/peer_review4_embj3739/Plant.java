// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.List;

/**
*   Klass för Plant
*/
public class Plant extends Entity
{
    // Instansvariabler
    private final ImageIcon image = new ImageIcon("plant.gif");     // Denna Entitys bild
    private final Pasture pasture;    // Var finns denna Entity
    private final int duplicateRate;  // Med vilket intervall skall plantan föröka sig
    private final int ageDuplicate;   // Hur gammal skall plantan vara för att föröka sig
    private int age;    // Plantans ålder
    
    /**
    *   Konstruktor
    *   @param Vilken pasture skall entity läggas till
    *   @param Hur gammal måste entity vara innan den förökar sig
    *   @param Hur ofta förökar sig Entity
    */
    public Plant(Pasture pasture, int ageDuplicate, int duplicateRate)
    {
        this.pasture = pasture;
        this.ageDuplicate = ageDuplicate;
        age = ageDuplicate;
        this.duplicateRate = duplicateRate;
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
    *   tick-medtoden.
    *   anropas varje varv i Engines snurra
    */
    public void tick()
    {
            if (pasture.getEntityPosition(this) == null)
    {
        return;
    }
        age--;
        if (age == 0)
        {
            Point neighbour = pasture.getRandomMember(pasture.getFreeNeighbours(this));
            if(neighbour != null)
            {
                pasture.addEntity(new Plant(pasture, ageDuplicate,duplicateRate), neighbour);
            }
            age = duplicateRate;
        }
    }
  
    /**
    *   Metod för att kontrollera om denna entity är kompatibel med en annan.
    *   Plantor är kompatibel med alla utom staket.
    */
    public boolean isCompatible(Entity otherEntity)
    {
        if (otherEntity instanceof Fence || otherEntity instanceof Plant) // Gräs kan ej växa på rutor som har staket eller som redan har gräs
        {
            return false;
        }
        return true;
    }
   
    
}
