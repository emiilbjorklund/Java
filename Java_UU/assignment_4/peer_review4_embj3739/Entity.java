// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * This is the superclass of all entities in the pasture simulation
 * system. This interface <b>must</b> be implemented by all entities
 * that exist in the simulation of the pasture.
 */
public abstract class Entity {
    

    protected abstract void tick();

    /** 
     * ImageIcon returns the icon of this entity, to be displayed by
     * the pasture gui.
     */
    public abstract ImageIcon getImage();
    
    public abstract boolean isCompatible(Entity otherEntity);
        
}
