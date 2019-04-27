import java.util.*;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;


public class Pasture {
    Constants constants;
    private final int MAX_WIDTH = 51;
    private final int MAX_HEIGHT = 36;
    private int plantCounter;
    private int sheepCounter;
    private int wolfCounter;
    
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    public final Set<Entity> cleanUp = new HashSet<Entity>();
    private final Set<Entity> world = 
        new HashSet<Entity>();
    private final Map<Point, List<Entity>> grid = 
        new HashMap<Point, List<Entity>>();
    private final Map<Entity, Point> point 
        = new HashMap<Entity, Point>();

    private final PastureGUI gui;

    public Pasture() {
        Engine engine = new Engine(this);
        gui = new PastureGUI(MAX_WIDTH, MAX_HEIGHT, engine);

        synchronized(gui.syncObject) {
            try {
                gui.syncObject.wait();
            } catch (InterruptedException e) {
            }
        }
        if (!gui.inputCustom.isEmpty()){
            updateConfig();
        }

        for (int i = 0; i < constants.WIDTH.get(); i++) {
            addEntity(new Fence(this), new Point(i,0));
            addEntity(new Fence(this), new Point(i, constants.HEIGHT.get() - 1));
        }
        for (int i = 1; i < constants.HEIGHT.get()-1; i++) {
            addEntity(new Fence(this), new Point(0,i));
            addEntity(new Fence(this), new Point(constants.WIDTH.get() - 1,i));
        }

        for (int i = 0; i < constants.NO_FENCES.get(); i++) {
            Fence fence = new Fence(this);
            addEntity(fence,getFreePosition(fence));
        }

        for (int i = 0; i < constants.NO_PLANTS.get(); i++) {
            Plant plant = new Plant(this);
            addEntity(plant,getFreePosition(plant));
        }
        for (int i = 0; i < constants.NO_SHEEPS.get(); i++) {
            Herbivore herbivore = new Herbivore(this);
            Point possition = getFreePosition(herbivore);
            herbivore.setPossition(possition);
            addEntity(herbivore,possition);
             
        }

        for (int i = 0; i < constants.NO_WOLFS.get(); i++) {
            Carnivore carnivore = new Carnivore(this);
            Point possition = getFreePosition(carnivore);
            carnivore.setPossition(possition);
            addEntity(carnivore,possition);
             
        }
        
        gui.update(plantCounter,sheepCounter,wolfCounter);
    }

    public void refresh() {
        for (Entity delete : cleanUp){
                deleteEntity(delete);
        }
        cleanUp.clear();

        gui.update(plantCounter,sheepCounter,wolfCounter);
    }

    public void addEntity(Entity entity, Point pos) {
        if (entity.getClass().equals(Plant.class)) plantCounter++;
        else if (entity.getClass().equals(Herbivore.class)) sheepCounter++;
        else if (entity.getClass().equals(Carnivore.class)) wolfCounter++;

        world.add(entity);

        List<Entity> l = grid.get(pos);
        if (l == null) {
            l = new  ArrayList<Entity>();
            grid.put(pos, l);
        }
        l.add(entity);

        point.put(entity,pos);

        gui.addEntity(entity, pos);
    }
    
    public void moveEntity(Entity e, Point newPos, Point oldPos) {
        List<Entity> l = grid.get(oldPos);
        if (!l.remove(e)) 
            throw new IllegalStateException("Inconsistent stat in Pasture");  
        l = grid.get(newPos);
        if (l == null) {
            l = new ArrayList<Entity>();
            grid.put(newPos, l);
        }
        l.add(e);

        point.put(e, newPos);

        gui.moveEntity(e, oldPos, newPos);
    }
    public void removeEntity(Entity entity){
        cleanUp.add(entity);
    }

