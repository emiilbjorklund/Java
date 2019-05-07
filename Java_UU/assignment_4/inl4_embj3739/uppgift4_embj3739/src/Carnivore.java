/**
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
import java.util.*;
import java.awt.Point;

public class Carnivore extends Animal {
    
    /** 
     * Adds a new object of same type in the pasture.
     * @param neighbours A List of available points.
    */
    public void giveBirth(List<Point> neighbours){
        Carnivore carnivore = new Carnivore(pasture);
        carnivore.setPossition(neighbours.get(0));
        pasture.addEntity(carnivore,neighbours.get(0));
    }

    /** 
     * Checks if an entity is food.
     * @param otherEntity An entity in the pasture.
     * @return A boolean, true if the entity is food.
    */
    public boolean isFood(Entity otherEntity){
        if (otherEntity instanceof Herbivore == true){
            return true;
        }
        else 
            return false; 
    }

    /** 
     * hecks if an entity is an enemy.
     * @param otherEntity An entity in the pasture.
     * @return A boolean, true if the entity is an enemy.
    */
    public boolean isEnemy(Entity otherEntity){
        return false;
    }

    /** 
     * The method that is trigged by a engine and performes action on the entity.
    */
    public void tick(){
        /** 
         * Performes action if tick is equal to the speed of the entity.
        */
        if (wait == pasture.constants.SPEED_WOLF.get()){
            List<Point> neighbours = checkEnviroment(pasture.constants.FOV_WOLFS.get());

            if (alive>=pasture.constants.DEATH_WOLFS.get()){
                pasture.removeEntity(this);
                return;
            }
            else if(hasFood){
                eat(food);
                
            }
            else if(hasFriend && pasture.constants.DEATH_WOLFS.get() - alive >= 100){
                followFriend(friend);
                
            } 
            else if (!neighbours.isEmpty()){
                if ((hasEaten) && (mature >=pasture.constants.MATURE_WOLFS.get()) && (reproduce >= pasture.constants.DELAY_WOLFS.get())){
                        giveBirth(neighbours);
                        reproduce = 0;
                }
                else
                    move(neighbours); 
            }
            wait = 0;
        }
        wait++;
        mature++;
        reproduce++;
        alive++;
    }

    /**
     * Creates a new carnivore.
     * @param pasture The pasture that invokes the constructor.
     */
    public Carnivore(Pasture pasture){
        super(pasture,"img/wolf.gif",IsCompatible.CARNIVORE);

    }
}
