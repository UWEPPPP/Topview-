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
    private Integer currentConnections;
    private BlockingQueue<Connection> connectionPool;
    private boolean isShrinking = false;
    private boolean isExpanding = false;


    private ConnectionPool() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (FileReader fre = new FileReader("D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\src\\main\\resources\\properties")) {
            Properties properties = new Properties();
            properties.load(fre);
            maxConnections = Integer.parseInt(properties.getProperty("maxConnections"));
            initConnections = Integer.parseInt(properties.getProperty("initConnections"));
            currentConnections = initConnections;
            url = properties.getProperty("URL");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            connectionPool = new ArrayBlockingQueue<>(maxConnections);
            for (int i = 0; i < initConnections; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                connectionPool.add(connection);
            }
            startAutoShrinkingThread();
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

    public synchronized Connection getConnection() throws SQLException, InterruptedException {
        while (connectionPool.isEmpty()) {
            if (currentConnections <= maxConnections && !isExpanding) {
                isExpanding = true;
                Connection connection = DriverManager.getConnection(url, username, password);
                currentConnections++;
                isExpanding = false;
                return connection;
            } else {
                wait();
            }
        }
        return connectionPool.remove();
    }

    private synchronized void shrinkPool(int targetSize) throws SQLException {
        if (targetSize < 0 || targetSize > currentConnections) {
            throw new SQLException("connections is larger than currentConnections");
        }
        int numToClose = currentConnections - targetSize;
        for (int i = 0; i < numToClose; i++) {
            Connection connection = connectionPool.remove();
            connection.close();
            currentConnections--;
        }
    }

    //自动缩容
    private void startAutoShrinkingThread() {
        ThreadPool.SERVICE.submit((Runnable) () -> {
            while (true) {
                try {
                    Thread.sleep(60000);
                    // 每分钟检查一次是否需要缩减连接池大小
                    if (!isExpanding && !isShrinking && currentConnections > initConnections) {
                        isShrinking = true;
                        shrinkPool(initConnections);
                        isShrinking = false;
                    }
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connectionPool.add(connection);
            notifyAll(); // 唤醒等待连接的线程
        } else {
            throw new SQLException("Unable to release connection");
        }
    }


}

