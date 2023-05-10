package service;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.wrapper.NftMarket;
import service.wrapper.NftStorage;

import java.util.List;

public interface ITraceService {
    List<NftStorage.ItemLife> getLife(int id, NftMarket nftMarket) throws ContractException;
}
