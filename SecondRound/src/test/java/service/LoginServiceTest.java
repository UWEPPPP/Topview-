package service;

import junit.framework.TestCase;
import service.impl.LoginService;

import java.util.Map;

public class LoginServiceTest extends TestCase {
    public void test() throws Exception {
        ILoginService ILoginService = LoginService.getInstance();
        Map<String, Object> login = ILoginService.login("123123", "123123");
        System.out.println(login);
    }
}