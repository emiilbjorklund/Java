/**
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
public class Fence extends Entity {
    
    /**
     * Creates a new static element, fence.
     * @param pasture The pasture that invokes the constructor.
    */
    public Fence(Pasture pasture) {
        super(pasture,"img/fence.gif",IsCompatible.FENCE);
    }

    @Override
    public void tick() {}

}