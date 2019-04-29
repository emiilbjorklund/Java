public class Fence extends Entity {

    public boolean isCompatible(Entity entity) {
        return false;
    }
        
    public Fence(Pasture pasture) {
        super(pasture,"img/fence.gif",IsCompatible.FENCE);
    }

    @Override
    public void tick() {}

}