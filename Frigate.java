package spaceinv.model.ships;

import spaceinv.model.Shootable;
import spaceinv.model.Projectile;

import static spaceinv.model.SI.SHIP_HEIGHT;
import static spaceinv.model.SI.SHIP_WIDTH;
/*
 *   Type of space ship
 */
public class Frigate extends AbstractSpaceship implements Shootable {

    // Default value
    public static final int FRIGATE_POINTS = 300;

    public Frigate(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public Frigate(double x, double y) {
        this(x,y,SHIP_WIDTH,SHIP_HEIGHT);
    }


    public int getPoints() {
        return FRIGATE_POINTS;
    }
}
