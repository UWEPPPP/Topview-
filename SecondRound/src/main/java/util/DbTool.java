package util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 数据库工具
 *
 * @author 刘家辉
 * @date 2023/05/12
 */
public class DbTool {

    /**
     * 通过反射内省获得单个对象
     *
     * @param resultSet 结果集
     * @param tClass    t类
     * @return {@link T}
     * @throws Exception 异常
     */
    public static <T> T getObject(ResultSet resultSet, Class<T> tClass) throws Exception {
        T object = tClass.newInstance();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
            String colName = resultSetMetaData.getColumnName(i + 1);
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(colName, tClass);
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(object, resultSet.getObject(colName));
        }
        return object;
    }

}
