import java.util.*;
import java.awt.Point;

public class Carnivore extends Animal {

    public void giveBirth(List<Point> neighbours){
        Carnivore carnivore = new Carnivore(pasture);
        carnivore.setPossition(neighbours.get(0));
        pasture.addEntity(carnivore,neighbours.get(0));
    }

    public boolean isFood(Entity otherEntity){
        if (otherEntity instanceof Herbivore == true){
            return true;
        }
        else 
            return false; 
    }

    public boolean isEnemy(Entity otherEntity){
        return false;
    }

    public void tick(){
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
    
    public Carnivore(Pasture pasture){
        super(pasture,"img/wolf.gif",IsCompatible.CARNIVORE);

    }
}
