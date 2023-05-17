package tv.controller.listener;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import tv.factory.HandlerFactory;
import tv.service.wrapper.NftMarket;
import tv.util.Contract;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 侦听器
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@WebListener(value = "init")
public class Listener  implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Object bean = HandlerFactory.getInstance();
        Contract.setAdmin(Contract.setNftMarket("a7dc106e8ce088d92d00251147ab53a5822b91c9a11206b10363ba2304229580"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
