package service;

import dao.FactoryDAO;
import dao.UserDAO;
import entity.po.User;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.wrapper.NftMarket;
import util.Contract;
import util.CryptoUtil;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
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

    public Map<String,Object> login(String name, String password) throws SQLException, ClassNotFoundException, ContractException {
        UserDAO userDaoInstance = FactoryDAO.getUserDaoInstance();
        User user = new User();
        String paddedStr = String.format("%-32s", password).replace(' ', '0');
        String encryptPassword = CryptoUtil.encryptHexPrivateKey(paddedStr);
        user.setName(name);
        user.setPassword(encryptPassword);
        System.out.println(name+" "+encryptPassword);
        User select = userDaoInstance.select(user);
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
