package tv.service.impl;

import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.dao.NftDao;
import tv.dao.UserDao;
import tv.entity.bo.TransferBo;
import tv.entity.po.Nft;
import tv.entity.po.User;
import tv.service.ITransferService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.*;
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
@Service
public class TransferServiceImpl implements ITransferService {
    @AutoWired
    public NftDao nftDaoImpl;
    @AutoWired
    public UserDao userDaoImpl;

    @Override
    public List<User> getTransferList(String owner) throws Exception {
        List<User> list = userDaoImpl.selectForList(owner);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    @Transaction
    public int transfer(TransferBo bo, String from, NftMarket nftMarket) throws Exception {
        List<Nft> list = nftDaoImpl.selectByCid(bo.getCid());
        Nft nft = list.get(0);
        int update = nftDaoImpl.updateOwnerByCid(bo.getRecipientAddress(), bo.getCid());
       if (update!=0) {
            TransactionReceipt transactionReceipt = nftMarket.tranferNft(BigInteger.valueOf(nft.getNftId()), bo.getRecipientAddress());
            String status = transactionReceipt.getStatus();
            if (status.equals(Contract.checkStatus)) {
                return 200;
            }
        }
        throw new RuntimeException("转增失败");
    }


}
