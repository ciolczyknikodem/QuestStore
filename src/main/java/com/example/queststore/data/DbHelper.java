package com.example.queststore.data;


import com.example.queststore.utils.QueryLogger;
import org.sqlite.SQLiteConfig;

import java.sql.*;

public class DbHelper {

    private static final String DB_URL = "jdbc:sqlite:queststore.db";
    private static final String DRIVER = "org.sqlite.JDBC";
    private QueryLogger queryLogger = new QueryLogger();
    private Connection connection;

    private void openConnection() {

        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL, config.toProperties());
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    protected void closeConnection() {

        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) { /*ignored*/ }
    }

    protected PreparedStatement getPreparedStatement(String sqlStatement) throws SQLException {
        QueryLogger.log(sqlStatement);
        openConnection();
        return connection.prepareStatement(sqlStatement);
    }

    protected ResultSet query(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    public boolean update(PreparedStatement statement) {

        try {
            openConnection();
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        } finally {
            closeConnection();
        }
        return false;
    }
}