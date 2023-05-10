package controller;

import factory.FactoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 非功能性测试servlet
 *
 * @author 刘家辉
 * @date 2023/05/07
 */
@WebServlet("/upAndDown")
@MultipartConfig
public class UpAndDownNftServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String cid = req.getParameter("cid");
        String onSale = req.getParameter("onSale");
        try {
            int status = FactoryService.getInfoService().upAndDown(cid, onSale);
            resp.setStatus(status);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}