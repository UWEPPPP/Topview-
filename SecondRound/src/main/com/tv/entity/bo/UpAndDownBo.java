package tv.entity.bo;

/**
 * 上下架
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class UpAndDownBo {
    private String cid;
    private Boolean onSale;

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
