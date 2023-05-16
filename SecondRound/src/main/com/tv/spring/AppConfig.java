package tv.spring;

import tv.spring.Component;
import tv.spring.ComponentScan;
import tv.spring.Scope;

@ComponentScan({"tv.util","tv.aop","tv.dao.impl","tv.service.impl"})
@Component
@Scope("singleton")
public class AppConfig {
}
