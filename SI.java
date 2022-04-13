package spaceinv.model;

import spaceinv.event.EventBus;
import spaceinv.event.ModelEvent;
import spaceinv.model.ships.AbstractSpaceship;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  SI (Space Invader) class representing the overall
 *  data and logic of the game
 *  (nothing about the look/rendering here)
 */
public class SI {

    // Default values (not to use directly). Make program adaptable
    // by letting other programmers set values if they wish.
    // If not, set default values (as a service)
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 500;
    public static final int LEFT_LIMIT = 50;
    public static final int RIGHT_LIMIT = 450;
    public static final int SHIP_WIDTH = 20;
    public static final int SHIP_HEIGHT = 20;
    public static final int SHIP_MAX_DX = 1;
    public static final int SHIP_MAX_DY = 0;
    public static final int GUN_WIDTH = 20;
    public static final int GUN_HEIGHT = 20;
    public static final double GUN_MAX_DX = 2;
    public static final double PROJECTILE_WIDTH = 5;
    public static final double PROJECTILE_HEIGHT = 5;
    public static final int GROUND_HEIGHT = 20;
    public static final int OUTER_SPACE_HEIGHT = 10;
    public static final int PROJECTILE_SPEED = 1;

    public static final long ONE_SEC = 1_000_000_000;
    public static final long HALF_SEC = 500_000_000;
    public static final long TENTH_SEC = 100_000_000;

    private static final Random rand = new Random();

    // TODO More references here
    private final Gun gun;
    private List<AbstractSpaceship> ships;
    private final List<Projectile> shipBombs = new ArrayList<>();
    private Projectile gunProjectile;
    private int points;
    private int i = 0;

    // TODO Constructor here
    public SI(Gun gun, List<AbstractSpaceship> ships) {
        this.gun = gun;
        this.ships = ships;
    }

    // Timing. All timing handled here!
    private long lastTimeForMove;
    private long lastTimeForFire;
    private int shipToMove = 0;

    // ------ Game loop (called by timer) -----------------

    public void update(long now) {
        if (ships.size() == 0) {
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_WON));
        }
        if(points<0){
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_LOST));
        }

        // gun movement
        if (gun.getDx() != 0) {
            gun.move();
        }

        // when gun fire
        if (gunProjectile != null) {
            gunProjectile.move();                                         // gun fired
            for (int i = 0; i < ships.size(); i++) {
                if (gunProjectile != null) {
                    if (gunProjectile.checkHit(ships.get(i))) {          // if Gun hit ship
                        EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GUN_HIT_SHIP, ships.get(i)));
                        points += ships.get(i).getPoints();
                        ships.remove(ships.get(i));                     // remove ship that got hit
                        gunProjectile = null;                           // remove gun projectile
                    }
                }
            }
            if (gunProjectile != null) {                // if no ship got hit, checks outerSpace
                gunProjectile.move();
                if (OuterSpace.hitOuterSpace(gunProjectile)) {
                    gunProjectile = null;
                }
            }
        }

        //ships movement
        for (int j = 0; j < ships.size(); j++) {
            ships.get(j).move();                                                    //alla ska röra sig
            if (ships.get(j).hitRightBorder() || ships.get(j).hitLeftBorder()) {
               for (int i = 0; i < ships.size(); i++) {                             //alla ska byta riktning
                    ships.get(i).switchDirection();
               }
            }
        }

        //if ships reach bottom or hit right or left border
        for(AbstractSpaceship ship : ships) {
            if (Ground.hitGround(ship)) {                               //if ships reach bottom
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_LOST));
            }
        }

        //random ships fire
            int dice = rand.nextInt(120);                       //how big dice is, if dice < x, it'll drop a bomb
            if (dice < 2) {
                int shipRoll = rand.nextInt(ships.size());             //randomizes a ship index, which will spawn a bomb
                shipBombs.add(ships.get(shipRoll).fire());             // add bomber ship to list
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_DROPPED,ships.get(shipRoll)));
            }

        //list of all bombs that should be removed
            List<Projectile> removeBombs = new ArrayList<>();
            for (int i =0; i<shipBombs.size(); i++) {
                shipBombs.get(i).move();                                // move bomber's projectile
                if(gunProjectile!=null){            //if gunprojectile exists & hits a bomb, it'll be removed with the bomb
                    if(gunProjectile.checkHit(shipBombs.get(i))){
                        EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GUN, gunProjectile));
                        gunProjectile = null;
                        removeBombs.add(shipBombs.get(i));
                    }
                }
                if (Ground.hitGround(shipBombs.get(i))) {                 // if projectile hit ground, remove from list shipBombs
                    EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GROUND,shipBombs.get(i)));
                    removeBombs.add(shipBombs.get(i));
                } else if (shipBombs.get(i).checkHit(gun)) {                 //if a bomb hits the gun
                    points -= 500;
                    EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GUN,gun));
                    removeBombs.add(shipBombs.get(i));
                }
            }
            shipBombs.removeAll(removeBombs);                               //alla bombs som har skjutits ska försvinna
    }


    // ---------- Interaction with GUI  -------------------------
    public void fireGun() {
        if (gunProjectile == null) {
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GUN_SHOOT,gun));
            gunProjectile = gun.fire();
        }
    }

    public List<Positionable> getPositionables() {
        List<Positionable> ps = new ArrayList<>();
        ps.add(gun);
        if (gunProjectile != null) {
            ps.add(gunProjectile);
        }
       ps.addAll(ships);
       ps.addAll(shipBombs);
        return ps;
    }

    public int getPoints() {
        return points;
    }

    public void setGunSpeed(double speed) {
        gun.setDx(speed);
    }


}


