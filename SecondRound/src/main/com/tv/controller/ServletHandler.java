package tv.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * servlet处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
public interface ServletHandler {
    static int CHECK = 500;

    /**
     * 处理
     * 处理请求
     *
     * @param request 请求
     * @return {@link Object}
     * @throws Exception 异常
     */
    Object handle(HttpServletRequest request) throws Exception;
}
