package service;

import java.util.Map;

public interface ILoginService {
    Map<String, Object> login(String name, String password) throws Exception;
}
