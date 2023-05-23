package tv.controller.handler;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.controller.ServletHandler;
import tv.entity.po.User;
import tv.service.IInfoService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
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
@Controller
public class InfoShowHandler implements ServletHandler {
    @AutoWired
    public IInfoService infoServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws ContractException, SQLException, ClassNotFoundException {
        HttpSession session = request.getSession();
        User user = CastUtil.cast(session.getAttribute("user"));
        NftMarket nftMarket = CastUtil.cast(session.getAttribute("nftMarket"));
        String profile = user.getProfile();
        int balance = infoServiceImpl.changeBalance(nftMarket);
        Map<String, Object> message = new HashMap<>(3);
        message.put("image", profile);
        message.put("name", user.getName());
        message.put("balance", balance);
        return message;
    }
}
