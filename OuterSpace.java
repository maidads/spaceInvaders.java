package spaceinv.model;

import static spaceinv.model.SI.*;
import static spaceinv.model.SI.OUTER_SPACE_HEIGHT;
import static spaceinv.model.SI.GAME_WIDTH;

/*
    Used to check if projectiles from gun have left our world
    Non visible class
 */
public class OuterSpace {
    public static boolean hitOuterSpace (Projectile projectile){
        return projectile.getY() <= OUTER_SPACE_HEIGHT;
    }
}