/**
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
import javax.swing.*;

public abstract class Entity {
    private ImageIcon image = null;
    public Pasture pasture;
    IsCompatible compatible;

    /**
     * Assignes a new ImageIcon with a path.
     * @param path
     */
    public void selectImage(String path){
        image = new ImageIcon(path);
    }

    /**
     * @return Returns the image.
     */
    public ImageIcon getImage() {
        return image;
    }
    
    public abstract void tick();

    /**
     * Creates a new entity.
     * @param pasture The pasture that declares the entity.
     * @param path Path to the image
     * @param entity Enum that checks if the entity is compatible.
     */
    public Entity(Pasture pasture, String path,IsCompatible entity) {
        this.pasture   = pasture;
        selectImage(path);
        compatible = entity;
    }
}
