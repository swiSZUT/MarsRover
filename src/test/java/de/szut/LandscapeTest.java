package de.szut;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LandscapeTest {

    Landscape landscape;

    @BeforeEach
    public void setUp() {
        landscape = new Landscape(100, 100);
    }

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

    @ParameterizedTest
    @CsvSource({"1, 2, 3", "20, 15, -10", "0, 0, 50", "99, 99, -13"})
    public void testSetSlope(int x, int y, int slope) {
        landscape.setSlope(x, y, slope);
        int[][] grid = landscape.getGrid();
        assertEquals(slope, grid[x][y]);
    }

    @ParameterizedTest
    @CsvSource({"-5, -10, 10", "-10, 40, -7", "150, 30, 45", "100, 100, -5"})
    public void testSetSlopeExceptionWhenOutOfBounds(int x, int y, int slope) {
        //Instantly green because exception is thrown by Array.
        assertThrows(IndexOutOfBoundsException.class, () -> landscape.setSlope(x, y, slope));
    }

    @ParameterizedTest
    @CsvSource({"1, 2, 3", "20, 15, -10", "0, 0, 50", "99, 99, -13"})
    public void testGetSlope(int x, int y, int slope) {
        landscape.setSlope(x, y, slope);
        assertEquals(slope, landscape.getSlope(x, y));
    }

    @ParameterizedTest
    @CsvSource({"-5, -10", "-10, 40", "150, 30", "100, 100"})
    public void testGetSlopeExceptionWhenOutOfBounds(int x, int y) {
        //Instantly green because exception is thrown by Array.
        assertThrows(IndexOutOfBoundsException.class, () -> landscape.getSlope(x, y));
    }

}
