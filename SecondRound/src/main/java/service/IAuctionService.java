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


    List<AuctionDto> showAuction() throws Exception;

    int offer(int id, int price, String bidder, NftMarket nftMarket) throws SQLException, ClassNotFoundException;

    int auctionBegin(String cid, String duration, String amount, NftMarket nftMarket) throws Exception;

}
