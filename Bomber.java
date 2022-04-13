package spaceinv.model.ships;

import spaceinv.model.Shootable;
import spaceinv.model.Projectile;

import static spaceinv.model.SI.SHIP_HEIGHT;
import static spaceinv.model.SI.SHIP_WIDTH;

/*
 *   Type of space ship
 */
public class Bomber extends AbstractSpaceship implements Shootable {

    // Default value
    public static final int BOMBER_POINTS = 200;

    public Bomber(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public Bomber(double x, double y) {
        this(x,y,SHIP_WIDTH,SHIP_HEIGHT);
    }

    public int getPoints() {
        return BOMBER_POINTS;
    }
}
