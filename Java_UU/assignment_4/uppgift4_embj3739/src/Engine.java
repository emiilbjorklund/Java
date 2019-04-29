/**
* @author  Emil Björklund -embj3739
* @version 1.0
* @since   2019-04-29
*/
import java.util.*;
import javax.swing.Timer;
import java.awt.event.*;

/**
 * The simulation is run by an internal timer that sends out a 'tick'
 * with a given interval. One tick from the timer means that each
 * entity in the pasture should obtain a tick. When an entity obtains
 * a tick, this entity is allowed to carry out their tasks according
 * to what kind they are. This could mean moving the entity, making
 * the entity starve from hunger, or producing a new offspring.
 */
public class Engine implements ActionListener {
    
    private final int SPEED_REFERENCE = 1000;
    private final int speed           = 100;
    private final Timer timer         = new Timer(SPEED_REFERENCE/speed,this);
    private int time                  = 0;

    private Pasture pasture;

    /**
     * Creates a new engine.
     * @param pasture The pasture that invokes the engine.
     */
    public Engine (Pasture pasture) {
        this.pasture = pasture;
    }

    /**
     * Trigged by timer and invokes the entities in the pasture.
     */
    public void actionPerformed(ActionEvent event) {
     
        List<Entity> queue = pasture.getEntities();
        for (Entity e : queue) {
            e.tick();
        }
        pasture.refresh();
        time++;
    }

    /**
     * Sets the speed.
     * @param speed Speed of simulation.
     */
    public void setSpeed(int speed) {
        timer.setDelay(speed);
    }

    /**
     * Starts the simulation.
     */
    public void start() {
        setSpeed(speed);
        timer.start();
    }

    /**
     * Stops the simulation.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * @return The time of the simulation.
     */
    public int getTime () {
        return time;
    }

}
