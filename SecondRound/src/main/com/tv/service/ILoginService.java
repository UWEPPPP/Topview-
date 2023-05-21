package tv.service;

import tv.entity.bo.LoginBo;

import java.util.Map;

/**
 * ilogin服务
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public interface ILoginService {
    Map<String, Object> login(LoginBo bo) throws Exception;
}
