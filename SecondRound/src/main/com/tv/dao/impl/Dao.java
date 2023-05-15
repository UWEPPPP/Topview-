package tv.dao.impl;

import tv.dao.IDao;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.ConnectionPool;
import tv.util.DbTool;

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

@Component
@Scope("singleton")
public class Dao implements IDao {

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

}
