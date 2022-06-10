package dev.randolph.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    
    private static ConnectionUtil connectionUtil;
    private static Properties databaseProperties;
    
    private ConnectionUtil() {
        // Init
        databaseProperties = new Properties();
        InputStream props = ConnectionUtil.class.getClassLoader().getResourceAsStream("connection.properties");
        
        // Loading properties from connection resource
        try {
            databaseProperties.load(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns an instance of a connection.
     * If the connection has already been created then it simply returns that instead.
     * @return The Connection.
     */
    public static synchronized ConnectionUtil getConnectionUtil() {
        // Checking if connection has already been created
        if (connectionUtil == null) {
            // Connection hasn't been created
            connectionUtil = new ConnectionUtil();
        }
        
        return connectionUtil;
    }
    
    /**
     * Creates a connection to the database as defined in the connection resource.
     * @return The connection to the database.
     */
    public Connection getConnection() {
        // Init
        Connection connection = null;
        String url = databaseProperties.getProperty("url");
        String username = databaseProperties.getProperty("username");
        String password = databaseProperties.getProperty("password");
        
        // Connecting to database
        try {
            connection = DriverManager.getConnection(url,  username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return connection;
    }
    
}
