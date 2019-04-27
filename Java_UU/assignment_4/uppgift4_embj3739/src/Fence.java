public class Fence extends Entity {

    public boolean isCompatible(Entity entity) {
        return false;
    }
        
    public Fence(Pasture pasture) {
        super(pasture,"/Developer/devjava/java_uu/assignment_4/uppgift4_embj3739/img/fence.gif",IsCompatible.FENCE);
    }

    @Override
    public void tick() {}

}