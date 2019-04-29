/**
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
import java.awt.Point;

public class Plant extends Entity {
    private int reproduceTimer = 0;

    /** 
     * Adds a new object of same type in the pasture.
    */
    public void giveBirth(){
        Plant plant = new Plant(pasture);
        Point newPos = pasture.getFreePosition(plant);
        pasture.addEntity(plant,newPos);
    }

    /** 
     * The method that is trigged by a engine and performes action on the entity.
    */
    public void tick() {
        if (reproduceTimer >=200){
            giveBirth();
            reproduceTimer = 0;
        }
        reproduceTimer++;
    }
    
    /** 
     * Creates a new plant.
     * @param pasture The pasture that invokes the constructor.
    */
    public Plant(Pasture pasture) {
        super(pasture,"img/plant.gif",IsCompatible.PLANT);

    }

}