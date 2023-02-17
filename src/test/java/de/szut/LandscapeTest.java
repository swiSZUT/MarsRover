package de.szut;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LandscapeTest {

    Landscape landscape;

    @ParameterizedTest
    @CsvSource({"100, 100", "50, 50", "100, 150", "2000, 1000"})
    public void testInitLandscape(int x, int y) {
        landscape = new Landscape(x, y);
        int[][] grid = landscape.getGrid();
        assertEquals(x, grid.length);
        for (int i = 0; i < grid.length; i++) {
            assertEquals(y, grid[i].length);
        }
    }

    @ParameterizedTest
    @CsvSource({"0, 0", "-50, -50", "0, 100", "-100, 200"})
    public void testInitLandscapeExceptionWhenSizeTooSmall(int x, int y) {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Landscape(x, y));
        assertEquals("Grid dimensions must be greater than 0.", e.getMessage());
    }

}
