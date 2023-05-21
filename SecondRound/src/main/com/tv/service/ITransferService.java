package tv.service;

import tv.entity.bo.TransferBo;
import tv.entity.po.User;
import tv.service.wrapper.NftMarket;

import java.util.List;

/**
 * 转增服务
 *
 * @author 刘家辉
 * @date 2023/05/11
 */
public interface ITransferService {
    /**
     * 获得其它用户的名单
     *
     * @param owner 老板
     * @return {@link List}<{@link User}>
     * @throws Exception 异常
     */
    List<User> getTransferList(String owner) throws Exception;

    /**
     * 转增
     *
     * @param bo        薄
     * @param from      从
     * @param nftMarket 非功能性测试市场
     * @return int
     * @throws Exception 异常
     */
    int transfer(TransferBo bo, String from, NftMarket nftMarket) throws Exception;
}
