package controller;

import com.alibaba.fastjson.JSON;
import entity.po.Nft;
import entity.po.User;
import factory.FactoryService;
import util.CastUtil;
import util.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * 显示servlet
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
@WebServlet("/display")
@MultipartConfig
public class DisplayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Nft> display = null;
        String choice = req.getParameter("choice");
        User user = CastUtil.cast(req.getSession().getAttribute("user"));
        try {
            if (choice != null) {
                display = FactoryService.getDisplayService().display(choice);
            } else {
                display = FactoryService.getDisplayService().displayByUser(user.getContract_address());
            }
        } catch (Exception e) {
            Logger.logException(Level.WARNING,"显示失败", e);
        }
        String jsonString = JSON.toJSONString(display);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
