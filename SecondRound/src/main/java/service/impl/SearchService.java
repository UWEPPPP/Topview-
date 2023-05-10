package service.impl;

import entity.po.Nft;
import factory.FactoryDao;
import service.ISearchService;

import java.util.List;

import static util.JsonUtil.analysisJson;

/**
 * 搜索服务
 *
 * @author 刘家辉
 * @date 2023/05/07
 */
public class SearchService implements ISearchService {
    private SearchService() {
    }

    public static ISearchService getInstance() {
        return SearchServiceHolder.INSTANCE;
    }

    @Override
    public List<Nft> search(String type, String text) throws Exception {
        List<Nft> list;
        String sql;
        switch (type) {
            case "name":
                sql = "select * from nft.nfts where is_sold = false and  name like ?";
                text = "%" + text + "%";
                break;
            case "caster":
                sql = "select * from nft.nfts where is_sold = false and  owner = ?";
                break;
            case "cid":
                sql = "select * from nft.nfts where is_sold = false and  ipfs_cid = ?";
                break;
            default:
                return null;
        }
        list = FactoryDao.getDao().select(sql, new Object[]{text}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }

    public static class SearchServiceHolder {
        private static final ISearchService INSTANCE = new SearchService();
    }


}
