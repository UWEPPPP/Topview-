package service;

import dao.FactoryDAO;
import dao.NftDAO;
import entity.po.Nft;

import java.sql.SQLException;
import java.util.List;

import static util.Json.analysisJson;

/**
 * 搜索服务
 *
 * @author 刘家辉
 * @date 2023/05/07
 */
public class SearchService {
    private SearchService() {
    }
    public static class SearchServiceHolder {
        private static final SearchService INSTANCE = new SearchService();
    }

    public static SearchService getInstance() {
        return SearchServiceHolder.INSTANCE;
    }
    public List<Nft> search(String type,String text) throws SQLException, ClassNotFoundException {
        List<Nft> list;
        switch (type){
            case "name":list=FactoryDAO.getNftDaoInstance().selectByName(text);
            break;
            case "caster":list=FactoryDAO.getNftDaoInstance().selectByOwner(text);
            break;
            case "cid":list=FactoryDAO.getNftDaoInstance().selectByCid(text);
            break;
            default: return null;
        }
        if(list.size()==0){
            return null;
        }
        return analysisJson(list);
    }


}
