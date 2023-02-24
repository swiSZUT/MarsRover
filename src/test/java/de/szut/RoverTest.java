package de.szut;

import de.szut.enums.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        assertEquals(mockLandscape, rover.getLandscape());
        assertEquals(startX, rover.getX());
        assertEquals(startY, rover.getY());
        assertEquals(startOrientation, rover.getOrientation());
    }

    @ParameterizedTest
    @CsvSource({"120, 100, 20, 0", "-30, -123, 70, 77", "200, -150, 0, 50"})
    public void testInitialPlacementWrapsAround(int startX, int startY, int expectedX, int expectedY) {
        when(mockLandscape.getWidth()).thenReturn(100);
        when(mockLandscape.getHeight()).thenReturn(100);
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.SOUTH);
        assertEquals(expectedX, rover.getX());
        assertEquals(expectedY, rover.getY());
    }

    @ParameterizedTest
    @MethodSource("generateOrientations")
    public void testTurnRight(Orientation startOrientation, Orientation endOrientation) {
        when(mockLandscape.getWidth()).thenReturn(100);
        when(mockLandscape.getHeight()).thenReturn(100);
        Rover rover = new Rover(mockLandscape, 50, 50, startOrientation);
        rover.turnRight();
        assertEquals(endOrientation, rover.getOrientation());
    }

    private static Stream<Arguments> generateOrientations() {
        List<Arguments> argumentsList = new ArrayList<>();

        argumentsList.add(Arguments.of(Orientation.NORTH, Orientation.EAST));
        argumentsList.add(Arguments.of(Orientation.EAST, Orientation.SOUTH));
        argumentsList.add(Arguments.of(Orientation.SOUTH, Orientation.WEST));
        argumentsList.add(Arguments.of(Orientation.WEST, Orientation.NORTH));

        return argumentsList.stream();
    }

}
