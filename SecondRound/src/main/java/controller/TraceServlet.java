package controller;

import com.alibaba.fastjson.JSON;
import factory.FactoryService;
import service.wrapper.NftMarket;
import service.wrapper.NftStorage;
import util.CastUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/trace")
@MultipartConfig
public class TraceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid = req.getParameter("cid");
        NftMarket nftMarket = CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        try {
            List<NftStorage.ItemLife> life = FactoryService.getTraceService().getLife(cid, nftMarket);
            String jsonString = JSON.toJSONString(life);
            System.out.println(jsonString);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}