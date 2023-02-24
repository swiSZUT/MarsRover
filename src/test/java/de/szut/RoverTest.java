package de.szut;

import de.szut.enums.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoverTest {

    @Mock private Landscape mockLandscape;

    @BeforeEach
    public void setUp() {
        mockLandscape = mock(Landscape.class);
    }

    @Test
    public void testInitialPlacementWithinBounds() {
        int[][] mockGrid = new int [100][100];
        when(mockLandscape.getGrid()).thenReturn(mockGrid);
        int startX = 20;
        int startY = 55;
        Orientation startOrientation = Orientation.NORTH;
        Rover rover = new Rover(mockLandscape, startX, startY, startOrientation);
        assertEquals(rover.getX(), startX);
        assertEquals(rover.getY(), startY);
        assertEquals(rover.getOrientation(), startOrientation);
    }

}
