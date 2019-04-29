import javax.swing.*;

abstract class Entity {
    private ImageIcon image = null;
    public Pasture pasture;
    IsCompatible compatible;

    public void selectImage(String path){
        image = new ImageIcon(path);
    }

    public ImageIcon getImage() {
        return image;
    }
    
    public abstract void tick();

    public Entity(Pasture pasture, String path,IsCompatible entity) {
        this.pasture   = pasture;
        selectImage(path);
        compatible = entity;
    }
}
