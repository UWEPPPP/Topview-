package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.po.User;
import tv.service.ITransferService;
import tv.service.wrapper.NftMarket;
import tv.spring.*;
import tv.util.CastUtil;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletRequest;

/**
 * 转变处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class TransferHandler implements ServletHandler {
    @AutoWired
    public ITransferService transferServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        String recipientAddress = request.getParameter("recipientAddress");
        String collectionItem = request.getParameter("collectionItem");
        if(recipientAddress == null || collectionItem == null) {
            throw new InputException("输入为空");
        }
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        NftMarket market = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        String contractAddress = user.getContract_address();
        int transfer = transferServiceImpl.transfer(recipientAddress, collectionItem, contractAddress, market);
        if(transfer == CHECK) {
            throw new InputException("转增失败");
        }
        return null;
    }
}
