package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.bo.TransferBo;
import tv.entity.po.User;
import tv.service.ITransferService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.CastUtil;
import tv.util.DataBinder;

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
        TransferBo bo= DataBinder.bind(TransferBo.class, request);
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        NftMarket market = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        String contractAddress = user.getContract_address();
        int transfer = transferServiceImpl.transfer(bo, contractAddress, market);
        if(transfer == CHECK) {
            throw new RuntimeException("转增失败");
        }
        return null;
    }
}
