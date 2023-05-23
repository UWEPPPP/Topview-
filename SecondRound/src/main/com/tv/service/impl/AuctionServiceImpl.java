package tv.service.impl;

import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.dao.AuctionDao;
import tv.dao.NftDao;
import tv.entity.bo.AuctionBeginBo;
import tv.entity.bo.AuctionBidBo;
import tv.entity.dto.AuctionDto;
import tv.entity.po.Auction;
import tv.entity.po.Nft;
import tv.service.IAuctionService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.*;
import tv.util.Contract;
import tv.util.JsonUtil;
import tv.util.Logger;
import tv.util.ThreadPool;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * 拍卖服务
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
@SecurityLogger
@Component
@Scope("singleton")
@Service
public class AuctionServiceImpl implements IAuctionService {
    @AutoWired
    public AuctionDao auctionDaoImpl;
    @AutoWired
    public NftDao nftDaoImpl;

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
    public int auctionBegin(AuctionBeginBo auctionBeginBo, NftMarket nftMarket) throws Exception {
        List<Nft> select = nftDaoImpl.selectByCid(auctionBeginBo.getCid());
        int nftId = select.get(0).getNftId();
        long time = System.currentTimeMillis() + auctionBeginBo.getDuration() * 1_000L;
        int result = auctionDaoImpl.insert(new Object[]{nftId, auctionBeginBo.getAmount(), time, select.get(0).getOwner()});
        autoCheckEnd(nftId, auctionBeginBo.getDuration());
        if (result != 0) {
            TransactionReceipt transactionReceipt = nftMarket.auctionBegin(BigInteger.valueOf(nftId), BigInteger.valueOf(auctionBeginBo.getAmount()), BigInteger.valueOf(auctionBeginBo.getDuration() * 1_000L));
            String status = transactionReceipt.getStatus();
            if (status.equals(Contract.checkStatus)) {
                return 200;
            }
        }
        return 500;
    }

    @Override
    public void auctionEnd(int nftId, NftMarket nftMarket) throws Exception {
        List<Auction> list = auctionDaoImpl.selectByNftId(nftId);
        String bidder = list.get(0).getHighest_bidder();
        int result = auctionDaoImpl.deleteByNftId(nftId);
        int result1 = nftDaoImpl.updateOwner(bidder, nftId);
        if (result != 0 && result1 != 0) {
            TransactionReceipt transactionReceipt = nftMarket.auctionEnd(BigInteger.valueOf(nftId));
            String status = transactionReceipt.getStatus();
            if (status.equals(Contract.checkStatus)) {
                tv.util.Logger.info("拍卖结束成功");
            }
        } else {
            tv.util.Logger.warning("拍卖结束失败");
        }
    }

    @Override
    public void autoCheckEnd(int id, int time) {
        NftMarket nftMarket = Contract.getAdmin();
        Runnable runnable = () -> {
            try {
                Thread.sleep(time * 1000L);
                auctionEnd(id, nftMarket);
            } catch (Exception e) {
                Logger.logException(Level.SEVERE, "拍卖结束失败", e);
            }
        };
        ThreadPool.SERVICE.submit(runnable);
    }


}
