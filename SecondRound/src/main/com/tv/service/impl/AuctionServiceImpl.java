package tv.service.impl;

import tv.entity.dto.AuctionDto;
import tv.entity.po.Auction;
import tv.entity.po.Nft;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.factory.Factory;
import tv.service.IAuctionService;
import tv.service.wrapper.NftMarket;
import tv.spring.Component;
import tv.spring.Scope;
import tv.spring.Service;
import tv.util.Contract;
import tv.util.JsonUtil;
import tv.util.Timer;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拍卖服务
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
@Component
@Scope("singleton")
@Service
public class AuctionServiceImpl implements IAuctionService {


    @Override
    public List<AuctionDto> showAuction() throws Exception {
        String sql = "select * from nft.nft_auction";
        List<Auction> listA = Factory.getInstance().iDao().select(sql, new Object[]{}, Auction.class);
        String sql1 = "select * from nft.nfts";
        List<Nft> list = Factory.getInstance().iDao().select(sql1, new Object[]{}, Nft.class);
        List<Nft> listB = JsonUtil.analysisJson(list);
        Map<Integer, Auction> mapA = new HashMap<>();
        for (Auction nftA : listA) {
            mapA.put(nftA.getNftId(), nftA);
        }
        List<AuctionDto> resultList = new ArrayList<>();
        for (Nft nftB : listB) {
            Auction nftA = mapA.get(nftB.getNftId());
            if (nftA != null) {
                AuctionDto combineNft = combine(nftA, nftB);
                resultList.add(combineNft);
            }
        }
        return resultList;
    }

    @Override
    public int offer(int id, int price, String bidder, NftMarket nftMarket) throws SQLException, ClassNotFoundException, InterruptedException {
        TransactionReceipt transactionReceipt = nftMarket.auctionNft(BigInteger.valueOf(id),BigInteger.valueOf( price));
        String status = transactionReceipt.getStatus();
        String sql = " update nft.nft_auction set highest_bid = ?,highest_bidder = ? where nftId = ?";
        int result = Factory.getInstance().iDao().insertOrUpdateOrDelete(sql, new Object[]{price,bidder,id});
        if (status.equals(Contract.checkStatus)&&result!=0) {
            return 200;
        } else {
            return 500;
        }
    }

    private AuctionDto combine(Auction nftA, Nft nftB) {
        AuctionDto auctionDto = new AuctionDto();
        // 拷贝相关属性
        auctionDto.setHighest_bid(nftA.getHighest_bid());
        auctionDto.setEnd_time(nftA.getEnd_time());
        auctionDto.setHighest_bidder(nftA.getHighest_bidder());
        auctionDto.setName(nftB.getName());
        auctionDto.setIpfs_cid(nftB.getIpfs_cid());
        auctionDto.setType(nftB.getType());
        auctionDto.setShow(nftB.getShow());
        auctionDto.setNftId(nftB.getNftId());
        return auctionDto;
    }

    @Override
    public int auctionBegin(String cid, String duration, String amount, NftMarket nftMarket) throws Exception {
        String sql = " select * from nft.nfts where ipfs_cid = ?";
        List<Nft> select = Factory.getInstance().iDao().select(sql, new Object[]{cid}, Nft.class);
        int nftId = select.get(0).getNftId();
        String sqlToInsert = " insert into nft.nft_auction (nftId,highest_bid,end_time,highest_bidder) values (?,?,?,?)";
        long time = System.currentTimeMillis() + Integer.parseInt(duration) * 1_000L;
        int result = Factory.getInstance().iDao().insertOrUpdateOrDelete(sqlToInsert, new Object[]{nftId, amount, time,select.get(0).getOwner()});
        TransactionReceipt transactionReceipt = nftMarket.auctionBegin(BigInteger.valueOf(nftId), BigInteger.valueOf(Long.parseLong(amount)), BigInteger.valueOf(Integer.parseInt(duration) * 1_000L));
        String status = transactionReceipt.getStatus();
        Timer.getInstance().beginAuction(nftId, Integer.parseInt(duration));
        if (status.equals(Contract.checkStatus) && result != 0) {
            return 200;
        }
        return 500;
    }

    @Override
    public void auctionEnd(int nftId, NftMarket nftMarket) throws Exception {
        TransactionReceipt transactionReceipt = nftMarket.auctionEnd(BigInteger.valueOf(nftId));
        String status = transactionReceipt.getStatus();
        String sql2 = "select * from nft.nft_auction where nftId = ?";
        List<Auction> list = Factory.getInstance().iDao().select(sql2, new Object[]{nftId}, Auction.class);
        String bidder = list.get(0).getHighest_bidder();
        String sql = " delete from nft.nft_auction where nftId = ?";
        int result = Factory.getInstance().iDao().insertOrUpdateOrDelete(sql, new Object[]{nftId});
        String sql1 = " update nft.nfts set owner = ? where nftId = ?";
        int result1 = Factory.getInstance().iDao().insertOrUpdateOrDelete(sql1, new Object[]{bidder,nftId});
        if(status.equals(Contract.checkStatus)&&result!=0&&result1!=0){
            System.out.println("拍卖结束");
        }else {
            System.out.println("拍卖结束异常");
        }
    }

}
