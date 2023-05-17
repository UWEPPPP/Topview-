package tv.controller.servlet;

import com.alibaba.fastjson.JSON;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.controller.ExceptionHandler;
import tv.controller.ServletHandler;
import tv.factory.HandlerFactory;
import tv.util.Logger;
import tv.util.exception.InputException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

/**
 * dispatcher servlet
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@WebServlet( value = "/Servlet/*")
@MultipartConfig

public class DispatcherServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());
        path=path.substring(9);
        ServletHandler handler = HandlerFactory.getInstance().getBean(path+"Handler");
        if (handler == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            Object result = handler.handle(request);
            if(result == null) {
              response.setStatus(200);
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString(result));
        } catch (NullPointerException e){
            ExceptionHandler.handleNullPointException(e, response);
        }catch (InputException e) {
            ExceptionHandler.handleInputException(e, response);
        }catch (ContractException e) {
            ExceptionHandler.handleContractException(e, response);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, response);
        }
    }



    private void handleException(Exception e, HttpServletResponse response) {
        // 发送错误信息到客户端
        response.setStatus(500);
        response.setContentType("application/json");
        try {
            response.getWriter().write("error message");
        } catch (IOException ex) {
            Logger.logException(Level.SEVERE, "The top exception", ex);
        }
    }
}
