package controller;

import com.alibaba.fastjson.JSON;
import entity.dto.AuctionDto;
import factory.FactoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 显示拍卖servlet
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
@WebServlet("/displayForAuction")
public class DisplayForAuctionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<AuctionDto> auctions = FactoryService.getAuctionService().showAuction();
            String jsonString = JSON.toJSONString(auctions);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}