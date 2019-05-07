// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Superklass för Entities som rör på sig.
 */
public abstract class Moving extends Entity {
    
    protected Point direction = new Point(); // Entity rörelseriktning

    /**
    *   Slumpar fram en riktning för Entityn att röra sig i.
    *   Hastighet i X och Y kan vara mellan -1 och 1.
    *   Det innebär också att en Entity kan stå still
    */
    protected void randomDirection()
    {
        Random rand = new Random();
        direction.setLocation(rand.nextInt(3)-1, rand.nextInt(3)-1);    
    }
    
    /**
    *   Metod som kollar om en förflyttning i önskad riktning är möjlig
    *   Annars sätts en slumpad riktning
    *   @param Den pasture som aktuell entity hör till
    */
    protected boolean movement(Pasture pasture)
    {
    if(!pasture.entityMovment(this, direction))
        {
            randomDirection();
            return false;
        }
        return true;
    }

    /**
    *   Metod för att finna mat inom synfältet och sätta rikting åt det hållet
    *   @param Vilken typ av föda som skall hittas
    *   @param Vilken pasture aktuell entity hör till
    *   @param Synfält
    */
    protected void findFood(Class foodType, Pasture pasture, int lengthOfVision)
    {
        Point foodIsAt = pasture.getRandomMember(pasture.findNeighboursOfType(this, foodType, lengthOfVision));
        if (foodIsAt != null)
            {
                Point thisEntityPos = pasture.getEntityPosition(this);
                direction.x = (int)Math.signum(foodIsAt.x - thisEntityPos.x);
                direction.y = (int)Math.signum(foodIsAt.y - thisEntityPos.y);
            }
    }
    
}
