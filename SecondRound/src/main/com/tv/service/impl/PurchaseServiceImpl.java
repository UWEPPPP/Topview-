package tv.service.impl;

import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.dao.NftDao;
import tv.service.IPurchaseService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Scope;
import tv.spring.annotate.ServiceLogger;
import tv.util.Contract;

import java.math.BigInteger;

/**
 * 购买服务
 *
 * @author 刘家辉
 * @date 2023/05/06
 */

@Component
@Scope("singleton")
@ServiceLogger
public class PurchaseServiceImpl implements IPurchaseService {

    @AutoWired
    public NftDao nftDaoImpl;

    @Override
    public int buy(int nftId, String owner, NftMarket nftMarket) throws Exception {
        TransactionReceipt transactionReceipt = nftMarket.buyNft(BigInteger.valueOf(nftId));
        String status = transactionReceipt.getStatus();
        int result = nftDaoImpl.updateOwnerBuy(false, owner, nftId);
        if (result == 0 || !status.equals(Contract.checkStatus)) {
            return 500;
        }
        return nftMarket.getBalance().intValue();
    }


}
