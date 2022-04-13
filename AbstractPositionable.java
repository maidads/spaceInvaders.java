package spaceinv.model;

import spaceinv.model.ships.AbstractSpaceship;

import static spaceinv.model.SI.*;

public abstract class AbstractPositionable implements Positionable {
    private final double width;
    private final double height;
    private double x;
    private double y;
    private double dx;
    private double dy;

    public AbstractPositionable(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dx = 0;
        this.dy = 0;
    }

    //if ship hit left border
    public boolean hitLeftBorder() {return (this.getX() == 0);}

    //if ship hit right border
    public boolean hitRightBorder() {return (this.getX() == GAME_WIDTH - this.getWidth());}

    public void move(){
        setX(getX()+getDx());
    }


/*
    För varje steg projectile rör sig uppåt så kollar den range. Vi subtraherar med 10 för att öka
    hitboxen, för att den inte ska ha stor möjlighet att smita igenom -> större range.
 */
    // checkHit check COLLISION
    public boolean checkHit(AbstractPositionable that){
        boolean xRange = (this.getX() > that.getX()) && (this.getX() < that.getX()+that.getWidth());
        boolean yRange = (this.getY()-10 < that.getY()+that.getHeight()) && (this.getY()+this.getHeight() > that.getY());
    return xRange && yRange;
}

    public void setX(double x) {
        //X värde sätts endast om X värdet är inom spelplanen (vänster/höger sida)
        if(x >= 0 && x <= GAME_WIDTH-this.getWidth()){
            this.x = x;
        }

    }

    public double getX() {
        return x;    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Getters
    public double getDx () {
        return dx;}

    public double getDy () {
        return dy;}


    //Setters
    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
    public void setY(double y) {
        this.y = y;
    }
}
