/**
* <h1>Pasture Simulation</h1>
*
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
import java.util.*;
import java.awt.Point;


public class Pasture {
    Constants constants;
    private final int MAX_WIDTH = 51;
    private final int MAX_HEIGHT = 36;
    private int plantCounter;
    private int sheepCounter;
    private int wolfCounter;

    public final Set<Entity> cleanUp = new HashSet<Entity>();
    private final Set<Entity> world = 
        new HashSet<Entity>();
    private final Map<Point, List<Entity>> grid = 
        new HashMap<Point, List<Entity>>();
    private final Map<Entity, Point> point 
        = new HashMap<Entity, Point>();

    private final PastureGUI gui;

    /**
     * Creates a pasture.
    */
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
    /** Updates the GUI.
    */
    public void refresh() {
        for (Entity delete : cleanUp){
                deleteEntity(delete);
        }
        cleanUp.clear();

        gui.update(plantCounter,sheepCounter,wolfCounter);
    }

    /** Adds entity to pasture.
    * @param entity A Entity that will be added to pasture.
    * @param pos A point of the desired position of the entity.
    */
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

    /** Moves entity in pasture.
    * @param entity A Entity that will be moved in pasture.
    * @param newPos The new position (Point) of the entity.
    * @param oldPos The old position (Point) of the entity.
    */
    public void moveEntity(Entity entity, Point newPos, Point oldPos) {
        List<Entity> l = grid.get(oldPos);
        if (!l.remove(entity)) 
            throw new IllegalStateException("Inconsistent stat in Pasture");  
        l = grid.get(newPos);
        if (l == null) {
            l = new ArrayList<Entity>();
            grid.put(newPos, l);
        }
        l.add(entity);

        point.put(entity, newPos);

        gui.moveEntity(entity, oldPos, newPos);
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

    /** 
    * Checks for free space.
    * @param entity A Entity that will be checked for free space.
    * @return A free Point in pasture.
    */
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
    
    /**
     * Get entities in pasture.
     * @return A List of all elements in the pasture.
    */
    public List<Entity> getEntities() {
        return new ArrayList<Entity>(world);
    }
    
    /**
     * Get entities at a position in pasture.
     * @param lookAt A Point where to check for entities.
     * @return A Collection of all elements in a point.
    */
    public Collection<Entity> getEntitiesAt(Point lookAt) {
        Collection<Entity> l = grid.get(lookAt);
        
        
        if (l == null) {
            return new ArrayList<Entity>();
        }
        else {
            return new ArrayList<Entity>(l);
        }
    }
    /**
     * Get an entitys position.
     * @param entity A entity in the pasture.
     * @return A Point for the entity.
    */
    public Point getEntityPosition(Entity entity) {
        return point.get(entity);
    }
    /**
     * Updates user inputs.
    */
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


