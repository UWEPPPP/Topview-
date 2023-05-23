package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.bo.MintNftBo;
import tv.entity.po.User;
import tv.service.IMintService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.CastUtil;
import tv.util.DataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * 铸造处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@Component
@Scope("singleton")
@Controller
public class MintHandler implements ServletHandler {
    @AutoWired
    public IMintService mintServiceImpl;

    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        NftMarket market = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        String contractAddress = user.getContract_address();
        MintNftBo bo = DataBinder.bind(MintNftBo.class, request);
        bo.setOwner(contractAddress);
        int result = mintServiceImpl.mint(bo, market);
        if (result == CHECK) {
            throw new RuntimeException("铸造失败,内部无报错");
        }
        return null;
    }
}
