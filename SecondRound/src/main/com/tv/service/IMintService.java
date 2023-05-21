package tv.service;

import tv.entity.bo.MintNftBo;
import tv.entity.po.Nft;
import tv.service.wrapper.NftMarket;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 情报服务
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public interface IMintService {
    int mint(MintNftBo bo, NftMarket nftMarket) throws IOException, SQLException, ClassNotFoundException, InterruptedException;
}
