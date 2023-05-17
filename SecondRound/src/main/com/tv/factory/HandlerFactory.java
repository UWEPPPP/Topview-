package tv.factory;

import tv.spring.AppConfig;
import tv.spring.ApplicationContext;
import tv.util.CastUtil;

/**
 * 工厂
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public class HandlerFactory {
    private HandlerFactory() {
        APPLICATION_CONTEXT = new ApplicationContext(AppConfig.class);
    }
    public static HandlerFactory getInstance() {
        return FactoryHolder.INSTANCE;
    }
    private static class FactoryHolder {
        private static final HandlerFactory INSTANCE = new HandlerFactory();
    }
    private  final ApplicationContext APPLICATION_CONTEXT;
    public <T> T getBean(String beanName) {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean(beanName));
    }
}
