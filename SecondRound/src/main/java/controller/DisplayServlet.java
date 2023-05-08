package controller;

import com.alibaba.fastjson.JSON;
import entity.po.Nft;
import entity.po.User;
import service.FactoryService;
import util.CastUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
        List<Nft> display;
        String choice = req.getParameter("choice");
        User user = CastUtil.cast(req.getSession().getAttribute("user"));
        try {
            if(choice!=null) {
                display = FactoryService.getDisplayService().display();
            }else {
                display = FactoryService.getDisplayService().displayByUser(user.getContract_address());
            }
            System.out.println(display+"????");
        } catch (Exception e) {
            throw new RuntimeException(e);
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
