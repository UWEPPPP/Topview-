package tv.controller.listener;

import tv.factory.HandlerFactory;
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
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Object bean = HandlerFactory.getInstance();
        Contract.setAdmin(Contract.setNftMarket("2e994eec1cb4e3cd934829be623a5761fe6bafdd8f295227b118314e1bbf13bf"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
