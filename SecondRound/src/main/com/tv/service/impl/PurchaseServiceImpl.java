package tv.service.impl;

import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.dao.NftDao;
import tv.service.IPurchaseService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.*;
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
@SecurityLogger
@Service
public class PurchaseServiceImpl implements IPurchaseService {

    @AutoWired
    public NftDao nftDaoImpl;

    @Override
    public int buy(int nftId, String owner, NftMarket nftMarket) throws Exception {
        int result = nftDaoImpl.updateOwnerBuy(false, owner, nftId);
        if (result != 0) {
            TransactionReceipt transactionReceipt = nftMarket.buyNft(BigInteger.valueOf(nftId));
            String status = transactionReceipt.getStatus();
            if (status.equals(Contract.checkStatus)) {
                return nftMarket.getBalance().intValue();
            }
        }
        throw new RuntimeException("购买失败");
    }


}
