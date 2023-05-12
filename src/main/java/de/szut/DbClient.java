package de.szut;

import de.szut.enums.Orientation;

import java.sql.*;

public class DbClient {

    private final String url = "jdbc:postgresql://localhost:5555/marsroverdb";
    private final String user = "postgres";
    private final String password = "postgres";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public boolean save(Rover rover) {
        Connection connection = connect();
        boolean success = true;

        try {
            Statement statement  = connection.createStatement();

            //Delete existing data
            statement.execute("TRUNCATE game;");
            statement.execute("TRUNCATE slopes;");

            //Save game data
            int roverX = rover.getX();
            int roverY = rover.getY();
            int roverOrientation = rover.getOrientation().ordinal();
            Landscape landscape = rover.getLandscape();
            int landscapeWidth = landscape.getWidth();
            int landscapeHeight = landscape.getHeight();
            String insertStatement = "INSERT INTO game VALUES (1, " + roverX + " , " + roverY + ", " + roverOrientation +
                    ", " + landscapeWidth + ", " + landscapeHeight + ");";
            int manipulatedRows = statement.executeUpdate(insertStatement);
            success &= manipulatedRows == 1;
            //Save slope data
            for (int i = 0; i < landscapeWidth; i++) {
                for (int j = 0; j < landscapeHeight; j++) {
                    int slope = landscape.getSlope(i, j);
                    if (slope != 0) {
                        insertStatement = "INSERT INTO slopes VALUES (" + i + ", " + j + ", " + slope + ");";
                        manipulatedRows = statement.executeUpdate(insertStatement);
                        success &= manipulatedRows == 1;
                    }
                }
            }
        } catch (Exception e) {
            success = false;
        }

        return success;
    }

    public Rover load() {
        Connection connection = connect();
        try {
            Statement statement = connection.createStatement();

            String selectStatement = "SELECT * FROM game;";
            ResultSet resultSet = statement.executeQuery(selectStatement);
            resultSet.next();
            int roverX = resultSet.getInt(2);
            int roverY = resultSet.getInt(3);
            Orientation roverOrientation = Orientation.values()[resultSet.getInt(4)];
            int landscapeWidth = resultSet.getInt(5);
            int landscapeHeight = resultSet.getInt(6);
            resultSet.close();

            Landscape landscape = new Landscape(landscapeWidth, landscapeHeight);
            selectStatement = "SELECT * FROM slopes;";
            resultSet = statement.executeQuery(selectStatement);
            while (resultSet.next()) {
                int x = resultSet.getInt(1);
                int y = resultSet.getInt(2);
                int slope = resultSet.getInt(3);
                landscape.setSlope(x, y, slope);
            }
            resultSet.close();

            return new Rover(landscape, roverX, roverY, roverOrientation);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
