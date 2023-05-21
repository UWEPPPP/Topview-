package tv.entity.bo;

/**
 * 转增
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class TransferBo {
    private String recipientAddress;
    private String cid;

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
