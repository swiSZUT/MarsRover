package de.szut;

import de.szut.enums.Orientation;

import static de.szut.enums.Orientation.*;

public class Rover {

    final private int maxManageableSlopeUp = 15;
    final private int maxManageableSlopeDown = -15;
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
                return moveInternal(xPos, modulo(this.yPos - 1, landscape.getHeight()), 0, 0);
            case EAST:
                return moveInternal(modulo(this.xPos + 1, landscape.getWidth()), yPos, 0, 0);
            case SOUTH:
                return moveInternal(xPos, modulo(this.yPos + 1, landscape.getHeight()), 0, 0);
            case WEST:
                return moveInternal(modulo(this.xPos - 1, landscape.getWidth()), yPos, 0, 0);
            default:
                return "";
        }

    }

    private String moveInternal(int newX, int newY, int minSlope, int maxSlope) {
        int slope = landscape.getSlope(newX, newY);
        if (slope < minSlope) {
            return "I encountered a chasm with slope " + slope + ". My current position is " + xPos + ", " + yPos + ".";
        } else if (slope > maxSlope) {
            return "I encountered a mountain with slope " + slope + ". My current position is " + xPos + ", " + yPos + ".";
        } else {
            xPos = newX;
            yPos = newY;
            return "I moved to " + xPos + ", " + yPos + ".";
        }
    }

    public String moveBackward() {
        switch (this.orientation) {
            case NORTH:
                return moveInternal(xPos, modulo(this.yPos + 1, landscape.getHeight()), 0, 0);
            case EAST:
                return moveInternal(modulo(this.xPos - 1, landscape.getWidth()), yPos, 0, 0);
            case SOUTH:
                return moveInternal(xPos, modulo(this.yPos - 1, landscape.getHeight()), 0, 0);
            case WEST:
                return moveInternal(modulo(this.xPos + 1, landscape.getWidth()), yPos, 0, 0);
            default:
                return "";
        }
    }

    public String moveUpward() {
        switch (this.orientation) {
            case NORTH:
                return moveInternal(xPos, modulo(this.yPos - 1, landscape.getHeight()), 1, maxManageableSlopeUp);
            case EAST:
                return moveInternal(modulo(this.xPos + 1, landscape.getWidth()), yPos, 1, maxManageableSlopeUp);
            case SOUTH:
                return moveInternal(xPos, modulo(this.yPos + 1, landscape.getHeight()), 1, maxManageableSlopeUp);
            case WEST:
                return moveInternal(modulo(this.xPos - 1, landscape.getWidth()), yPos, 1, maxManageableSlopeUp);
            default:
                return "";
        }
    }

    public String moveDownward() {
        switch (this.orientation) {
            case NORTH:
                return moveInternal(xPos, modulo(this.yPos - 1, landscape.getHeight()), maxManageableSlopeDown, -1);
            case EAST:
                return moveInternal(modulo(this.xPos + 1, landscape.getWidth()), yPos, maxManageableSlopeDown, -1);
            case SOUTH:
                return moveInternal(xPos, modulo(this.yPos + 1, landscape.getHeight()), maxManageableSlopeDown, -1);
            case WEST:
                return moveInternal(modulo(this.xPos - 1, landscape.getWidth()), yPos, maxManageableSlopeDown, -1);
            default:
                return "";
        }
    }

    public void execute (String input) {
    	for (int i = 0; i < input.length(); i++) {
    		char c = input.charAt(i);
    		switch(c) {
    		case 'f':
    			moveForward();
    			break;
    		case 'r':
    			turnRight();
    			break;
    		case 'l':
    			turnLeft();
    			break;
    		case 'u':
    			moveUpward();
    			break;
    		case 'd':
    			moveDownward();
    			break;
    		case 'b':
    			moveBackward();
    			break;
    		default:
                throw new IllegalArgumentException("Only the characters 'frludb' are allowed.");
    		}
    	}
    }

    private int modulo(int divident, int divisor) {
        return (((divident % divisor) + divisor) % divisor);
    }

}
