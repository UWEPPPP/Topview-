package tv.service;

import tv.entity.bo.AuctionBeginBo;
import tv.entity.bo.AuctionBidBo;
import tv.entity.dto.AuctionDto;
import tv.service.wrapper.NftMarket;

import java.sql.SQLException;
import java.util.List;

/**
 * 拍卖服务
 *
 * @author 刘家辉
 * @date 2023/05/11
 */
public interface IAuctionService {



    /**
     * 拍卖开始
     *
     * @param nftMarket      非功能性测试市场
     * @param auctionBeginBo 拍卖开始薄熙来
     * @return int
     * @throws Exception 异常
     */
    int auctionBegin(AuctionBeginBo auctionBeginBo, NftMarket nftMarket) throws Exception;

    /**
     * 拍卖结束
     *
     * @param nftId     非功能性测试id
     * @param nftMarket 非功能性测试市场
     * @throws Exception 异常
     */
    void auctionEnd(int nftId, NftMarket nftMarket) throws Exception;

    /**
     * 自动检查拍卖结束
     *
     * @param id   id
     * @param time 时间
     */
    void autoCheckEnd(int id, int time);
}
