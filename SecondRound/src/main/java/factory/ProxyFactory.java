package factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 刘家辉
 * @date 2023/04/13
 */
public class ProxyFactory {
    private static final Logger LOGGER = Logger.getLogger("ProxyFactory");

    /**
     * 创建代理
     *
     * @param target 目标
     * @return {@link Object}
     */
    public static Object createProxy(Object target) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            try {
                return method.invoke(target, args);
            } catch (InvocationTargetException e) {
                LOGGER.log(Level.INFO, "服务层抛出异常", e);
                return null;
            }
        });
    }
}
