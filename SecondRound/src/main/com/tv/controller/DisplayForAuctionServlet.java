package tv.controller;

import com.alibaba.fastjson.JSON;
import tv.entity.dto.AuctionDto;
import tv.factory.Factory;
import tv.util.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

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
            List<AuctionDto> auctions = Factory.getInstance().getAuctionService().showAuction();
            String jsonString = JSON.toJSONString(auctions);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (Exception e) {
            Logger.logException(Level.WARNING,"显示拍卖失败", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
