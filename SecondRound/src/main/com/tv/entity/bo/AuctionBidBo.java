package tv.entity.bo;

/**
 * 出价
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class AuctionBidBo {
  private int nftId;
  private int bidPrice;

    public int getNftId() {
        return nftId;
    }

    public void setNftId(int nftId) {
        this.nftId = nftId;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }
}
