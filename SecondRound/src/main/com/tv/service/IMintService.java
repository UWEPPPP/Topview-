package tv.service;

import tv.entity.bo.MintNftBo;
import tv.entity.po.Nft;
import tv.service.wrapper.NftMarket;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 铸造服务
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public interface IMintService {
    /**
     * 铸造
     *
     * @param bo        薄
     * @param nftMarket 非功能性测试市场
     * @return int
     * @throws IOException            ioexception
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     * @throws InterruptedException   中断异常
     */
    int mint(MintNftBo bo, NftMarket nftMarket) throws IOException, SQLException, ClassNotFoundException, InterruptedException;
}
