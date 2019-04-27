import java.util.*;
import java.awt.Point;

public class Herbivore extends Animal {

    public void giveBirth(List<Point> neighbours){
        Herbivore herbivore = new Herbivore(pasture);
        herbivore.setPossition(neighbours.get(0));
        pasture.addEntity(herbivore,neighbours.get(0));
    }

    public boolean isFood(Entity otherEntity){
            if (otherEntity instanceof Plant == true){
                return true;
            }
            else 
                return false;
    }

    public boolean isEnemy(Entity otherEntity){
        if((otherEntity instanceof Carnivore) || (otherEntity instanceof Fence)){
            return true;
        }
        else 
            return false;
    }
    public void tick(){
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
    public Herbivore(Pasture pasture){
        super(pasture,"/Developer/devjava/java_uu/assignment_4/uppgift4_embj3739/img/sheep.gif",IsCompatible.HERBIVORE);

    }
}