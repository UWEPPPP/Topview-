package tv.dao;

import tv.entity.po.Auction;

import java.util.List;

/**
 * 拍卖刀
 *
 * @author 刘家辉
 * @date 2023/05/23
 */
public interface AuctionDao {
    /**
     * 选择所有
     *
     * @return {@link List}<{@link Auction}>
     * @throws Exception 异常
     */
    List<Auction> selectAll() throws Exception;

    /**
     * 选择通过非功能性测试id
     *
     * @param nftId 非功能性测试id
     * @return {@link List}<{@link Auction}>
     * @throws Exception 异常
     */
    List<Auction> selectByNftId(int nftId) throws Exception;

    /**
     * 更新
     *
     * @param values 值
     * @return int
     */
    int update(Object[] values) throws Exception;

    int insert(Object[] values) throws Exception;

    /**
     * 按非功能性测试删除id
     * 删除
     *
     * @param nftId 非功能性测试id
     * @return int
     * @throws Exception 异常
     */
    int deleteByNftId(int nftId) throws Exception;
}
