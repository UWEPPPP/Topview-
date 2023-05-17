package tv.controller;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.entity.po.User;
import tv.service.IInfoService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.CastUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 信息显示处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@CommonLogger
public class InfoShowHandler implements ServletHandler {
    @AutoWired
    public IInfoService infoServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws ContractException, SQLException, ClassNotFoundException {
        HttpSession session = request.getSession();
        User user = CastUtil.cast(session.getAttribute("user"));
        NftMarket nftMarket = CastUtil.cast(session.getAttribute("nftMarket"));
        String profile = user.getProfile();
        int balance= infoServiceImpl.changeBalance(nftMarket);
        Map<String, Object> message = new HashMap<>(3);
        message.put("image", profile);
        message.put("name", user.getName());
        message.put("balance",balance);
        return message;
    }
}
