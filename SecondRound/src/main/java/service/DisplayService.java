package service;

import dao.FactoryDao;
import entity.po.Nft;

import java.util.List;

import static util.JsonUtil.analysisJson;

public class DisplayService {
    private DisplayService() {
    }


    public static class DisplayServiceHolder {
        private static final DisplayService INSTANCE = new DisplayService();
    }

    public static DisplayService getInstance() {
        return DisplayServiceHolder.INSTANCE;
    }

    public List<Nft> display() throws Exception {
        String sql = "select * from nft.nfts where is_sold = false";
        List<Nft> list = FactoryDao.getDao().select(sql, new Object[]{}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }
    public List<Nft> displayByUser(String owner) throws Exception {
        String sql = "select * from nft.nfts where owner = ?";
        List<Nft> list = FactoryDao.getDao().select(sql, new Object[]{owner}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }


}
