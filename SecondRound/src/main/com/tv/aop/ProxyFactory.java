package tv.aop;

import tv.spring.annotate.Transaction;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Scope;
import tv.util.ConnectionPool;
import tv.util.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * 代理工厂
 *
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
    public Object securityProxy(Object target) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            Logger.info("method: " + method.getName() + ", args: " + Arrays.toString(args));
            if (method.isAnnotationPresent(Transaction.class)) {
                Connection connection = null;
                try {
                    connection = connectionPool.getConnection();
                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    connection.setAutoCommit(false);
                    Object invoke = method.invoke(target, args);
                    connection.commit();
                    Logger.info("method return result: " + invoke);
                    return invoke;
                } catch (InvocationTargetException e) {
                    Logger.logException(Level.WARNING, "service层抛出异常", e);
                    connection.rollback();
                    return null;
                } finally {
                    connectionPool.releaseConnection(connection);
                }
            } else {
                try {
                    Object invoke = method.invoke(target, args);
                    Logger.info("method return result: " + invoke);
                    return invoke;
                } catch (InvocationTargetException e) {
                    Logger.logException(Level.WARNING, "service层抛出异常", e);
                    return null;
                }
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
                Logger.logException(Level.WARNING, "抛出异常", e);
                return null;
            }
        });
    }
}
