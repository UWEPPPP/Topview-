package tv.service.impl;

import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import tv.dao.NftDao;
import tv.entity.po.Nft;
import tv.service.ITraceService;
import tv.service.wrapper.NftMarket;
import tv.service.wrapper.NftStorage;
import tv.spring.annotate.*;

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
@CommonLogger
@Service
public class TraceServiceImpl implements ITraceService {
    @AutoWired
    public NftDao nftDaoImpl;

    @Override
    public List<NftStorage.ItemLife> getLife(String cid, NftMarket nftMarket) throws Exception {
        List<Nft> select = nftDaoImpl.selectByCid(cid);
        Nft nft = select.get(0);
        DynamicArray<NftStorage.ItemLife> nftLife = nftMarket.getNftLife(BigInteger.valueOf(nft.getNftId()));
        return nftLife.getValue();
    }


}
