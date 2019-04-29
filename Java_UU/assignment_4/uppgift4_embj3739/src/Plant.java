import java.awt.Point;

public class Plant extends Entity {
    private int reproduceTimer = 0;

    public boolean isCompatible(Entity entity) {
        if ((entity instanceof Fence) || (entity instanceof Plant) || (entity instanceof Animal)){
            return false;
        }
        else 
            return true;
    }
    public void giveBirth(){
        Plant plant = new Plant(pasture);
        Point newPos = pasture.getFreePosition(plant);
        pasture.addEntity(plant,newPos);
    }

    public void tick() {
        if (reproduceTimer >=200){
            giveBirth();
            reproduceTimer = 0;
        }
        reproduceTimer++;
    }
        
    public Plant(Pasture pasture) {
        super(pasture,"/Developer/devjava/java_uu/assignment_4/uppgift4_embj3739/img/plant.gif",IsCompatible.PLANT);

    }

}