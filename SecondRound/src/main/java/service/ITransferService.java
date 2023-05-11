package service;

import entity.po.User;
import service.wrapper.NftMarket;

import java.util.List;

/**
 * itransfer服务
 *
 * @author 刘家辉
 * @date 2023/05/11
 */
public interface ITransferService {
    /**
     * 得到转会名单
     *
     * @param owner 老板
     * @return {@link List}<{@link User}>
     * @throws Exception 异常
     */
    List<User> getTransferList(String owner) throws Exception;

    int transfer(String to, String cid, String from, NftMarket nftMarket) throws Exception;
}
