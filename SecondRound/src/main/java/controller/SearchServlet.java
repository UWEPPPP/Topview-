package controller;

import com.alibaba.fastjson.JSON;
import entity.po.Nft;
import service.FactoryService;

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
 * 搜索servlet
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
@WebServlet("/search")
@MultipartConfig
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">>>>");
        String type = req.getParameter("searchType");
        String searchText = req.getParameter("searchText");
        if (type == null || searchText == null) {
            resp.setStatus(500);
            return;
        }
        try {
            List<Nft> search = FactoryService.getSearchService().search(type, searchText);
            String jsonString = JSON.toJSONString(search);
            System.out.println(jsonString);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}