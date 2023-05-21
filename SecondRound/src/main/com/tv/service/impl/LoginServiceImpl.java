package tv.service.impl;

import tv.dao.IDao;
import tv.entity.bo.LoginBo;
import tv.entity.po.User;
import tv.service.ILoginService;
import tv.service.wrapper.NftMarket;
import tv.spring.AutoWired;
import tv.spring.Component;
import tv.spring.Scope;
import tv.spring.ServiceLogger;
import tv.util.CryptoUtil;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tv.util.Contract.setNftMarket;

/**
 * 登录服务
 *
 * @author 刘家辉
 * {@code @date} 2023/04/29
 */

@Component
@Scope("singleton")
@ServiceLogger
public class LoginServiceImpl implements ILoginService {
    @AutoWired
    public IDao dao;
    @Override
    public Map<String, Object> login(LoginBo bo) throws Exception {
        String paddedStr = String.format("%-32s", bo.getPassword()).replace(' ', '0');
        String encryptPassword = CryptoUtil.encryptHexPrivateKey(paddedStr);
        String sql = "select * from nft.nft_user where name = ? and password = ?";
        List<User> list = dao.select(sql, new Object[]{bo.getUsername(), encryptPassword}, User.class);
        User select = list.get(0);
        System.out.println(list);
        if (select == null) {
            return null;
        } else {
            NftMarket nftMarket = setNftMarket(CryptoUtil.decryptHexPrivateKey(select.getPrivate_key()));
            BigInteger balance = nftMarket.getBalance();
            select.setBalance(balance.toString());
            Map<String, Object> map = new HashMap<>(2);
            map.put("user", select);
            map.put("nftMarket", nftMarket);
            return map;
        }

    }

}
