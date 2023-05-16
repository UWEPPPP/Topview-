package tv.util;

import tv.spring.Component;
import tv.spring.Scope;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;

/**
 * @author 刘家辉
 * @date 2023/04/06
 */
@Component
@Scope("singleton")
public class ConnectionPool {
    private String url;
    private String username;
    private String password;
    private Integer maxConnections;
    private Integer initConnections;
    private Integer currentConnections;
    private BlockingQueue<Connection> connectionPool;
    private boolean isShrinking = false;
    private boolean isExpanding = false;


    public ConnectionPool() throws ClassNotFoundException {
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
            Logger.logException(Level.WARNING,"连接池初始化异常",e);
        }
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
            Logger.logException(Level.WARNING,"关闭连接异常",e);
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
        Logger.info("一个连接被获取");
        return connectionPool.remove();
    }

    private synchronized void shrinkPool(int targetSize) throws SQLException {
        if (targetSize < 0 || targetSize > currentConnections) {
            Logger.logException(Level.WARNING,"连接池缩容异常",new Exception());
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
                   Logger.logException(Level.WARNING,"连接池自动缩容开启异常",e);
                }
            }
        });
    }

    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connectionPool.add(connection);
            notifyAll(); // 唤醒等待连接的线程
        } else {
            Logger.logException(Level.WARNING,"释放了一个无效连接",new Exception());
        }
        Logger.info("一个连接被释放");
    }


}

