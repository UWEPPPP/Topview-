package service;

import dao.FactoryDAO;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import service.wrapper.NftMarket;
import util.CastUtil;
import util.Contract;

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
    public int buy(int id, int price, String owner) throws SQLException, ClassNotFoundException {
        NftMarket nftMarket = Contract.getNftMarket();
        TransactionReceipt transactionReceipt = nftMarket.buyNft(BigInteger.valueOf(id), BigInteger.valueOf(price));
        String status = transactionReceipt.getStatus();
        String check = "0x0";
        int result = CastUtil.cast(FactoryDAO.getNftDaoInstance().update(owner,id));
        return check.equals(status)|| result != 0?200:500;
    }
}
