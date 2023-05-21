package tv.aop;

import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Scope;
import tv.dao.ConnectionPool;
import tv.util.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * 代理工厂
 * @author 刘家辉
 * @date 2023/04/13
 */
@Component
@Scope("singleton")
public class ProxyFactory {
    @AutoWired
    public ConnectionPool connectionPool;


    /**
     * 创建代理
     *
     * @param target 目标
     * @return {@link Object}
     */
    public Object serviceProxy(Object target) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            Connection connection = null;
            try {
                Logger.info("method: " + method.getName() + ", args: " + Arrays.toString(args));
                connection = connectionPool.getConnection();
                connection.setAutoCommit(false);
                Object invoke = method.invoke(target, args);
                connection.commit();
                Logger.info("method return result: " + invoke);
                return invoke;
            } catch (InvocationTargetException e) {
                Logger.logException(Level.WARNING,"Dao层抛出异常", e);
                connection.rollback();
                return null;
            } finally {
                connectionPool.releaseConnection(connection);
            }
        });
    }
    public Object commonProxy(Object target) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            try {
                Logger.info("method: " + method.getName() + ", args: " + Arrays.toString(args));
                // 调用真实对象的方法
                Object result = method.invoke(target, args);
                // 记录日志
                Logger.info("method return result: " + result);
                return result;
            } catch (InvocationTargetException e) {
                Logger.logException(Level.WARNING,"Service层抛出异常", e);
                return null;
            }
        });
    }
}
