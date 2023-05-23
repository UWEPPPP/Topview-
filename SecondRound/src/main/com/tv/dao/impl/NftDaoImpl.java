package tv.dao.impl;

import tv.dao.Crud;
import tv.entity.po.Nft;
import tv.spring.annotate.*;

import java.util.List;

/**
 * 非功能性测试刀impl
 *
 * @author 刘家辉
 * @date 2023/05/23
 */
@Component
@Scope("singleton")
@CommonLogger
@Storage
public class NftDaoImpl implements tv.dao.NftDao {
    @AutoWired
    public Crud crudImpl;

    @Override
    public List<Nft> selectAll() throws Exception {
        return crudImpl.select(Nft.class, new String[]{}, new Object[]{});
    }

    @Override
    public List<Nft> selectByCid(String cid) throws Exception {
        return crudImpl.select(Nft.class, new String[]{"ipfs_cid"}, new Object[]{cid});
    }

    @Override
    public int updateOwner(String bidder, int nftId) throws Exception {
        return crudImpl.update(Nft.class, new String[]{"owner"}, new String[]{"nftId"}, new Object[]{bidder, nftId});
    }

    @Override
    public List<Nft> selectWhichChoice(String display) throws Exception {
        return crudImpl.select(Nft.class, new String[]{"is_sold", "AND", "type"}, new Object[]{false, display});
    }

    @Override
    public List<Nft> selectByOwner(String owner) throws Exception {
        return crudImpl.select(Nft.class, new String[]{"owner"}, new Object[]{owner});
    }

    @Override
    public int updateSold(boolean result, String cid) throws Exception {
        return crudImpl.update(Nft.class, new String[]{"is_sold"}, new String[]{"ipfs_cid"}, new Object[]{result, cid});
    }

    @Override
    public int insert(String name, String ipfsCid, int price, String type, String owner, String description, boolean b, int nftId) throws Exception {
        return crudImpl.insert(Nft.class, new String[]{"name", "ipfs_cid", "price", "type", "owner", "description", "is_sold", "nftId"}, new Object[]{name, ipfsCid, price, type, owner, description, b, nftId});
    }

    @Override
    public int updateOwnerBuy(boolean b, String owner, int nftId) throws Exception {
        return crudImpl.update(Nft.class, new String[]{"is_sold", "owner"}, new String[]{"nftId"}, new Object[]{b, owner, nftId});
    }

    @Override
    public List<Nft> selectBySearch(String choice, String text) throws Exception {
        return crudImpl.select(Nft.class, new String[]{"is_sold", "AND", choice}, new Object[]{false, text});
    }

    @Override
    public int updateOwnerByCid(String recipientAddress, String cid) throws Exception {
        return crudImpl.update(Nft.class, new String[]{"owner"}, new String[]{"ipfs_cid"}, new Object[]{recipientAddress, cid});
    }
}
