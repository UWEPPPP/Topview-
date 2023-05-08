package util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 刘家辉
 * @date 2023/04/06
 */
public class ConnectionPool {
    private static ConnectionPool instance;
    private final String url;
    private final String username;
    private final String password;
    private final Integer maxConnections;
    private final Integer initConnections;
    private Integer allConnections;
    private BlockingQueue<Connection> connectionPool;

    private ConnectionPool() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (FileReader fre = new FileReader("D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\src\\main\\resources\\properties")) {
            Properties properties = new Properties();
            properties.load(fre);
            maxConnections = Integer.parseInt(properties.getProperty("maxConnections"));
            initConnections = Integer.parseInt(properties.getProperty("initConnections"));
            allConnections = initConnections;
            url = properties.getProperty("URL");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            connectionPool = new ArrayBlockingQueue<>(maxConnections);
            for (int i = 0; i < initConnections; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                connectionPool.add(connection);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized ConnectionPool getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public static void close(PreparedStatement preparedStatement, ResultSet set) {
        try {
            if (set != null) {
                set.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (!connectionPool.isEmpty()) {
            return connectionPool.remove();
        } else {
            if (allConnections <= maxConnections) {
                Connection connection = DriverManager.getConnection(url, username, password);
                allConnections++;
                return connection;
            }
        }
        throw new SQLException("Connection limit exceeded");
    }

    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed() && connectionPool.size() < maxConnections) {
            connectionPool.add(connection);
        } else {
            throw new SQLException("Unable to release connection");
        }
    }


}

