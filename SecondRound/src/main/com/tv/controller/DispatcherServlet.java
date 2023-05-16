package tv.controller;

import tv.factory.Factory;
import tv.util.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * dispatcher servlet
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@WebServlet("/Servlet/*")
public class DispatcherServlet extends HttpServlet {
    private final String[] servlets = {"/login",
            "/register",
            "/auction",
            "/displayForAuction",
            "/display",
            "/info",
            "/mint",
            "/purchase",
            "/search",
            "/trace",
            "/transfer",
            "/upAndDown"};
    private Map<String, Object> handlers = new HashMap<>();

    @Override
    public void init() {
            handlers.put("/login", Factory.getInstance().getLoginService());
            handlers.put("/register", Factory.getInstance().getRegisterService());
            handlers.put("/auction", Factory.getInstance().getAuctionService());
            handlers.put("/displayForAuction", Factory.getInstance().getDisplayForAuctionService());
            handlers.put("/display", Factory.getInstance().getDisplayService());
            handlers.put("/info", Factory.getInstance().getInfoService());
            handlers.put("/mint", Factory.getInstance().getMintService());
            handlers.put("/purchase", Factory.getInstance().getPurchaseService());
            handlers.put("/search", Factory.getInstance().getSearchService());
            handlers.put("/trace", Factory.getInstance().getTraceService());
            handlers.put("/transfer", Factory.getInstance().getTransferService());
            handlers.put("/upAndDown", Factory.getInstance().getUpAndDownService());
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 根据路径转发到不同的Servlet
            for(String url:servlets) {
                if(request.getRequestURI().equals(url)) {
                    request.getRequestDispatcher("/Servlet"+url).forward(request, response);
                }
            }
        } catch (Exception e) {
            // 处理全局异常
            handleException(e, response);
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
