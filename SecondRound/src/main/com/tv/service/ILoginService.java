package tv.service;

import tv.entity.bo.LoginBo;

import java.util.Map;

/**
 * 登录服务
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public interface ILoginService {
    /**
     * 登录
     *
     * @param bo 薄
     * @return {@link Map}<{@link String}, {@link Object}>
     * @throws Exception 异常
     */
    Map<String, Object> login(LoginBo bo) throws Exception;
}
