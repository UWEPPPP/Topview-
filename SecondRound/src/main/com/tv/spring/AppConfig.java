package tv.spring;

import tv.spring.Component;
import tv.spring.ComponentScan;
import tv.spring.Scope;

@ComponentScan({"tv.spring","tv.service.impl","tv.dao.impl","tv.util"})
@Component
@Scope("singleton")
public class AppConfig {
}
