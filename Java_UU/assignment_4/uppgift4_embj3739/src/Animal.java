import java.util.*;
import java.awt.Point;
import java.util.Random; 


abstract class Animal extends Entity{
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

    public void setPossition(Point pos){
        possition = pos;
    }

    public boolean isFriend(Entity otherEntity){
        if (this != otherEntity){
            return this.getClass().equals(otherEntity.getClass());
        }
        else return false;
    }

    public void eat(Entity food){
        Point target = pasture.getEntityPosition(food);
        if (target == null){
            hasFood = false;
            return;
        }

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

    public void runFromThreat(Entity enemy){
        Point threat = pasture.getEntityPosition(enemy);
        if (threat == null){
            hasEnemy = false;
            return;
        }
        moveFromTarget(threat);
        hasEnemy = false;
    }

    public void followFriend(Entity friend){
        Point buddy = pasture.getEntityPosition(friend);
        if (buddy == null){
            hasFriend = false;
            return;
        }
        moveToTarget(buddy);
        hasFriend = false;
    }

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
    public void move(List<Point> neighbours) {
        int n = rand.nextInt(neighbours.size());
        pasture.moveEntity(this, neighbours.get(n),possition);
        possition = pasture.getEntityPosition(this);
    }

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

    public void moveFromTarget(Point threat) {
        deltaX = (int)threat.getX() - (int)possition.getX();
        deltaY = (int)threat.getY() - (int)possition.getY();

        if(deltaX > 0) desiredX = -1;
        else desiredX = 1;

        if(deltaY > 0) desiredY = -1;
        else desiredY = 1;

        Point goTo = new Point((int)possition.getX()+desiredX,(int)possition.getY()+desiredY);
        Collection <Entity> collection = pasture.getEntitiesAt(goTo);

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