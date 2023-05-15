package tv.controller;

import tv.entity.po.User;
import tv.factory.Factory;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.service.wrapper.NftMarket;
import tv.util.CastUtil;
import tv.util.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

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
        if(id == null){
            Logger.info("id获取失败");
            resp.setStatus(500);
            return;
        }
        User user = CastUtil.cast(req.getSession().getAttribute("user"));
        NftMarket market = CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        try {
            int balance = Factory.getInstance().getPurchaseService().buy(Integer.parseInt(id), user.getContract_address(), market);
            if (balance != Integer.parseInt(user.getBalance())) {
                user.setBalance(String.valueOf(balance));
                req.getSession().setAttribute("user", user);
                resp.setStatus(200);
            } else {
                resp.setStatus(500);
            }
        } catch (SQLException | ClassNotFoundException | ContractException | InterruptedException e) {
            Logger.logException(Level.WARNING,"购买失败", e);
        }
    }
}