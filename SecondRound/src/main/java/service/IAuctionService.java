package service;

import entity.dto.AuctionDto;
import service.wrapper.NftMarket;

import java.sql.SQLException;
import java.util.List;

/**
 *
 *
 * @author 刘家辉
 * @date 2023/05/11
 */
public interface IAuctionService {


    /**
     * 展示拍卖
     *
     * @return {@link List}<{@link AuctionDto}>
     * @throws Exception 异常
     */
    List<AuctionDto> showAuction() throws Exception;

    /**
     * 提供
     *
     * @param id        id
     * @param price     价格
     * @param bidder    投标人
     * @param nftMarket 非功能性测试市场
     * @return int
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    int offer(int id, int price, String bidder, NftMarket nftMarket) throws SQLException, ClassNotFoundException, InterruptedException;

    /**
     * 拍卖开始
     *
     * @param cid       cid
     * @param duration  持续时间
     * @param amount    量
     * @param nftMarket 非功能性测试市场
     * @return int
     * @throws Exception 异常
     */
    int auctionBegin(String cid, String duration, String amount, NftMarket nftMarket) throws Exception;

    /**
     * 拍卖结束
     *
     * @param nftId     非功能性测试id
     * @param nftMarket 非功能性测试市场
     * @throws Exception 异常
     */
    void auctionEnd(int nftId, NftMarket nftMarket) throws Exception;
}
