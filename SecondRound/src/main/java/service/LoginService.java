package service;

import dao.FactoryDao;
import dao.IDao;
import entity.po.User;
import service.wrapper.NftMarket;
import util.CryptoUtil;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.Contract.setNftMarket;

/**
 * 登录服务
 *
 * @author 刘家辉
 * {@code @date} 2023/04/29
 */
public class LoginService {
    private LoginService() {
    }
    public static class LoginServiceHolder {
        private static final LoginService INSTANCE = new LoginService();
    }
    public static LoginService getInstance() {
        return LoginServiceHolder.INSTANCE;
    }

    public Map<String,Object> login(String name, String password) throws Exception {
        IDao iDao = FactoryDao.getDao();
        String paddedStr = String.format("%-32s", password).replace(' ', '0');
        String encryptPassword = CryptoUtil.encryptHexPrivateKey(paddedStr);
        String sql ="select * from nft.nft_user where name = ? and password = ?";
        List<User> list = iDao.select(sql, new Object[]{name, encryptPassword}, User.class);
        User select = list.get(0);
        if (select==null) {
            return null;
        }else {
            NftMarket nftMarket = setNftMarket(CryptoUtil.decryptHexPrivateKey(select.getPrivateKey()));
            BigInteger balance = nftMarket.getBalance();
            select.setBalance(balance.toString());
            Map<String,Object> map=new HashMap<>(2);
            map.put("user",select);
            map.put("nftMarket",nftMarket);
            return  map;
        }

    }
}
