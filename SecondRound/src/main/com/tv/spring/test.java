package tv.spring;

import tv.factory.Factory;
import tv.service.impl.TestService;
import tv.util.CastUtil;

@Component
@Scope("singleton")
public class test {
    @AutoWired
    private TestService testService;

    public static void main(String[] args) {
     ApplicationContext   APPLICATION_CONTEXT = new ApplicationContext(AppConfig.class);
     System.out.println(APPLICATION_CONTEXT.getBean("testService"));
    }
}
