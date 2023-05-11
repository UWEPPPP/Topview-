package service.impl;

import factory.FactoryDao;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.IPurchaseService;
import service.wrapper.NftMarket;
import util.Contract;

import java.math.BigInteger;
import java.sql.SQLException;

/**
 * 购买服务
 *
 * @author 刘家辉
 * @date 2023/05/06
 */
public class PurchaseServiceImpl implements IPurchaseService {
    private PurchaseServiceImpl() {
    }

    public static IPurchaseService getInstance() {
        return PurchaseServiceHolder.INSTANCE;
    }

    @Override
    public int buy(int id, String owner, NftMarket nftMarket) throws SQLException, ClassNotFoundException, ContractException {
        TransactionReceipt transactionReceipt = nftMarket.buyNft(BigInteger.valueOf(id));
        String status = transactionReceipt.getStatus();
        String sql = "update nft.nfts set is_sold = false,owner = ? where nftId = ?";
        int result = FactoryDao.getDao().insertOrUpdateOrDelete(sql, new Object[]{owner, id});
        if (result == 0 || !status.equals(Contract.checkStatus)) {
            return 500;
        }
        return nftMarket.getBalance().intValue();
    }

    public static class PurchaseServiceHolder {
        private static final IPurchaseService INSTANCE = new PurchaseServiceImpl();
    }
}
