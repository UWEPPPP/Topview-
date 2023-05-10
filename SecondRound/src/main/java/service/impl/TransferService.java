package service.impl;

import entity.po.Nft;
import entity.po.User;
import factory.FactoryDao;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import service.ITransferService;
import service.wrapper.NftMarket;
import util.Contract;

import java.math.BigInteger;
import java.util.List;

public class TransferService implements ITransferService {
    private TransferService() {
    }

    public static ITransferService getInstance() {
        return TransferServiceHolder.INSTANCE;
    }

    @Override
    public List<User> getTransferList(String owner) throws Exception {
        String sql = "select * from nft.nft_user where contract_address != ?";
        List<User> list = FactoryDao.getDao().select(sql, new Object[]{owner}, User.class);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    public int transfer(String to, String cid, String from, NftMarket nftMarket) throws Exception {
        String sql = "select * from nft.nfts where ipfs_cid = ?";
        List<Nft> list = FactoryDao.getDao().select(sql, new Object[]{cid}, Nft.class);
        Nft nft = list.get(0);
        TransactionReceipt transactionReceipt = nftMarket.tranferNft(BigInteger.valueOf(nft.getNftId()), to);
        String status = transactionReceipt.getStatus();
        if (status.equals(Contract.checkStatus)) {
            String sql1 = "update nft.nfts set owner = ? where ipfs_cid = ?";
            int update = FactoryDao.getDao().insertOrUpdateOrDelete(sql1, new Object[]{to, cid});
            if (update != 0) {
                return 200;
            }
            System.out.println("update error");
        }
        System.out.println("transfer error");
        return 500;
    }

    public static class TransferServiceHolder {
        private static final ITransferService INSTANCE = new TransferService();
    }
}
