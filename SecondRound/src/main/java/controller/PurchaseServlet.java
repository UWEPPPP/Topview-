package controller;

import entity.po.User;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
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

/**
 * 购买servlet
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
@WebServlet("/purchase")
@MultipartConfig
public class PurchaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String price = req.getParameter("price");
        User user = CastUtil.cast(req.getSession().getAttribute("user"));
        NftMarket market= CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        try {
            int balance = FactoryService.getPurchaseService().buy(Integer.parseInt(id), Integer.parseInt(price), user.getContract_address(),market);
            if (balance !=Integer.parseInt(user.getBalance())) {
                user.setBalance(String.valueOf(balance));
                req.getSession().setAttribute("user", user);
                resp.setStatus(200);
            } else {
                resp.setStatus(500);
            }
        } catch (SQLException | ClassNotFoundException | ContractException e) {
            throw new RuntimeException(e);
        }
    }
}