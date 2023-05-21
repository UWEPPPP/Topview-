package tv.service;

import tv.service.wrapper.NftMarket;
import tv.service.wrapper.NftStorage;

import java.util.List;

/**
 * 追溯服务
 *
 * @author 刘家辉
 * @date 2023/05/11
 */
public interface ITraceService {
    /**
     * 获得生命周期
     *
     * @param cid       cid
     * @param nftMarket 数字藏品系统
     * @return {@link List}<{@link NftStorage.ItemLife}>
     * @throws Exception 异常
     */
    List<NftStorage.ItemLife> getLife(String cid, NftMarket nftMarket) throws Exception;

}
