package tv.service.impl;

import tv.entity.po.Nft;
import tv.factory.Factory;
import tv.service.ISearchService;
import tv.spring.Component;
import tv.spring.Scope;
import tv.spring.Service;

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
@Service
public class SearchServiceImpl implements ISearchService {

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
        list = Factory.getInstance().iDao().select(sql, new Object[]{text}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }




}
