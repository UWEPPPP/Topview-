package tv.service.impl;

import tv.dao.IDao;
import tv.entity.bo.SearchBo;
import tv.entity.po.Nft;
import tv.service.ISearchService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Scope;
import tv.spring.annotate.ServiceLogger;

import java.util.List;

import static tv.util.JsonUtil.analysisJson;

/**
 * 搜索服务
 *
 * @author 刘家辉
 * @date 2023/05/07
 */

@Component
@Scope("singleton")
@ServiceLogger
public class SearchServiceImpl implements ISearchService {
    @AutoWired
    public IDao dao;
    @Override
    public List<Nft> search(SearchBo bo) throws Exception {
        List<Nft> list;
        String sql;
        String text = null;
        switch (bo.getSearchType()) {
            case "name":
                sql = "select * from nft.nfts where is_sold = false and  name like ?";
                text = "%" + bo.getSearchText() + "%";
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
        list = dao.select(sql, new Object[]{text}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }




}
