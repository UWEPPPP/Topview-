package tv.service.impl;

import tv.entity.po.Nft;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import tv.factory.Factory;
import tv.service.ITraceService;
import tv.service.wrapper.NftMarket;
import tv.service.wrapper.NftStorage;
import tv.spring.Component;
import tv.spring.Scope;
import tv.spring.Service;

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
@Service
public class TraceServiceImpl implements ITraceService {


    @Override
    public List<NftStorage.ItemLife> getLife(String cid, NftMarket nftMarket) throws Exception {
        String sql = "select * from nft.nfts where ipfs_cid = ?";
        List<Nft> select = Factory.getInstance().iDao().select(sql, new Object[]{cid}, Nft.class);
        Nft nft = select.get(0);
        DynamicArray<NftStorage.ItemLife> nftLife = nftMarket.getNftLife(BigInteger.valueOf(nft.getNftId()));
        return nftLife.getValue();
    }


}
