package tv.entity.po;

/**
 * 拍卖
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
@SuppressWarnings("all")
public class Auction {
    private int id;
    private int nftId;
    private int highest_bid;
    private String highest_bidder;
    private Long end_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNftId() {
        return nftId;
    }

    public void setNftId(int nftId) {
        this.nftId = nftId;
    }

    public int getHighest_bid() {
        return highest_bid;
    }

    public void setHighest_bid(int highest_bid) {
        this.highest_bid = highest_bid;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public String getHighest_bidder() {
        return highest_bidder;
    }

    public void setHighest_bidder(String highest_bidder) {
        this.highest_bidder = highest_bidder;
    }
}
