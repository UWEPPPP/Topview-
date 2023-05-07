package service;

import dao.FactoryDAO;
import entity.po.Nft;
import entity.po.User;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import service.wrapper.NftMarket;
import util.Contract;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class TransferService {
    private TransferService() {
    }
    public static class TransferServiceHolder {
        private static final TransferService INSTANCE = new TransferService();
    }

    public static TransferService getInstance() {
        return TransferServiceHolder.INSTANCE;
    }

    public void transfer(String from, String to, int tokenId) {}
    public List<User> getTransferList(String owner) throws SQLException, ClassNotFoundException {
        return FactoryDAO.getUserDaoInstance().selectToTransfer(owner);
    }
    public int transfer(String to, String cid, String from) throws SQLException, ClassNotFoundException {
        List<Nft> list = FactoryDAO.getNftDaoInstance().selectByCid(cid);
        Nft nft = list.get(0);
        NftMarket nftMarket = Contract.getNftMarket();
        TransactionReceipt transactionReceipt = nftMarket.safeTransferFrom(from, to, nft.getTokenId(), BigInteger.valueOf(1), "0x".getBytes());
        String status = transactionReceipt.getStatus();
        String check = "0x0";
        if (status.equals(check)) {
            int update = (int)FactoryDAO.getNftDaoInstance().update(to, nft.getTokenId().intValue());
            if(update!=0){
                return 200;
            }
            System.out.println("update error");
        }
        System.out.println("transfer error");
        return 500;
    }
}
