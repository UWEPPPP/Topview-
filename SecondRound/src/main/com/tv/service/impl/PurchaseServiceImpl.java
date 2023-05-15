package tv.service.impl;

import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.factory.Factory;
import tv.service.IPurchaseService;
import tv.service.wrapper.NftMarket;
import tv.spring.Component;
import tv.spring.Scope;
import tv.spring.Service;
import tv.util.Contract;

import java.math.BigInteger;
import java.sql.SQLException;

/**
 * 购买服务
 *
 * @author 刘家辉
 * @date 2023/05/06
 */

@Component
@Scope("singleton")
@Service
public class PurchaseServiceImpl implements IPurchaseService {


    @Override
    public int buy(int id, String owner, NftMarket nftMarket) throws SQLException, ClassNotFoundException, ContractException, InterruptedException {
        TransactionReceipt transactionReceipt = nftMarket.buyNft(BigInteger.valueOf(id));
        String status = transactionReceipt.getStatus();
        String sql = "update nft.nfts set is_sold = false,owner = ? where nftId = ?";
        int result = Factory.getInstance().iDao().insertOrUpdateOrDelete(sql, new Object[]{owner, id});
        if (result == 0 || !status.equals(Contract.checkStatus)) {
            return 500;
        }
        return nftMarket.getBalance().intValue();
    }


}
