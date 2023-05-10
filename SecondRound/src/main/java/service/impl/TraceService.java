package service.impl;

import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
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
public class TraceService implements ITraceService {
    private TraceService() {
    }

    public static ITraceService getInstance() {
        return TraceServiceHolder.INSTANCE;
    }

    @Override
    public List<NftStorage.ItemLife> getLife(int id, NftMarket nftMarket) throws ContractException {
        DynamicArray<NftStorage.ItemLife> nftLife = nftMarket.getNftLife(BigInteger.valueOf(id));
        return nftLife.getValue();
    }

    public static class TraceServiceHolder {
        private static final ITraceService INSTANCE = new TraceService();
    }
}
