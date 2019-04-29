/**
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
import java.util.*;
import java.awt.Point;

public class Herbivore extends Animal {
    
    /** 
     * Adds a new object of same type in the pasture.
     * @param neighbours A List of available points.
    */
    private void giveBirth(List<Point> neighbours){
        Herbivore herbivore = new Herbivore(pasture);
        herbivore.setPossition(neighbours.get(0));
        pasture.addEntity(herbivore,neighbours.get(0));
    }
    
    /** 
     * Checks if an entity is food.
     * @param otherEntity An entity in the pasture.
     * @return A boolean, true if the entity is food.
    */
    public boolean isFood(Entity otherEntity){
            if (otherEntity instanceof Plant == true){
                return true;
            }
            else 
                return false;
    }

    /** 
     * Checks if an entity is an enemy.
     * @param otherEntity An entity in the pasture.
     * @return A boolean, true if the entity is an enemy.
    */
    public boolean isEnemy(Entity otherEntity){
        if((otherEntity instanceof Carnivore) || (otherEntity instanceof Fence)){
            return true;
        }
        else 
            return false;
    }
    /** 
     * The method that is trigged by a engine and performes action on the entity.
    */
    public void tick(){
        /** 
         * Performes action if tick is equal to the speed of the entity.
        */
        if (wait == pasture.constants.SPEED_SHEEP.get()){
            List<Point> neighbours = checkEnviroment(pasture.constants.FOV_SHEEPS.get());

            if (alive>=pasture.constants.DEATH_SHEEPS.get()){
                pasture.removeEntity(this);
                return;
            }
            else if(hasEnemy){
                runFromThreat(enemy);
                
            }
            else if(hasFood){
                eat(food);
                
            }
            else if(hasFriend && pasture.constants.DEATH_SHEEPS.get() - alive >= 100){
                followFriend(friend);
                
            } 
            else if (!neighbours.isEmpty()){
                if ((hasEaten) && (mature >=pasture.constants.MATURE_SHEEPS.get()) && (reproduce >= pasture.constants.DELAY_SHEEPS.get())){
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
     * Creates a new herbivore.
     * @param pasture The pasture that invokes the constructor.
     */
    public Herbivore(Pasture pasture){
        super(pasture,"img/sheep.gif",IsCompatible.HERBIVORE);

    }
}