package tv.controller;

import tv.entity.po.Nft;
import tv.entity.po.User;
import tv.service.IMintService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.CommonLogger;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.CastUtil;
import tv.util.exception.InputException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 铸造处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
@Component
@Scope("singleton")
@CommonLogger
public class MintHandler implements ServletHandler{
    @AutoWired
    public IMintService mintServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws InputException, ServletException, IOException, SQLException, ClassNotFoundException, InterruptedException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String type = request.getParameter("type");
        String price = request.getParameter("price");
        Part file = request.getPart("file");
        if (name == null || name.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                type == null || type.trim().isEmpty() ||
                price == null || price.trim().isEmpty() ||
                file == null) {
            throw new InputException("铸造信息输入为空");
        }
        User user = CastUtil.cast(request.getSession().getAttribute("user"));
        NftMarket market = CastUtil.cast(request.getSession().getAttribute("nftMarket"));
        String contractAddress = user.getContract_address();
        Nft nft = new Nft();
        nft.setName(name);
        nft.setDescription(description);
        nft.setType(type);
        nft.setPrice(Integer.parseInt(price));
        nft.setOwner(contractAddress);
        int result = mintServiceImpl.mint(nft, file, market);
    //   if( result ==CHECK){
    //       throw new InputException("铸造失败");
    //   }
        return "success";
    }
}
