package tv.dao.impl;

import tv.spring.annotate.AutoWired;
import tv.spring.annotate.CommonLogger;
import tv.spring.annotate.Component;
import tv.spring.annotate.Scope;
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
public class DaoImpl implements tv.dao.Dao {
    @AutoWired
    public ConnectionPool connectionPool;

    @Override
    public int insertOrUpdateOrDelete(String sql, Object[] objects) throws SQLException, InterruptedException {
        System.out.println("sql:" + sql);
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        int result = preparedStatement.executeUpdate();
        connectionPool.releaseConnection(connection);
        ConnectionPool.close(preparedStatement, null);
        return result;
    }

    @Override
    public <T> List<T> select(String sql, Object[] objects, Class<T> tClass) throws Exception {
        System.out.println("sql:" + sql);
        Connection connection = connectionPool.getConnection();
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
        connectionPool.releaseConnection(connection);
        ConnectionPool.close(preparedStatement, resultSet);
        return list;
    }

}
