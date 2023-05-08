package service;

import dao.FactoryDao;
import entity.po.Nft;
import entity.po.User;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import service.wrapper.NftMarket;

import java.math.BigInteger;
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

    public List<User> getTransferList(String owner) throws Exception {
        String sql="select * from nft.nfts where owner != ?";
       List<User> list = FactoryDao.getDao().select(sql,new Object[]{owner},User.class);
         if(list.size()==0){
              return null;
         }
            return list;
    }
    public int transfer(String to, String cid, String from,NftMarket nftMarket) throws Exception {
        String sql="select * from nft.nfts where ipfs_cid = ?";
        List<Nft> list = FactoryDao.getDao().select(sql, new Object[]{cid}, Nft.class);
        Nft nft = list.get(0);
        TransactionReceipt transactionReceipt = nftMarket.safeTransferFrom(from, to, BigInteger.valueOf(nft.getNftId()), BigInteger.valueOf(1), "0x".getBytes());
        String status = transactionReceipt.getStatus();
        String check = "0x0";
        if (status.equals(check)) {
            String sql1 = "update nft.nfts set owner = ? where ipfs_cid = ?";
            int update = FactoryDao.getDao().insertOrUpdateOrDelete(sql, new Object[]{to, cid});
            if(update!=0){
                return 200;
            }
            System.out.println("update error");
        }
        System.out.println("transfer error");
        return 500;
    }
}
