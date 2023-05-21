package tv.factory;

import org.bouncycastle.crypto.tls.PRFAlgorithm;
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
    private HandlerFactory() {
    }
    public static HandlerFactory getInstance() {
        return INSTANCE;
    }
    private static final HandlerFactory INSTANCE = new HandlerFactory();
    private  final ApplicationContext APPLICATION_CONTEXT= new ApplicationContext(AppConfig.class);
    public <T> T getBean(String beanName) {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean(beanName));
    }
}
