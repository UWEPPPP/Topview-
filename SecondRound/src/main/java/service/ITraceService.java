package service;

import service.wrapper.NftMarket;
import service.wrapper.NftStorage;

import java.util.List;

/**
 * itrace服务
 *
 * @author 刘家辉
 * @date 2023/05/11
 */
public interface ITraceService {
    List<NftStorage.ItemLife> getLife(String cid, NftMarket nftMarket) throws Exception;

}
