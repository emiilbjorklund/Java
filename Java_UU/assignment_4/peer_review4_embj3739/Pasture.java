// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4
import java.util.*;
import java.awt.Point;

/**
 * A pasture contains sheep, wolves, fences, plants, and possibly
 * other entities. These entities move around in the pasture and try
 * to find food, other entities of the same kind and run away from
 * possible enimies. 
 */
public class Pasture {
    
    private int ageSheepDuplicate;    // Antal tick innan får förökar sig
    private int sheepduplicateRate;   // Antal tick mellan förökning
    private int numberOfSheep;        // Antal får
    private int sheepSpeed;           // Antal tick innan fåret flyttar sig
    private int sheepMaxTimeEat;      // Max antal klick utan mat
    private int sheepLengthOfVision;  // Hur långt ser entity

    private int ageWolfDuplicate;     // Antal tick innan varg förökar sig
    private int wolfduplicateRate;    // Antal tick mellan förökning
    private int numberOfWolf;         // Antal vargar
    private int wolfSpeed;            // Antal tick innan varg flyttar sig
    private int wolfMaxTimeEat;       // Max antal klick utan mat
    private int wolfLengthOfVision;   // Hur långt ser entity

    private int agePlantDuplicate;    // Antal tick innan gräs förökar sig
    private int plantDuplicateRate;   // Antal till mellan förökning
    private int numberOfPlants;       // Antalet plantor
  
    // Size of pasture
    private int   width;
    private int   height;
  
    private final int numberOfFence = 15;       // Denna parameter skall enligt uppgiften inte kunna ändras av användaren.
    
    private final Set<Entity> world = 
        new HashSet<Entity>();
    private final Map<Point, List<Entity>> grid = 
        new HashMap<Point, List<Entity>>();
    private final Map<Entity, Point> point 
        = new HashMap<Entity, Point>();

    private PastureGUI gui;


    
    /**
    *   Konstruktor
    *   Sätter in startparametrar och skapar en simulering
    *   @param ett objekt av klass Parameters med alla startparametrar
    */
    public Pasture(Parameters parameters)
    {
        ageSheepDuplicate = parameters.ageSheepDuplicate;
        sheepduplicateRate = parameters.sheepduplicateRate;
        numberOfSheep = parameters.numberOfSheep;
        sheepSpeed = parameters.sheepSpeed;
        sheepMaxTimeEat = parameters.sheepMaxTimeEat;
        sheepLengthOfVision = parameters.sheepLengthOfVision;
        ageWolfDuplicate = parameters.ageWolfDuplicate;
        wolfduplicateRate = parameters.wolfduplicateRate;
        numberOfWolf = parameters.numberOfWolf;
        wolfSpeed = parameters.wolfSpeed;
        wolfMaxTimeEat = parameters.wolfMaxTimeEat;
        wolfLengthOfVision = parameters.wolfLengthOfVision;
        agePlantDuplicate = parameters.agePlantDuplicate;
        plantDuplicateRate = parameters.plantDuplicateRate;
        numberOfPlants = parameters.numberOfPlants;
        width = parameters.width;
        height = parameters.height;
        
        startNewPasture();   
    }
 
    /** 
     * Creates a new instance of this class and places the entities in
     * it on random positions.
     */
    private void startNewPasture() {

       
        Engine engine = new Engine(this);
        gui = new PastureGUI(width, height, engine);

        /* The pasture is surrounded by a fence.  */
        for (int i = 0; i < width; i++) {
            addEntity(new Fence(this), new Point(i,0));
            addEntity(new Fence(this), new Point(i, height - 1));
        }
        for (int i = 1; i < height-1; i++) {
            addEntity(new Fence(this), new Point(0,i));
            addEntity(new Fence(this), new Point(width - 1,i));
        }

        /* 
         * Now insert the right number of different entities in the
         * pasture.
         */
         
        // Sätt in staket
        for (int i = 0; i < numberOfFence; i++)
        {
            Entity entity = new Fence(this);
            addEntity(entity, getFreePosition(width, height, entity));
        }
         
        // Sätt in Plantor
        for (int i = 0; i < numberOfPlants; i++)
        {
            Entity entity = new Plant(this, agePlantDuplicate, plantDuplicateRate);
            addEntity(entity, getFreePosition(width, height, entity));
        }
        
        // Sätt in Får
        for (int i = 0; i < numberOfSheep; i++)
        {
            Entity entity = new Sheep(this, ageSheepDuplicate, sheepduplicateRate, sheepSpeed, sheepMaxTimeEat, sheepLengthOfVision);
            addEntity(entity, getFreePosition(width, height, entity));
        }
        
        // Sätt in vargar
        for (int i = 0; i < numberOfWolf; i++)
        {
            Entity entity = new Wolf(this, ageWolfDuplicate, wolfduplicateRate, wolfSpeed, wolfMaxTimeEat, wolfLengthOfVision);
            addEntity(entity, getFreePosition(width, height, entity));
        }

        gui.update();
    }

    public void refresh() {
        gui.update();
    }


