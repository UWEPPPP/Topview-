package tv.service;

import tv.entity.bo.AuctionBidBo;
import tv.entity.dto.AuctionDto;
import tv.service.wrapper.NftMarket;

import java.sql.SQLException;
import java.util.List;

public interface IAuctionBidService {
    /**
     * 展示拍卖品
     *
     * @return {@link List}<{@link AuctionDto}>
     * @throws Exception 异常
     */
    List<AuctionDto> showAuction() throws Exception;

    /**
     * 出价
     *
     * @param bidder    投标人
     * @param nftMarket 非功能性测试市场
     * @param bo        薄
     * @return int
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     * @throws InterruptedException   中断异常
     */
    int offer(AuctionBidBo bo, String bidder, NftMarket nftMarket) throws Exception;

}
