package controller;

import com.alibaba.fastjson.JSON;
import factory.FactoryService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
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
        String id = req.getParameter("id");
        NftMarket nftMarket = CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        try {
            List<NftStorage.ItemLife> life = FactoryService.getTraceService().getLife(Integer.parseInt(id), nftMarket);
            String jsonString = JSON.toJSONString(life);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (ContractException e) {
            throw new RuntimeException(e);
        }
    }
}