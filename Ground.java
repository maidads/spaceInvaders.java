package spaceinv.model;


import static spaceinv.model.SI.*;

/*
    The ground where the Gun lives.
    Used to check if projectiles from ships have hit the ground
 */

public class Ground extends AbstractPositionable {
    public static final double height = GROUND_HEIGHT;

    public Ground(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public Ground(double x, double y) {
        this(x, y, GAME_WIDTH, GROUND_HEIGHT);
    }

    public static boolean hitGround(AbstractPositionable positionable){
        return (positionable.getY() >= GAME_HEIGHT-GROUND_HEIGHT);
    }

}
