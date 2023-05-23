package tv.dao.impl;

import tv.dao.Crud;
import tv.entity.po.Auction;
import tv.spring.annotate.*;

import java.util.List;

/**
 * 拍卖刀
 *
 * @author 刘家辉
 * @date 2023/05/23
 */
@Component
@Scope("singleton")
@CommonLogger
@Storage
public class AuctionDaoImpl implements tv.dao.AuctionDao {
    @AutoWired
    public Crud crudImpl;

    @Override
    public List<Auction> selectAll() throws Exception {
        return crudImpl.select(Auction.class, new String[]{}, new Object[]{});
    }

    @Override
    public List<Auction> selectByNftId(int nftId) throws Exception {
        return crudImpl.select(Auction.class, new String[]{"nftId"}, new Object[]{nftId});
    }

    @Override
    public int update(Object[] values) throws Exception {
        return crudImpl.update(Auction.class, new String[]{"highest_bid", "highest_bidder"}, new String[]{"nftId"}, values);
    }

    @Override
    public int insert(Object[] values) throws Exception {
        return crudImpl.insert(Auction.class, new String[]{"nftId", "highest_bid", "end_time", "highest_bidder"}, values);

    }

    @Override
    public int deleteByNftId(int nftId) throws Exception {
        return crudImpl.delete(Auction.class, new String[]{"nftId"}, new Object[]{nftId});
    }
}
