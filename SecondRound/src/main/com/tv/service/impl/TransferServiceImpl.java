package tv.service.impl;

import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.dao.IDao;
import tv.entity.bo.TransferBo;
import tv.entity.po.Nft;
import tv.entity.po.User;
import tv.service.ITransferService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.Component;
import tv.spring.Scope;
import tv.spring.ServiceLogger;
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
@ServiceLogger
public class TransferServiceImpl implements ITransferService {
    @AutoWired
    public IDao dao;
    @Override
    public List<User> getTransferList(String owner) throws Exception {
        String sql = "select * from nft.nft_user where contract_address != ?";
        List<User> list = dao.select(sql, new Object[]{owner}, User.class);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    public int transfer(TransferBo bo, String from, NftMarket nftMarket) throws Exception {
        String sql = "select * from nft.nfts where ipfs_cid = ?";
        List<Nft> list = dao.select(sql, new Object[]{bo.getCollectionItem()}, Nft.class);
        Nft nft = list.get(0);
        TransactionReceipt transactionReceipt = nftMarket.tranferNft(BigInteger.valueOf(nft.getNftId()), bo.getRecipientAddress());
        String status = transactionReceipt.getStatus();
        if (status.equals(Contract.checkStatus)) {
            String sql1 = "update nft.nfts set owner = ? where ipfs_cid = ?";
            int update = dao.insertOrUpdateOrDelete(sql1, new Object[]{bo.getRecipientAddress(),bo.getCollectionItem()});
            if (update != 0) {
                return 200;
            }
            tv.util.Logger.info("转增成功");
        }
        tv.util.Logger.warning("转增失败");
        return 500;
    }


}
