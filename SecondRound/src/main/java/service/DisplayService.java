package service;

import dao.FactoryDAO;
import entity.po.Nft;
import util.CastUtil;

import java.sql.SQLException;
import java.util.List;

public class DisplayService {
    private DisplayService() {
    }


    public static class DisplayServiceHolder {
        private static final DisplayService INSTANCE = new DisplayService();
    }

    public static DisplayService getInstance() {
        return DisplayServiceHolder.INSTANCE;
    }

    public List<Nft> display() throws SQLException, ClassNotFoundException {
        List<Nft> list = CastUtil.cast(FactoryDAO.getNftDaoInstance().select(""));
        if (list.size() == 0) {
            return null;
        }
        return list;
    }
    public List<Nft> displayByUser(String owner) throws SQLException, ClassNotFoundException {
        List<Nft> list = CastUtil.cast(FactoryDAO.getNftDaoInstance().selectByUser(owner));
        if (list.size() == 0) {
            return null;
        }
        return list;
    }
}
