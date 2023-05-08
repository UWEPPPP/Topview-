package controller;

import com.alibaba.fastjson.JSON;
import entity.po.User;
import service.FactoryService;
import service.wrapper.NftMarket;
import util.CastUtil;

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
 * 转移servlet
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
@WebServlet("/transfer")
@MultipartConfig
public class TransferServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = CastUtil.cast(req.getSession().getAttribute("user"));
        try {
            List<User> transferList = FactoryService.getTransferService().getTransferList(user.getContractAddress());
            String jsonString = JSON.toJSONString(transferList);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recipientAddress = req.getParameter("recipientAddress");
        String collectionItem = req.getParameter("collectionItem");
        User user = (User) req.getSession().getAttribute("user");
        NftMarket market=CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        String contractAddress = user.getContractAddress();
        System.out.println(recipientAddress);
        System.out.println(collectionItem);
        try {
            int transfer = FactoryService.getTransferService().transfer(recipientAddress, collectionItem, contractAddress,market);
            resp.setStatus(transfer);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}