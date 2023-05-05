package de.szut;

import de.szut.enums.Orientation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    public void testSaveAndLoadGame() {
        //Save
        DbClient dbClient = new DbClient();
        Landscape landscape = new Landscape(100, 100);
        Rover rover = new Rover(landscape, 5, 50, Orientation.EAST);
        landscape.setSlope(0, 0, 10);
        landscape.setSlope(1, 0, -5);
        landscape.setSlope(2, 0, 20);
        boolean saveSuccessful = dbClient.save(rover);

        assertTrue(saveSuccessful);

        //Create new local game state
        landscape = new Landscape(150, 200);
        rover = new Rover(landscape, 10, 10, Orientation.SOUTH);

        //Load
        rover = dbClient.load();
        landscape = rover.getLandscape();
        //Assert rover
        assertEquals(5, rover.getX());
        assertEquals(50, rover.getY());
        assertEquals(Orientation.EAST, rover.getOrientation());
        //Assert landscape
        assertEquals(100, landscape.getWidth());
        assertEquals(100, landscape.getHeight());
        assertEquals(10, landscape.getSlope(0, 0));
        assertEquals(-5, landscape.getSlope(1, 0));
        assertEquals(20, landscape.getSlope(2, 0));
        int nonZeroSlopes = 0;
        for (int i = 0; i < landscape.getWidth(); i++) {
            for (int j = 0; j < landscape.getHeight(); j++) {
                if (landscape.getSlope(i, j) != 0) {
                    nonZeroSlopes++;
                }
            }
        }
        assertEquals(3, nonZeroSlopes);
    }

}
