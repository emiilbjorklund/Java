// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.List;

/**
*   Klass för Sheep
*/
public class Sheep extends Moving
{
    // Instansvariabler
    private final ImageIcon image = new ImageIcon("sheep.gif");     // Denna Entitys bild
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
    public Sheep(Pasture pasture, int ageDuplicate, int duplicateRate, int entitySpeed, int maxTimeNoEat, int lengthOfVision)
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
    * @return Entitys bild
    */
    public ImageIcon getImage()
    {
        return image;
    }
    
    /**
    *   tick-medtoden.
    *   Anropas varje varv i Engines snurra
    */
    public void tick()
        {
        // Kontrollera först att denna entity finns kvar.
        // Den kan ha svältit ihjäl eller blivit uppäten.
        if (pasture.getEntityPosition(this) == null)
        {
            return; // Fanns den inte kvar så hoppar vi ur denna metod på en gång.
        }
        // Uppdatera ålder, förflyttningstimer och mattimer.    
        age--;
        speed--;
        lastEat--;
    
        // Står fåret på gräs? I så fall ät. Kollas direkt efter en förflyttning
        if (speed == entitySpeed-1)
        {
        Collection<Entity> entitiesInThisSpot = pasture.getEntitiesAt(pasture.getEntityPosition(this));
        for (Entity e : entitiesInThisSpot)
        {
            if (e instanceof Plant)
            {
                pasture.removeEntity(e);
                hasEaten = true;
                lastEat = maxTimeNoEat;
            }
        }
        }

        // Har entity ätit i tid?
        if (lastEat == 0)
        {
            pasture.removeEntity(this); // dog av svält
            return;                     // Entity finns inte kvar så vi hoppar ur detta nu.
        }
        
        // Är det dags för reproduktion
        if (age == 0 && hasEaten)       // Förrutom tid så skall entity ha ätit minst en gång.
        {
            Point neighbour = pasture.getRandomMember(pasture.getFreeNeighbours(this));
            if(neighbour != null)
            {
                pasture.addEntity(new Sheep(pasture, ageDuplicate,duplicateRate, entitySpeed, maxTimeNoEat, lengthOfVision), neighbour);
            }
            age = duplicateRate;
        }
        
        // Entity rör sig
        if(speed <= 0)
        {
            // Titta efter mat och ändra rikting mot den om den finns inom synhåll innan förflyttning.
            findFood(Plant.class, pasture, lengthOfVision);

            // Titta efter vargar och ändra rikning från den om det finns inom synhåll innan förflyttning
            // Titta efter vargar efter att ha tittat efter mat. Viktigare att fly från varg än att äta.
            Point enemyIsAt = pasture.getRandomMember(pasture.findNeighboursOfType(this, Wolf.class, lengthOfVision));
            if (enemyIsAt != null)
            {
                Point thisEntityPos = pasture.getEntityPosition(this);
                direction.x = -(int)Math.signum(enemyIsAt.x - thisEntityPos.x);
                direction.y = -(int)Math.signum(enemyIsAt.y - thisEntityPos.y);
            }
            
            if (movement(pasture));    // Utför rörelsen
            {
                speed = entitySpeed;  // Sätt antal tick till nästa förflyttning
            }
        } 
    }
      
    /**
    *   Metod för att kontrollera om denna entity är kompatibel med en annan.
    *   @param ett entitobjekt att jämföra med.
    *   @return sant/falskt om kompatibel eller ej
    */
    public boolean isCompatible(Entity otherEntity)
    {
        if (otherEntity instanceof Fence || otherEntity instanceof Sheep)  // Får kan inte befinna sig på samma ruta som staket eller ett annat får.
        {
            return false;
        }
        return true;
    }
}
