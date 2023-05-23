package tv.dao;

import tv.entity.po.Nft;

import java.util.List;

public interface NftDao {
    /**
     * 选择所有
     *
     * @return {@link List}<{@link Nft}>
     * @throws Exception 异常
     */
    List<Nft> selectAll() throws Exception;

    /**
     * 选择由cid
     *
     * @param cid cid
     * @return {@link List}<{@link Nft}>
     * @throws Exception 异常
     */
    List<Nft> selectByCid(String cid) throws Exception;

    /**
     * 更新主人
     *
     * @param bidder 投标人
     * @param nftId  非功能性测试id
     * @return int
     * @throws Exception 异常
     */
    int updateOwner(String bidder, int nftId) throws Exception;

    /**
     * 选择选择
     *
     * @param display 显示
     * @return {@link List}<{@link Nft}>
     * @throws Exception 异常
     */
    List<Nft> selectWhichChoice(String display) throws Exception;

    /**
     * 选择由业主
     *
     * @param owner 老板
     * @return {@link List}<{@link Nft}>
     * @throws Exception 异常
     */
    List<Nft> selectByOwner(String owner) throws Exception;

    /**
     * 更新销售
     *
     * @param result 结果
     * @param cid    cid
     * @return int
     */
    int updateSold(boolean result, String cid) throws Exception;

    /**
     * 插入
     *
     * @param name        名字
     * @param ipfsCid     ipf cid
     * @param price       价格
     * @param type        类型
     * @param owner       老板
     * @param description 描述
     * @param b           b
     * @param nftId       非功能性测试id
     * @return int
     */
    int insert(String name, String ipfsCid, int price, String type, String owner, String description, boolean b, int nftId) throws Exception;

    /**
     * 更新老板买
     *
     * @param b     b
     * @param owner 老板
     * @param id    id
     * @return int
     * @throws Exception 异常
     */
    int updateOwnerBuy(boolean b, String owner, int id) throws Exception;

    /**
     * 选择搜索
     *
     * @param choice 选择
     * @param text   文本
     * @return {@link List}<{@link Nft}>
     * @throws Exception 异常
     */
    List<Nft> selectBySearch(String choice, String text) throws Exception;

    /**
     * 更新由cid所有者
     *
     * @param recipientAddress 收件人地址
     * @param cid              cid
     * @return int
     */
    int updateOwnerByCid(String recipientAddress, String cid) throws Exception;
}
