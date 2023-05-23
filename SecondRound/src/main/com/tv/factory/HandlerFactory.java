package tv.factory;

import tv.spring.AppConfig;
import tv.spring.ApplicationContext;
import tv.util.CastUtil;

/**
 * 用于从容器中取出对象
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public class HandlerFactory {
    private static final HandlerFactory INSTANCE = new HandlerFactory();
    private final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext(AppConfig.class);

    private HandlerFactory() {
    }

    public static HandlerFactory getInstance() {
        return INSTANCE;
    }

    public <T> T getBean(String beanName) {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean(beanName));
    }
}
