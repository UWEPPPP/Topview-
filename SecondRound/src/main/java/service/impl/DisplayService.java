package service.impl;

import entity.po.Nft;
import factory.FactoryDao;
import service.IDisplayService;

import java.util.List;

import static util.JsonUtil.analysisJson;

public class DisplayService implements IDisplayService {
    private DisplayService() {
    }

    public static IDisplayService getInstance() {
        return DisplayServiceHolder.INSTANCE;
    }

    @Override
    public List<Nft> display() throws Exception {
        String sql = "select * from nft.nfts where is_sold = false";
        List<Nft> list = FactoryDao.getDao().select(sql, new Object[]{}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }

    @Override
    public List<Nft> displayByUser(String owner) throws Exception {
        String sql = "select * from nft.nfts where owner = ?";
        List<Nft> list = FactoryDao.getDao().select(sql, new Object[]{owner}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }

    public static class DisplayServiceHolder {
        private static final IDisplayService INSTANCE = new DisplayService();
    }


}
