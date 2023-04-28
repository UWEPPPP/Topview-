package service;

import dao.FactoryDAO;
import dao.UserDAO;
import entity.po.User;
import util.Contract;
import util.CryptoUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    private LoginService() {
    }
    public static class LoginServiceHolder {
        private static final LoginService INSTANCE = new LoginService();
    }
    public static LoginService getInstance() {
        return LoginServiceHolder.INSTANCE;
    }

    public User login(String name, String password) throws SQLException, ClassNotFoundException {
        UserDAO userDaoInstance = FactoryDAO.getUserDaoInstance();
        User user = new User();
        String cryptedPassword = CryptoUtil.encryptHexPrivateKey(password, "D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\password.txt");
        user.setName(name);
        user.setPassword(cryptedPassword);
        ResultSet select = userDaoInstance.select(user);
        if (!select.next()) {
            return null;
        }else {
            user.setBalance(String.valueOf(select.getInt("balance")));
            user.setContract_address(select.getString("contract_address"));
            user.setPrivate_key(select.getString("private_key"));
            user.setProfile(select.getString("profile"));
            Contract.setNftMarket(user.getPrivate_key());
            return  user;
        }

    }
}
