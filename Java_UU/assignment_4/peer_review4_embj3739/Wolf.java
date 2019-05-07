// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.List;

/**
*   Klass för Wolf, vargar
*/
public class Wolf extends Moving
{
    // Instansvariabler
    private final ImageIcon image = new ImageIcon("wolf.gif");     // Denna Entitys bild
    private final Pasture pasture;    // Var finns denna Entity
    private final int duplicateRate;  // Med vilket intervall skall entity föröka sig
    private final int ageDuplicate;   // Hur gammalt skall entity vara för att föröka sig
    private final int entitySpeed;      // Hur många tick mellan förflyttningar
    private final int maxTimeNoEat;      // Hur länge klarar sig entity utan mat
    private final int lengthOfVision; // Hur långt ser entity
    private int age;    // entityns ålder
    private int speed;   // Hur fort rör sig entity (lägre siffra = snabbare). Antalet tick mellan förflyttningar.
    private int lastEat;  // När åt entity senast?
    private boolean hasEaten = false;  // Har denna entity ätit någon gång?
    
    /**
    *   Konstruktor
    *   @param Vilken pasture skall entity läggas till
    *   @param Hur gammal måste entity vara innan den förökar sig
    *   @param Hur ofta förökar sig Entity
    *   @param Entitys hastighet. Antalet tick mellan förflyttningar.
    *   @param Hur många tick kan entity klara sig utan att äta
    *   @param Entitys synfält
    */
    public Wolf(Pasture pasture, int ageDuplicate, int duplicateRate, int entitySpeed, int maxTimeNoEat, int lengthOfVision)
    {
        this.pasture = pasture;
        this.ageDuplicate = ageDuplicate;
        age = ageDuplicate;
        this.duplicateRate = duplicateRate;
        this.entitySpeed = entitySpeed;
        speed = entitySpeed;
        this.maxTimeNoEat = maxTimeNoEat;
        this.lengthOfVision = lengthOfVision;
        lastEat = maxTimeNoEat;
        randomDirection();  // Ge entity en slumpmässig riktning
    
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
    */
    public void tick()
    {
    if (pasture.getEntityPosition(this) == null)
    {
        return;
    }
    // Uppdatera ålder, förflyttningstimer och mattimer.    
    age--;
    speed--;
    lastEat--;
  
        // Står vargen på får? I så fall ät.
        Collection<Entity> entitiesInThisSpot = pasture.getEntitiesAt(pasture.getEntityPosition(this));
            for (Entity e : entitiesInThisSpot)
            //if (entitiesInThisSpot != null)
            {
                if (e instanceof Sheep)
                {
                    pasture.removeEntity(e);
                    hasEaten = true;
                    lastEat = maxTimeNoEat;
                }
            }
       
        
        // Har entity ätit i tid?
        if (lastEat == 0)
        {
            pasture.removeEntity(this); // dog av svält
            return;                     // Finns inte denna Entity kvar så hoppar vi ur denna metod här och nu.
        }
        
        // Är det dags för reproduktion
        if (age == 0 && hasEaten)
        {
            Point neighbour = pasture.getRandomMember(pasture.getFreeNeighbours(this));
            if(neighbour != null)
            {
                pasture.addEntity(new Wolf(pasture, ageDuplicate,duplicateRate, entitySpeed, maxTimeNoEat, lengthOfVision), neighbour);
            }
            age = duplicateRate;
        }
        
        // Entity rör sig. 
        if(speed <= 0)
        {
            findFood(Sheep.class, pasture, lengthOfVision); // Titta om det finns mat inom synfältet och ändra i så fall riktning dit
            if (movement(pasture));
            {
                speed = entitySpeed;
            }
        } 
    }
  
    /**
    *   Metod för att kontrollera om denna entity är kompatibel med en annan.
    */
    public boolean isCompatible(Entity otherEntity)
    {
        if (otherEntity instanceof Fence || otherEntity instanceof Wolf)  // Vargar kan inte gå till rutor som har staket eller andra vargar
        {
            return false;
        }
        return true;
    }
}