    public void deleteEntity(Entity entity) {
        Point p = point.get(entity);

        if (entity.getClass().equals(Plant.class)) plantCounter--;
        else if (entity.getClass().equals(Herbivore.class)) sheepCounter--;
        else if (entity.getClass().equals(Carnivore.class)) wolfCounter--;

        world.remove(entity); 
        grid.get(p).remove(entity);
        point.remove(entity);
        gui.removeEntity(entity, p);

    }

    public Point getFreePosition(Entity entity)  
        throws MissingResourceException {
        Point position = new Point((int)(Math.random() * constants.WIDTH.get()),
                                   (int)(Math.random() * constants.HEIGHT.get())); 

        int p = position.x+position.y*constants.WIDTH.get();
        int m = constants.HEIGHT.get() * constants.WIDTH.get();
        int q = 97; //any large prime will do

        for (int i = 0; i<m; i++) {
            int j = (p+i*q) % m;
            int x = j % constants.WIDTH.get();
            int y = j / constants.WIDTH.get();

            position = new Point(x,y);
            boolean free = true;

            Collection <Entity> c = getEntitiesAt(position);
            if (!c.isEmpty()) {
                for (Entity thisThing : c) {
                    if(!entity.compatible.check(thisThing)) { 
                        free = false; break; 
                    }
                }
            }
            if (free) {
                return position;
            }
        }
        throw new MissingResourceException(
                  "There is no free space"+" left in the pasture",
                  "Pasture", "");
    }

    public List<Entity> getEntities() {
        return new ArrayList<Entity>(world);
    }
        
    public Collection<Entity> getEntitiesAt(Point lookAt) {
        Collection<Entity> l = grid.get(lookAt);
        
        
        if (l == null) {
            return new ArrayList<Entity>();
        }
        else {
            return new ArrayList<Entity>(l);
        }
    }

    public Point getEntityPosition(Entity entity) {
        return point.get(entity);
    }

    private void updateConfig(){
        if (gui.inputCustom.get(0) != -1) constants.WIDTH.set(gui.inputCustom.get(0));
        if (gui.inputCustom.get(1) != -1) constants.HEIGHT.set(gui.inputCustom.get(1));
        if (gui.inputCustom.get(2) != -1) constants.NO_FENCES.set(gui.inputCustom.get(2));
        if (gui.inputCustom.get(3) != -1) constants.NO_PLANTS.set(gui.inputCustom.get(3));
        if (gui.inputCustom.get(4) != -1) constants.NO_SHEEPS.set(gui.inputCustom.get(4));
        if (gui.inputCustom.get(5) != -1) constants.NO_WOLFS.set(gui.inputCustom.get(5));
        if (gui.inputCustom.get(6) != -1) constants.MATURE_SHEEPS.set(gui.inputCustom.get(6));
        if (gui.inputCustom.get(7) != -1) constants.MATURE_WOLFS.set(gui.inputCustom.get(7));
        if (gui.inputCustom.get(8) != -1) constants.DELAY_SHEEPS.set(gui.inputCustom.get(8));
        if (gui.inputCustom.get(9) != -1) constants.DELAY_WOLFS.set(gui.inputCustom.get(9));
        if (gui.inputCustom.get(10) != -1) constants.DEATH_SHEEPS.set(gui.inputCustom.get(10));
        if (gui.inputCustom.get(11) != -1) constants.DEATH_WOLFS.set(gui.inputCustom.get(11));
        if (gui.inputCustom.get(12) != -1) constants.SPEED_SHEEP.set(gui.inputCustom.get(12));
        if (gui.inputCustom.get(13) != -1) constants.SPEED_WOLF.set(gui.inputCustom.get(13));
        if (gui.inputCustom.get(14) != -1) constants.FOV_SHEEPS.set(gui.inputCustom.get(14));
        if (gui.inputCustom.get(15) != -1) constants.FOV_WOLFS.set(gui.inputCustom.get(15));
    }

    public static void main(String[] args) {
        new Pasture();
    }


}


