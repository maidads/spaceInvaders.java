package spaceinv.model.ships;

import static spaceinv.model.SI.*;
import spaceinv.model.AbstractPositionable;
import spaceinv.model.Projectile;
import spaceinv.model.Shootable;
import spaceinv.model.Shooter;

import static spaceinv.model.SI.PROJECTILE_SPEED;

public abstract class AbstractSpaceship extends AbstractPositionable implements Shootable {
    public boolean hitLeftBorder;
    public boolean hitRightBorder;
    private double x;
    private double y;
    private double dx = 2;
    public static final int SHIP_WIDTH = 20;
    public static final int SHIP_HEIGHT = 20;
    public static final int LEFT_LIMIT = 50;
    public static final int RIGHT_LIMIT = 450;
    public static final int GAME_WIDTH = 500;


    public AbstractSpaceship(double x, double y, double width, double height) {
        super(x,y,width,height);
    }

    public Projectile fire() {
        return Shooter.fire( this, -PROJECTILE_SPEED);
    }

    public void switchDirection() {
        this.setDx(-this.getDx());
        this.setY(this.getY() + SHIP_HEIGHT);
        this.setX(this.getX() + this.getDx());
    }


    public abstract int getPoints();


}





