/**
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
import java.util.*;
import java.awt.Point;
import java.util.Random; 


public abstract class Animal extends Entity{
    Random rand = new Random();
    IsCompatible compatible = null;
    protected Point possition;

    protected Entity food = null;
    protected Entity friend = null;
    protected Entity enemy = null;

    protected boolean hasEaten = false;
    protected boolean hasFood = false;
    protected boolean hasFriend = false;
    protected boolean hasEnemy = false;

    protected int reproduce = 0;
    protected int alive = 0;
    protected int delay = 0;
    protected int wait = 0;
    protected int mature = 0;

    private int deltaX;
    private int deltaY;
    private int desiredX;
    private int desiredY;

    public abstract boolean isFood(Entity otherEntity);
    public abstract boolean isEnemy(Entity otherEntity);

    /**
     * Sets the current position of the entity
     * @param pos Position of the entity.
     */
    public void setPossition(Point pos){
        possition = pos;
    }

    /**
     * Checks if an entity is friendly.
     * @param otherEntity
     * @return boolean, true if the entity is friendly
     */
    public boolean isFriend(Entity otherEntity){
        if (this != otherEntity){
            return this.getClass().equals(otherEntity.getClass());
        }
        else return false;
    }

    /**
     * Removes a target from pasture by "eating"
     * @param food A entity that is food.
     */
    public void eat(Entity food){
        Point target = pasture.getEntityPosition(food);
        if (target == null){
            hasFood = false;
            return;
        }

        /**
         * Checks if the food is within one square away, if not invoke moveToTarget().
         */
        if ((Math.abs(target.x - possition.x) <= 1) && (Math.abs(target.x - possition.x) <=1)){
            pasture.moveEntity(this, target, possition);
            possition = pasture.getEntityPosition(this);
            pasture.removeEntity(food);
            hasEaten = true;
            hasFood = false;
            food = null;
            alive = 0;
        }
        else {
            moveToTarget(target);
            if (possition.equals(target)){
                pasture.removeEntity(food);
                hasEaten = true;
                hasFood = false;
                food = null;
                alive = 0;
            }
        }
    }

    /**
     * Invoked when an enemy is nearby.
     * @param enemy An entity of an enemy.
     */
    public void runFromThreat(Entity enemy){
        Point threat = pasture.getEntityPosition(enemy);
        if (threat == null){
            hasEnemy = false;
            return;
        }
        moveFromTarget(threat);
        hasEnemy = false;
    }

    /**
     * Invoked when a friend is nearby
     * @param friend An entity of an friend.
     */
    public void followFriend(Entity friend){
        Point buddy = pasture.getEntityPosition(friend);
        if (buddy == null){
            hasFriend = false;
            return;
        }
        moveToTarget(buddy);
        hasFriend = false;
    }

    /**
     * Checks the environment by iterating thru the points nearby.
     * Enemys, firends and food will be stored for later use. 
     * @param fov Determines the size of the scan.
     * @return Returns a list of available space.
     */
    public List checkEnviroment (int fov){
        List<Point> valSetOne = new ArrayList<Point>();
        food = null;
        for (int x = -fov; x <= fov; x++) {
            for (int y = -fov; y <= fov; y++) {
                
                Point p = new Point(possition.x + x, possition.y + y);
                Collection <Entity> collection = pasture.getEntitiesAt(p);
                 if(collection.isEmpty()){
                    valSetOne.add(p);
                } 
                else{
                    for (Entity entity : collection){
                        if (isEnemy(entity)){
                            hasEnemy = true;
                            enemy = entity;
                            
                        }
                        else if (isFriend(entity)){
                            hasFriend = true;
                            friend = entity;
                        }
                        else if(compatible.check(entity)){
                            if (isFood(entity) && food == null){
                                hasFood = true;
                                food = entity; 
                            }
                            else if((Math.abs(possition.x-p.x) <= 1) && (Math.abs(possition.y-p.y)) <= 1 ){
                                valSetOne.add(p);
                            }
                        }
                    }
                }     
            }
        }
        return valSetOne;
    }

    /**
     * Moves the entity to a random point
     * @param neighbours List of available space.
     */
    public void move(List<Point> neighbours) {
        int n = rand.nextInt(neighbours.size());
        pasture.moveEntity(this, neighbours.get(n),possition);
        possition = pasture.getEntityPosition(this);
    }

    /**
     * Moves the entity towards a desired point.
     * If the difference between the entitys position and the targets position is positive
     * the new position will be the current plus 1 and vice versa.
     * @param target A point of the target.
     */
    public void moveToTarget(Point target) {
        deltaX = (int)target.getX() - (int)possition.getX();
        deltaY = (int)target.getY() - (int)possition.getY();

        if (deltaX != 0){
            if(deltaX > 0) desiredX = 1;
            else desiredX = -1;
        }

        if (deltaY == 0) deltaY = 0;{
            if(deltaY > 0) desiredY = 1;
            else desiredY = -1;
        }

        Point goTo = new Point((int)possition.getX()+desiredX,(int)possition.getY()+desiredY);
        Collection <Entity> collection = pasture.getEntitiesAt(goTo);

        /**
         * Checks if the desried point is compatible.
         */
        if (!collection.isEmpty()){
            for (Entity entity : collection){
                if (compatible.check(entity)){
                    pasture.moveEntity(this, goTo,possition);
                    possition = pasture.getEntityPosition(this);
                }
            }
        }
        else if (collection.isEmpty()){
            pasture.moveEntity(this, goTo,possition);
            possition = pasture.getEntityPosition(this);
        }
    }
    /**
     * Moves the entity away a desired point.
     * If the difference between the entitys position and the targets position is positive
     * the new position will be the current minus 1 and vice versa.
     * @param threat A point of the target.
     */
    public void moveFromTarget(Point threat) {
        deltaX = (int)threat.getX() - (int)possition.getX();
        deltaY = (int)threat.getY() - (int)possition.getY();

        if(deltaX > 0) desiredX = -1;
        else desiredX = 1;

        if(deltaY > 0) desiredY = -1;
        else desiredY = 1;

        Point goTo = new Point((int)possition.getX()+desiredX,(int)possition.getY()+desiredY);
        Collection <Entity> collection = pasture.getEntitiesAt(goTo);

        /**
         * Checks if the desried point is compatible.
         */
        if (collection.isEmpty()){
            pasture.moveEntity(this, goTo,possition);
            possition = pasture.getEntityPosition(this);
        }
        else {
            for (Entity entity : collection){
                if (compatible.check(entity)){
                    pasture.moveEntity(this, goTo,possition);
                    possition = pasture.getEntityPosition(this);
                }
            }
        }
    }
    public Animal(Pasture pasture,String path, IsCompatible entity) {
        super(pasture,path,entity);
        compatible = entity;
    }

}