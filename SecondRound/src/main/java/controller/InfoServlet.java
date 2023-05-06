package controller;

import entity.po.User;
import service.FactoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * 信息servlet
 *
 * @author 刘家辉
 * @date 2023/04/30
 */
@WebServlet("/info")
@MultipartConfig
public class InfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("name");
        System.out.println(parameter);
        User user = (User) req.getSession().getAttribute("user");
        if(parameter==null){
            resp.setStatus(500);
            return;
        }
        try {
            String name = FactoryService.getInfoService().changeInfo(parameter, null, user.getContractAddress());
            user.setName(name);
            req.getSession().setAttribute("user",user);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part= req.getPart("avatar");
        User user = (User) req.getSession().getAttribute("user");
        if (part == null) {
                resp.setStatus(500);
                return;
        }
        try {
            String profile = FactoryService.getInfoService().changeInfo(null, part, user.getContractAddress());
            user.setProfile(profile);
            req.getSession().setAttribute("user",user);
            resp.setStatus((200));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
