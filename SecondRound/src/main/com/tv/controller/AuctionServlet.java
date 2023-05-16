package tv.controller;

import tv.entity.po.User;
import tv.factory.Factory;
import tv.service.wrapper.NftMarket;
import tv.util.CastUtil;
import tv.util.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 拍卖servlet
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
@WebServlet("/Servlet/auction")
public class AuctionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nfdId = req.getParameter("nftId");
        String bidPrice = req.getParameter("bidPrice");
        NftMarket nftMarket= CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        User user= CastUtil.cast(req.getSession().getAttribute("user"));
        int offer = 0;
        try {
            offer = Factory.getInstance().getAuctionService().offer(Integer.parseInt(nfdId.trim()), Integer.parseInt(bidPrice.trim()), user.getContract_address(), nftMarket);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        resp.setStatus(offer);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid = req.getParameter("cid");
        String duration = req.getParameter("duration");
        String amount = req.getParameter("amount");
        NftMarket user = CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        if (cid == null || duration == null || amount == null) {
            Logger.info("拍卖参数错误");
            resp.setStatus(500);
        }
        try {
            int status = Factory.getInstance().getAuctionService().auctionBegin(cid, duration, amount, user);
            resp.setStatus(status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
