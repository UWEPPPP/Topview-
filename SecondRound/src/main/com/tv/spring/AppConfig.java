package tv.spring;

import tv.spring.annotate.Component;
import tv.spring.annotate.ComponentScan;
import tv.spring.annotate.Scope;

/**
 * 扫包配置
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
@ComponentScan({"tv.util","tv.controller.handler","tv.aop","tv.dao.impl","tv.service.impl"})
@Component
@Scope("singleton")
public class AppConfig {
}
