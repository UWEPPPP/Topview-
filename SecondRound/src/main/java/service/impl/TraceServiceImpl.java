package service.impl;

import entity.po.Nft;
import factory.FactoryDao;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import service.ITraceService;
import service.wrapper.NftMarket;
import service.wrapper.NftStorage;

import java.math.BigInteger;
import java.util.List;

/**
 * 跟踪服务
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
public class TraceServiceImpl implements ITraceService {
    private TraceServiceImpl() {
    }

    public static ITraceService getInstance() {
        return TraceServiceHolder.INSTANCE;
    }

    @Override
    public List<NftStorage.ItemLife> getLife(String cid, NftMarket nftMarket) throws Exception {
        String sql = "select * from nft.nfts where ipfs_cid = ?";
        List<Nft> select = FactoryDao.getDao().select(sql, new Object[]{cid}, Nft.class);
        Nft nft = select.get(0);
        DynamicArray<NftStorage.ItemLife> nftLife = nftMarket.getNftLife(BigInteger.valueOf(nft.getNftId()));
        return nftLife.getValue();
    }

    public static class TraceServiceHolder {
        private static final ITraceService INSTANCE = new TraceServiceImpl();
    }
}
