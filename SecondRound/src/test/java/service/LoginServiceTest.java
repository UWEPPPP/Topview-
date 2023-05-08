package service;

import junit.framework.TestCase;

import java.util.Map;

public class LoginServiceTest extends TestCase {
     public void test() throws Exception {
            LoginService loginService = LoginService.getInstance();
         Map<String, Object> login = loginService.login("123123", "123123");
         System.out.println(login);
     }
}