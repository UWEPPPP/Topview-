package service;

import dao.FactoryDao;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.wrapper.NftMarket;
import util.CastUtil;

import java.math.BigInteger;
import java.sql.SQLException;

/**
 * 购买服务
 *
 * @author 刘家辉
 * @date 2023/05/06
 */
public class PurchaseService {
    private PurchaseService() {
    }
    public static class PurchaseServiceHolder {
        private static final PurchaseService INSTANCE = new PurchaseService();
    }

    public static PurchaseService getInstance() {
        return PurchaseServiceHolder.INSTANCE;
    }
    public int buy(int id, int price, String owner,NftMarket nftMarket) throws SQLException, ClassNotFoundException, ContractException {
        TransactionReceipt transactionReceipt = nftMarket.buyNft(BigInteger.valueOf(id), BigInteger.valueOf(price));
        String status = transactionReceipt.getStatus();
        String check = "0x0";
        String sql ="update nft.nfts set is_sold = false,owner = ? where nftId = ?";
        int result = FactoryDao.getDao().insertOrUpdateOrDelete(sql, new Object[]{owner, id});
        if(result == 0 || !status.equals(check)){
            return 500;
        }
        return nftMarket.getBalance().intValue();
    }
}
