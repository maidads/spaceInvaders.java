package spaceinv.model;


import static spaceinv.model.SI.*;


/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun extends AbstractPositionable implements Shootable {
    public Gun(double x, double y, double width, double height) {
        super(x,y,width,height);
    }

    public Gun(double x, double y) {
        this(x,y, GUN_WIDTH,GUN_HEIGHT);
    }

    public Projectile fire() {
        return Shooter.fire(this, PROJECTILE_SPEED);
    }


}


