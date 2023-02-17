package de.szut;

public class Landscape {

    private int[][] grid;

    public Landscape(int x, int y) {
        grid = new int[x][y];
    }

    public int[][] getGrid() {
        return grid;
    }

}
