package tv.entity.po;

/**
 * 非功能性测试
 *
 * @author 刘家辉
 * @date 2023/04/29
 */
@SuppressWarnings("all")
public class Nft {
    private int id;
    private String name;
    private String ipfs_cid;
    private int price;
    private String type;
    private String owner;
    private String description;
    private int nftId;
    private String show;
    private Boolean is_sold;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpfs_cid() {
        return ipfs_cid;
    }

    public void setIpfs_cid(String ipfs_cid) {
        this.ipfs_cid = ipfs_cid;
    }

    public int getNftId() {
        return nftId;
    }

    public void setNftId(int nftId) {
        this.nftId = nftId;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIs_sold() {
        return is_sold;
    }

    public void setIs_sold(Boolean is_sold) {
        this.is_sold = is_sold;
    }

}
