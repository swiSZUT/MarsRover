package de.szut;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
        return false;
    }

    public Rover load() {
        return null;
    }

}
