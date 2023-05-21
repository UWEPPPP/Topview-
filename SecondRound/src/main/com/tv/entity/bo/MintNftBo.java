package tv.entity.bo;

import javax.servlet.http.Part;

/**
 * 铸造nft
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class MintNftBo {
  private String name;
  private String description;
  private String type;
  private int price;
  private Part file;
  private String owner;
  private String ipfs_cid;
  private int nftId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
}
