package entity.po;

import java.math.BigInteger;

/**
 * 非功能性测试
 *
 * @author 刘家辉
 * @date 2023/04/29
 */
public class Nft {
  private String name;
  private String ipfsCid;
  private String price;
  private String type;
  private String owner;
  private String description;
    private BigInteger tokenId;
  private String show;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getIpfsCid() {
        return ipfsCid;
    }

    public void setIpfsCid(String ipfsCid) {
        this.ipfsCid = ipfsCid;
    }

    public void setTokenId(BigInteger tokenId) {
        this.tokenId = tokenId;
    }

    public BigInteger getTokenId() {
        return tokenId;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
