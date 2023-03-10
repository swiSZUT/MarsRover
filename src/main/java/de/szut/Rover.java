package de.szut;

import de.szut.enums.Orientation;

import static de.szut.enums.Orientation.*;

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
        switch (this.orientation) {
            case NORTH:
                this.orientation = EAST;
                break;
            case EAST:
                this.orientation = SOUTH;
                break;
            case SOUTH:
                this.orientation = WEST;
                break;
            case WEST:
                this.orientation = NORTH;
                break;
            default:
                //Do nothing
        }
    }

    public void turnLeft() {
        switch (this.orientation) {
            case NORTH:
                this.orientation = WEST;
                break;
            case EAST:
                this.orientation = NORTH;
                break;
            case SOUTH:
                this.orientation = EAST;
                break;
            case WEST:
                this.orientation = SOUTH;
                break;
            default:
                //Do nothing
        }
    }

    public String moveForward() {
        switch (this.orientation) {
            case NORTH:
                return moveInternal(xPos, modulo(this.yPos - 1, landscape.getHeight()));
            case EAST:
                return moveInternal(modulo(this.xPos + 1, landscape.getWidth()), yPos);
            case SOUTH:
                return moveInternal(xPos, modulo(this.yPos + 1, landscape.getHeight()));
            case WEST:
                return moveInternal(modulo(this.xPos - 1, landscape.getWidth()), yPos);
            default:
                return "";
        }

    }

    private String moveInternal(int newX, int newY) {
        int slope = landscape.getSlope(newX, newY);
        if (slope == 0) {
            xPos = newX;
            yPos = newY;
            return "I moved to " + xPos + ", " + yPos + ".";
        } else {
            return "I encountered a " + (slope > 0 ? "mountain":"chasm") + " with slope " + slope + ". My current position is " + xPos + ", " + yPos + ".";
        }
    }

    public String moveBackward() {
        switch (this.orientation) {
            case NORTH:
                return moveInternal(xPos, modulo(this.yPos + 1, landscape.getHeight()));
            case EAST:
                return moveInternal(modulo(this.xPos - 1, landscape.getWidth()), yPos);
            case SOUTH:
                return moveInternal(xPos, modulo(this.yPos - 1, landscape.getHeight()));
            case WEST:
                return moveInternal(modulo(this.xPos + 1, landscape.getWidth()), yPos);
            default:
                return "";
        }
    }

    public String moveUpward() {
        return "";
    }

    private int modulo(int divident, int divisor) {
        return (((divident % divisor) + divisor) % divisor);
    }

}
