package de.szut;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LandscapeTest {

    Landscape landscape;

    @ParameterizedTest
    @CsvSource({"100, 100", "50, 50", "100, 150", "2000000, 2000000"})
    public void testInitLandscape(int x, int y) {
        landscape = new Landscape(x, y);
        int[][] grid = landscape.getGrid();
        assertEquals(grid.length, x);
        for (int i = 0; i < grid.length; i++) {
            assertEquals(grid[i].length, y);
        }
    }

}
