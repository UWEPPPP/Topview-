package tv.entity.bo;

/**
 * 开始拍卖
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class AuctionBeginBo {
    private String cid;
    private int duration;
    private int amount;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
