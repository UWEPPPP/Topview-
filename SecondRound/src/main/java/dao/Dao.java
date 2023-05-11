package dao;

import util.ConnectionPool;
import util.DbTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 刀
 *
 * @author 刘家辉
 * @date 2023/05/08
 */
public class Dao implements IDao {
    private Dao() {

    }

    public static Dao getInstance() {
        return DaoHolder.INSTANCE;
    }

    @Override
    public int insertOrUpdateOrDelete(String sql, Object[] objects) throws ClassNotFoundException, SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        int result = preparedStatement.executeUpdate();
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.close(preparedStatement, null);
        return result;
    }

    @Override
    public <T> List<T> select(String sql, Object[] objects, Class<T> tClass) throws Exception {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T object = DbTool.getObject(resultSet, tClass);
            list.add(object);
        }
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.close(preparedStatement, resultSet);
        return list;
    }

    public static class DaoHolder {
        private static final Dao INSTANCE = new Dao();
    }
}
