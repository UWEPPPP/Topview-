package tv.service;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.service.wrapper.NftMarket;

import java.sql.SQLException;

/**
 * 购买服务
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public interface IPurchaseService {
    /**
     * 买
     *
     * @param id        id
     * @param owner     老板
     * @param nftMarket 非功能性测试市场
     * @return int
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     * @throws ContractException      合同例外
     * @throws InterruptedException   中断异常
     */
    int buy(int id, String owner, NftMarket nftMarket) throws Exception;
}
