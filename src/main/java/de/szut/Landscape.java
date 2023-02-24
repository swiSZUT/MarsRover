package de.szut;

public class Landscape {

    private int[][] grid;

    public Landscape(int x, int y) {
        if (x <= 0 || y <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be greater than 0.");
        }
        grid = new int[x][y];
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setSlope(int x, int y, int slope) {
        grid[x][y] = slope;
    }

    public int getSlope(int x, int y) {
        return grid[x][y];
    }
}
