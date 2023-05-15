package tv.service.impl;

import tv.entity.po.Nft;
import tv.entity.po.User;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.factory.Factory;
import tv.service.ITransferService;
import tv.service.wrapper.NftMarket;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.Contract;

import java.math.BigInteger;
import java.util.List;

/**
 * 传输服务impl
 *
 * @author 刘家辉
 * @date 2023/05/11
 */

@Component
@Scope("singleton")
public class TransferServiceImpl implements ITransferService {

    @Override
    public List<User> getTransferList(String owner) throws Exception {
        String sql = "select * from nft.nft_user where contract_address != ?";
        List<User> list = Factory.getInstance().iDao().select(sql, new Object[]{owner}, User.class);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    public int transfer(String to, String cid, String from, NftMarket nftMarket) throws Exception {
        String sql = "select * from nft.nfts where ipfs_cid = ?";
        List<Nft> list = Factory.getInstance().iDao().select(sql, new Object[]{cid}, Nft.class);
        Nft nft = list.get(0);
        TransactionReceipt transactionReceipt = nftMarket.tranferNft(BigInteger.valueOf(nft.getNftId()), to);
        String status = transactionReceipt.getStatus();
        if (status.equals(Contract.checkStatus)) {
            String sql1 = "update nft.nfts set owner = ? where ipfs_cid = ?";
            int update = Factory.getInstance().iDao().insertOrUpdateOrDelete(sql1, new Object[]{to, cid});
            if (update != 0) {
                return 200;
            }
            System.out.println("update error");
        }
        System.out.println("transfer error");
        return 500;
    }


}
