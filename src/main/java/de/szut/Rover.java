package de.szut;

import de.szut.enums.Orientation;

public class Rover {

    private int xPos, yPos;
    private Orientation orientation;
    private Landscape landscape;

    public Rover(Landscape landscape, int startX, int startY, Orientation startOrientation) {
        this.landscape = landscape;
        this.xPos = modulo(startX, this.landscape.getWidth());
        this.yPos = modulo(startY, this.getLandscape().getHeight());
        this.orientation = startOrientation;
    }

    public Landscape getLandscape() {
        return this.landscape;
    }

    public int getX() {
        return this.xPos;
    }

    public int getY() {
        return this.yPos;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void turnRight() {

    }

    private int modulo(int divident, int divisor) {
        return (((divident % divisor) + divisor) % divisor);
    }

}
