package tv.service.impl;

import tv.dao.NftDao;
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
    public NftDao nftDaoImpl;

    @Override
    public List<Nft> search(SearchBo bo) throws Exception {
        List<Nft> list;
        String choice;
        String text = bo.getSearchText();
        switch (bo.getSearchType()) {
            case "name":
                text = "%" + bo.getSearchText() + "%";
                choice = "@name";
                break;
            case "caster":
                choice = "owner";
                break;
            case "cid":
                choice = "ipfs_cid";
                break;
            default:
                return null;
        }
        list = nftDaoImpl.selectBySearch(choice, text);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }


}
