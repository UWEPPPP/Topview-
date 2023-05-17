package tv.spring;

@ComponentScan({"tv.util","tv.controller.handler","tv.aop","tv.dao.impl","tv.service.impl"})
@Component
@Scope("singleton")
public class AppConfig {
}
