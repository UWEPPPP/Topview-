package tv.controller;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.entity.po.User;
import tv.service.IPurchaseService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.CastUtil;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * 购买处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@Component
@Scope("singleton")
@CommonLogger
public class PurchaseHandler implements ServletHandler {
    @AutoWired
    public IPurchaseService purchaseServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws InputException, ContractException, SQLException, ClassNotFoundException, InterruptedException {
        String id = request.getParameter("id");
        if (id == null) {
            throw new InputException("输入为空");
        }
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        NftMarket market = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        int balance = purchaseServiceImpl.buy(Integer.parseInt(id), user.getContract_address(), market);
        if (balance != Integer.parseInt(user.getBalance())) {
            user.setBalance(String.valueOf(balance));
            request.getSession().setAttribute("user", user);
            return "success";
        }
        throw new InputException("购买失败");
    }
}