    /**
     * Add a new entity to the pasture.
     */
    public void addEntity(Entity entity, Point pos) {

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
    
    public void moveEntity(Entity e, Point newPos) {
        
        Point oldPos = point.get(e);
        List<Entity> l = grid.get(oldPos);
        if (!l.remove(e)) 
            throw new IllegalStateException("Inconsistent stat in Pasture");
        /* We expect the entity to be at its old position, before we
           move, right? */
                
        l = grid.get(newPos);
        if (l == null) {
            l = new ArrayList<Entity>();
            grid.put(newPos, l);
        }
        l.add(e);

        point.put(e, newPos);

        gui.moveEntity(e, oldPos, newPos);
    }

    /**
     * Remove the specified entity from this pasture.
     */
    public void removeEntity(Entity entity) { 

        Point p = point.get(entity);
        world.remove(entity); 
        grid.get(p).remove(entity);
        point.remove(entity);
        gui.removeEntity(entity, p);

    }

    /**
     * Various methods for getting information about the pasture
     */

    public List<Entity> getEntities() {
        return new ArrayList<Entity>(world);
    }
        
    public Collection<Entity> getEntitiesAt(Point lookAt) {

        Collection<Entity> l = grid.get(lookAt);
        
        if (l==null) {
            return null;
        }
        else {
            return new ArrayList<Entity>(l);
        }
    }

    /**
    *   Söker i angränsande rutor efter en Entity av önskad typeToFind
    *   Letar först i de närmsta rutorna, sedan längre och längre ut till lengthOfView
    *   @param Vilken entity är det som tittar
    *   @param Vilken typ av entity letar den efter
    *   @param Hur långt är synfältet
    *   @return Lista med Funna entities
    */
    public List<Point> findNeighboursOfType(Entity entity, Class typeToFind, int lengthOfView)
    {
        List<Point> found = new ArrayList<Point>();
        int entityX = getEntityPosition(entity).x;
        int entityY = getEntityPosition(entity).y;
        int repetition = 1;
        Collection<Entity> entitiesInThisSpot;
        while (repetition <= lengthOfView || found == null)
        {
            for (int x = -repetition; x <= repetition; x++)
            {
                for (int y = -repetition; y <= repetition; y++)
                {
                    Point p = new Point(entityX + x, entityY + y);
                    entitiesInThisSpot = getEntitiesAt(p);
                    if (entitiesInThisSpot != null)
                    {
                        for (Entity e : entitiesInThisSpot)
                        {
                            if (typeToFind.isInstance(e))
                            {
                                found.add(p);
                            }
                        }
                    }
                }
            }
        repetition++;
        }
        return found;
    }
    
    public List<Point> getFreeNeighbours(Entity entity) {
        List<Point> free = new ArrayList<Point>();

        int entityX = getEntityPosition(entity).x;
        int entityY = getEntityPosition(entity).y;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Point p = new Point(entityX + x, entityY + y);
                if (freeSpace(p, entity))
                {    
                    free.add(p);
                }
            }
        }
        return free;
    }

    public boolean freeSpace(Point p, Entity e) {
                              
        List <Entity> l = grid.get(p);
        if (l == null) return true;
        for (Entity old : l) 
            if (!old.isCompatible(e))
            {    
                return false;
            }
        return true;
    }

    public Point getEntityPosition(Entity entity) {
        return point.get(entity);
    }

    /**
     * A general method for grabbing a random element from a
     * list. 
     */
    public static <X> X getRandomMember(List<X> c) {
        if (c.size() == 0)
            return null;
        
        int n = (int)(Math.random() * c.size());
        
        return c.get(n);
    }
    
    /**
     * Returns a random free position in the pasture if there exists
     * one.
     * 
     * If the first random position turns out to be occupied, the rest
     * of the board is searched to find a free position. 
     */
    public Point getFreePosition(int width, int height, Entity entity) 
        throws MissingResourceException {
        Point position = new Point((int)(Math.random() * width),
                                   (int)(Math.random() * height)); 

        int p = position.x+position.y*width;
        int m = height * width;
        int q = 97; //any large prime will do

        for (int i = 0; i<m; i++) {
            int j = (p+i*q) % m;
            int x = j % width;
            int y = j / width;

            position = new Point(x,y);
            boolean free = true;

            Collection <Entity> c = getEntitiesAt(position);
            if (c != null) {
                for (Entity thisThing : c) {
                    if(!entity.isCompatible(thisThing)) { 
                        free = false;
                        break; 
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
    *   Metod som flyttar entities i rätt riktning om det går.
    */
    public boolean entityMovment(Entity entity, Point direction) 
    {    
            Point newPos = getEntityPosition(entity).getLocation();
            newPos.translate(direction.x, direction.y);
            if (freeSpace(newPos, entity))
            {
                moveEntity(entity, newPos);
                return true;
            }
            else
            {
                Point neighbour = getRandomMember(getFreeNeighbours(entity));
                if(neighbour != null)
                {
                    moveEntity(entity, neighbour);
                }
                return false;
            }
    }
    


    /** The method for the JVM to run. */
    public static void main(String[] args)
    {

        new Pasture(new Parameters());
    }


}


