package tv.entity.dto;

/**
 * 拍卖dto
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
@SuppressWarnings("all")
public class AuctionDto {
    private String name;
    private int nftId;
    private String ipfs_cid;
    private String type;
    private int highest_bid;
    private String highest_bidder;
    private Long end_time;
    private String show;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpfs_cid() {
        return ipfs_cid;
    }

    public void setIpfs_cid(String ipfs_cid) {
        this.ipfs_cid = ipfs_cid;
    }

    public int getHighest_bid() {
        return highest_bid;
    }

    public void setHighest_bid(int highest_bid) {
        this.highest_bid = highest_bid;
    }

    public String getHighest_bidder() {
        return highest_bidder;
    }

    public void setHighest_bidder(String highest_bidder) {
        this.highest_bidder = highest_bidder;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public int getNftId() {
        return nftId;
    }

    public void setNftId(int nftId) {
        this.nftId = nftId;
    }
}
