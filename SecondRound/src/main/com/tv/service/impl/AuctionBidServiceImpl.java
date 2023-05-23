package tv.service.impl;

import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.dao.AuctionDao;
import tv.dao.NftDao;
import tv.entity.bo.AuctionBidBo;
import tv.entity.dto.AuctionDto;
import tv.entity.po.Auction;
import tv.entity.po.Nft;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.*;
import tv.util.Contract;
import tv.util.JsonUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拍卖竞价服务impl
 *
 * @author 刘家辉
 * @date 2023/05/24
 */
@CommonLogger
@Component
@Scope("singleton")
@Service
public class AuctionBidServiceImpl implements tv.service.IAuctionBidService {
    @AutoWired
    public AuctionDao auctionDaoImpl;
    @AutoWired
    public NftDao nftDaoImpl;
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
    public List<AuctionDto> showAuction() throws Exception {
        List<Auction> listA = auctionDaoImpl.selectAll();
        List<Nft> list = nftDaoImpl.selectAll();
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
    public int offer(AuctionBidBo bo, String bidder, NftMarket nftMarket) throws Exception {
        int result = auctionDaoImpl.update(new Object[]{bo.getBidPrice(), bidder, bo.getNftId()});
        if (result != 0) {
            TransactionReceipt transactionReceipt = nftMarket.auctionNft(BigInteger.valueOf(bo.getNftId()), BigInteger.valueOf(bo.getBidPrice()));
            String status = transactionReceipt.getStatus();
            if (status.equals(Contract.checkStatus)) {
                return 200;
            }
        }
        return 500;
    }
}
