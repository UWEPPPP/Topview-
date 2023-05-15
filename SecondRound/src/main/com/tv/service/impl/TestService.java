package tv.service.impl;

import tv.spring.AppConfig;
import tv.spring.AutoWired;
import tv.spring.Component;
import tv.spring.Scope;

/**
 * 测试服务
 *
 * @author 刘家辉
 * @date 2023/05/15
 */
@Component
@Scope("prototype")
public class TestService {
    @AutoWired
    public AppConfig appConfig;
    public void test() {
        System.out.println("spring.test");
    }
}
