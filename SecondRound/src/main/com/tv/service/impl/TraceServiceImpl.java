package tv.service.impl;

import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import tv.dao.IDao;
import tv.entity.po.Nft;
import tv.service.ITraceService;
import tv.service.wrapper.NftMarket;
import tv.service.wrapper.NftStorage;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Scope;
import tv.spring.annotate.ServiceLogger;

import java.math.BigInteger;
import java.util.List;

/**
 * 跟踪服务
 *
 * @author 刘家辉
 * @date 2023/05/10
 */

@Component
@Scope("singleton")
@ServiceLogger
public class TraceServiceImpl implements ITraceService {
    @AutoWired
    public IDao dao;

    @Override
    public List<NftStorage.ItemLife> getLife(String cid, NftMarket nftMarket) throws Exception {
        String sql = "select * from nft.nfts where ipfs_cid = ?";
        List<Nft> select = dao.select(sql, new Object[]{cid}, Nft.class);
        Nft nft = select.get(0);
        DynamicArray<NftStorage.ItemLife> nftLife = nftMarket.getNftLife(BigInteger.valueOf(nft.getNftId()));
        return nftLife.getValue();
    }


}
