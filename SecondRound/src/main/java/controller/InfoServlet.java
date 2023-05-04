package controller;

import entity.po.User;
import service.FactoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 信息servlet
 *
 * @author 刘家辉
 * @date 2023/04/30
 */
public class InfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("new-name");
        Part part= req.getPart("new-avatar");
        User user = (User) req.getSession().getAttribute("user");
        if(parameter==null||parameter.trim().isEmpty()||part==null){
            resp.setStatus(500);
            return;
        }
        try {
            int status = FactoryService.getInfoService().changeInfo(parameter, part,user.getContractAddress());
            resp.setStatus(status);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
