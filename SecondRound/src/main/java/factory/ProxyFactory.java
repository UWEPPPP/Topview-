package factory;

import util.ConnectionPool;
import util.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * @author 刘家辉
 * @date 2023/04/13
 */
public class ProxyFactory {
    /**
     * 创建代理
     *
     * @param target 目标
     * @return {@link Object}
     */
    public static Object daoProxy(Object target) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            Connection connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                connection.setAutoCommit(false);
                Object invoke = method.invoke(target, args);
                connection.commit();
                return invoke;
            } catch (InvocationTargetException e) {
                Logger.logException(Level.WARNING,"Dao层抛出异常", e);
                connection.rollback();
                return null;
            } finally {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        });
    }
    public static Object serviceProxy(Object target) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            try {
                util.Logger.info("method: " + method.getName() + ", args: " + Arrays.toString(args));
                // 调用真实对象的方法
                Object result = method.invoke(target, args);
                // 记录日志
                util.Logger.info("method return result: " + result);
                return result;
            } catch (InvocationTargetException e) {
                util.Logger.logException(Level.WARNING,"Service层抛出异常", e);
                return null;
            }
        });
    }
}
