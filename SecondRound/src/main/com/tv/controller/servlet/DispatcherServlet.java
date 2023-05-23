package tv.controller.servlet;

import com.alibaba.fastjson.JSON;
import tv.controller.ServletHandler;
import tv.controller.handler.ExceptionHandler;
import tv.factory.HandlerFactory;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求转发器
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@WebServlet(value = "/Servlet/*")
@MultipartConfig

public class DispatcherServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());
        path = path.substring(9);
        ServletHandler handler = HandlerFactory.getInstance().getBean(path + "Handler");
        if (handler == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            Object result = handler.handle(request);
            if (result == null) {
                response.setStatus(200);
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString(result));
        } catch (Exception e) {
            ExceptionHandler.handleAllException(e, response);
        }
    }
}
