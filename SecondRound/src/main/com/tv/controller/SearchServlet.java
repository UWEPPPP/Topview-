package tv.controller;

import com.alibaba.fastjson.JSON;
import tv.entity.po.Nft;
import tv.factory.Factory;
import tv.util.Logger;

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
 * 搜索servlet
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
@WebServlet("/Servlet/search")
@MultipartConfig
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("searchType");
        String searchText = req.getParameter("searchText");
        if (type == null || searchText == null) {
            Logger.info("搜索参数异常");
            resp.setStatus(500);
            return;
        }
        try {
            List<Nft> search = Factory.getInstance().getSearchService().search(type, searchText);
            String jsonString = JSON.toJSONString(search);
            System.out.println(jsonString);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (Exception e) {
            Logger.logException(Level.WARNING,"搜索失败", e);
        }
    }
}