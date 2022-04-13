package spaceinv.model.ships;

import spaceinv.model.*;
import spaceinv.model.Projectile;

/*
 *   Type of space ship
 */
public class BattleCruiser extends AbstractSpaceship implements Shootable {

    public static final int BATTLE_CRUISER_POINTS = 100;

    public BattleCruiser(double x, double y, double width, double height) {
        super(x,y,width,height);
      //  this.dx = 0;

    }
    public BattleCruiser(double x, double y) {
        this(x,y,SHIP_WIDTH,SHIP_HEIGHT);
    }

    public int getPoints() {
        return BATTLE_CRUISER_POINTS;
    }
}