package service;

import junit.framework.TestCase;
import tv.service.ILoginService;
import tv.service.impl.LoginServiceImpl;

import java.util.Map;

public class LoginServiceImplTest extends TestCase {
    public void test() throws Exception {
        ILoginService ILoginService = new LoginServiceImpl();
        Map<String, Object> login = ILoginService.login("123123", "123123");
        System.out.println(login);
    }
}