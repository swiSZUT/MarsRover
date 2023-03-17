package de.szut;

import de.szut.enums.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
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
        when(mockLandscape.getWidth()).thenReturn(100);
        when(mockLandscape.getHeight()).thenReturn(100);
    }

    @Test
    public void testInitialPlacementWithinBounds() {
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
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.SOUTH);
        assertEquals(expectedX, rover.getX());
        assertEquals(expectedY, rover.getY());
    }

    @ParameterizedTest
    @MethodSource("generateOrientations")
    public void testTurnRight(Orientation startOrientation, Orientation endOrientation) {
        Rover rover = new Rover(mockLandscape, 50, 50, startOrientation);
        rover.turnRight();
        assertEquals(endOrientation, rover.getOrientation());
    }

    @ParameterizedTest
    @MethodSource("generateOrientations")
    public void testTurnLeft(Orientation endOrientation, Orientation startOrientation) {
        Rover rover = new Rover(mockLandscape, 50, 50, startOrientation);
        rover.turnLeft();
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

    @ParameterizedTest
    @MethodSource("generateOrientationsAndPositionsForForward")
    public void testForwardNoObstacle(Orientation startOrientation, int expectedX, int expectedY) {
        Rover rover = new Rover(mockLandscape, 50, 50, startOrientation);
        rover.moveForward();
        assertEquals(expectedX, rover.getX());
        assertEquals(expectedY, rover.getY());
    }

    private static Stream<Arguments> generateOrientationsAndPositionsForForward() {
        List<Arguments> argumentsList = new ArrayList<>();

        argumentsList.add(Arguments.of(Orientation.NORTH, 50, 49));
        argumentsList.add(Arguments.of(Orientation.EAST, 51, 50));
        argumentsList.add(Arguments.of(Orientation.SOUTH, 50, 51));
        argumentsList.add(Arguments.of(Orientation.WEST, 49, 50));

        return argumentsList.stream();
    }

    @ParameterizedTest
    @MethodSource("generateOrientationsAndPositionsForForwardAcrossEdge")
    public void testForwardNoObstacleAcrossEdge(Orientation startOrientation, int startX, int startY,
                                                int expectedX, int expectedY) {
        Rover rover = new Rover(mockLandscape, startX, startY, startOrientation);
        rover.moveForward();
        assertEquals(expectedX, rover.getX());
        assertEquals(expectedY, rover.getY());
    }

    private static Stream<Arguments> generateOrientationsAndPositionsForForwardAcrossEdge() {
        List<Arguments> argumentsList = new ArrayList<>();

        argumentsList.add(Arguments.of(Orientation.NORTH, 50, 0, 50, 99));
        argumentsList.add(Arguments.of(Orientation.EAST, 99, 50, 0, 50));
        argumentsList.add(Arguments.of(Orientation.SOUTH, 50, 99, 50, 0));
        argumentsList.add(Arguments.of(Orientation.WEST, 0, 50, 99, 50));

        return argumentsList.stream();
    }

    @ParameterizedTest
    @CsvSource({"50, mountain", "-30, chasm", "5, mountain", "-10, chasm"})
    public void testForwardObstacle(int slope, String obstacleType) {
        int startX = 50;
        int startY = 50;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.SOUTH);
        when(mockLandscape.getSlope(50, 51)).thenReturn(slope);
        String obstacleMessage = rover.moveForward();
        assertEquals("I encountered a " + obstacleType + " with slope " + slope + ". My current position is 50, 50.", obstacleMessage);
        assertEquals(startX, rover.getX());
        assertEquals(startY, rover.getY());
    }

    @ParameterizedTest
    @MethodSource("generateOrientationsAndPositionsForBackward")
    public void testBackwardNoObstacle(Orientation startOrientation, int expectedX, int expectedY) {
        Rover rover = new Rover(mockLandscape, 50, 50, startOrientation);
        rover.moveBackward();
        assertEquals(expectedX, rover.getX());
        assertEquals(expectedY, rover.getY());
    }

    private static Stream<Arguments> generateOrientationsAndPositionsForBackward() {
        List<Arguments> argumentsList = new ArrayList<>();

        argumentsList.add(Arguments.of(Orientation.NORTH, 50, 51));
        argumentsList.add(Arguments.of(Orientation.EAST, 49, 50));
        argumentsList.add(Arguments.of(Orientation.SOUTH, 50, 49));
        argumentsList.add(Arguments.of(Orientation.WEST, 51, 50));

        return argumentsList.stream();
    }

    @ParameterizedTest
    @MethodSource("generateOrientationsAndPositionsForBackwardAcrossEdge")
    public void testBackwardNoObstacleAcrossEdge(Orientation startOrientation, int startX, int startY,
                                                int expectedX, int expectedY) {
        Rover rover = new Rover(mockLandscape, startX, startY, startOrientation);
        rover.moveBackward();
        assertEquals(expectedX, rover.getX());
        assertEquals(expectedY, rover.getY());
    }

    private static Stream<Arguments> generateOrientationsAndPositionsForBackwardAcrossEdge() {
        List<Arguments> argumentsList = new ArrayList<>();

        argumentsList.add(Arguments.of(Orientation.NORTH, 50, 99, 50, 0));
        argumentsList.add(Arguments.of(Orientation.EAST, 0, 50, 99, 50));
        argumentsList.add(Arguments.of(Orientation.SOUTH, 50, 0, 50, 99));
        argumentsList.add(Arguments.of(Orientation.WEST, 99, 50, 0, 50));

        return argumentsList.stream();
    }

    @ParameterizedTest
    @CsvSource({"50, mountain", "-30, chasm", "5, mountain", "-10, chasm"})
    public void testBackwardObstacle(int slope, String obstacleType) {
        int startX = 50;
        int startY = 50;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.SOUTH);
        when(mockLandscape.getSlope(50, 49)).thenReturn(slope);
        String obstacleMessage = rover.moveBackward();
        assertEquals("I encountered a " + obstacleType + " with slope " + slope + ". My current position is 50, 50.", obstacleMessage);
        assertEquals(startX, rover.getX());
        assertEquals(startY, rover.getY());
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 15, 7})
    public void testUpwardManageableSlope(int slope) {
        int startX = 50;
        int startY = 50;
        int endX = startX + 1;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.EAST);
        when(mockLandscape.getSlope(endX, startY)).thenReturn(slope);
        rover.moveUpward();
        assertEquals(endX, rover.getX());
        assertEquals(startY, rover.getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {16, 100, 400})
    public void testUpwardTooSteep(int slope) {
        int startX = 50;
        int startY = 50;
        int endX = startX + 1;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.EAST);
        when(mockLandscape.getSlope(endX, startY)).thenReturn(slope);
        String obstacleMessage = rover.moveUpward();
        assertEquals("I encountered a mountain with slope " + slope + ". My current position is 50, 50.", obstacleMessage);
        assertEquals(startX, rover.getX());
        assertEquals(startY, rover.getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -5, -20})
    public void testUpwardChasm(int slope) {
        int startX = 50;
        int startY = 50;
        int endX = startX + 1;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.EAST);
        when(mockLandscape.getSlope(endX, startY)).thenReturn(slope);
        String obstacleMessage = rover.moveUpward();
        assertEquals("I encountered a chasm with slope " + slope + ". My current position is 50, 50.", obstacleMessage);
        assertEquals(startX, rover.getX());
        assertEquals(startY, rover.getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -15, -7})
    public void testDownwardManageableSlope(int slope) {
        int startX = 50;
        int startY = 50;
        int endX = startX + 1;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.EAST);
        when(mockLandscape.getSlope(endX, startY)).thenReturn(slope);
        rover.moveDownward();
        assertEquals(endX, rover.getX());
        assertEquals(startY, rover.getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {-16, -100, -400})
    public void testDownwardTooSteep(int slope) {
        int startX = 50;
        int startY = 50;
        int endX = startX + 1;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.EAST);
        when(mockLandscape.getSlope(endX, startY)).thenReturn(slope);
        String obstacleMessage = rover.moveDownward();
        assertEquals("I encountered a chasm with slope " + slope + ". My current position is 50, 50.", obstacleMessage);
        assertEquals(startX, rover.getX());
        assertEquals(startY, rover.getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 20})
    public void testDownwardMountain(int slope) {
        int startX = 50;
        int startY = 50;
        int endX = startX + 1;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.EAST);
        when(mockLandscape.getSlope(endX, startY)).thenReturn(slope);
        String obstacleMessage = rover.moveDownward();
        assertEquals("I encountered a mountain with slope " + slope + ". My current position is 50, 50.", obstacleMessage);
        assertEquals(startX, rover.getX());
        assertEquals(startY, rover.getY());
    }

    @ParameterizedTest
    @CsvSource({"ff, 50, 52", "luf, 52, 50"})
    public void testExecuteCommands(String input, int endX, int endY) {
        //Positiv und negativ trennen?
        int startX = 50;
        int startY = 50;
        Rover rover = new Rover(mockLandscape, startX, startY, Orientation.SOUTH);
        //Landscape anpassen
        when(mockLandscape.getSlope(51, 50)).thenReturn(10);
        rover.execute(input);
        assertEquals(endX, rover.getX());
        assertEquals(endY, rover.getY());
    }

}
