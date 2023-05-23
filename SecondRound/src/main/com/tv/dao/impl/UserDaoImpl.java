package tv.dao.impl;

import tv.dao.Crud;
import tv.entity.po.User;
import tv.spring.annotate.*;

import java.util.List;

/**
 * 用户刀impl
 *
 * @author 刘家辉
 * @date 2023/05/23
 */
@Component
@Scope("singleton")
@CommonLogger
@Storage
public class UserDaoImpl implements tv.dao.UserDao {
    @AutoWired
    public Crud crudImpl;

    @Override
    public int updateInfo(String choice, String update, String contractAddress) throws Exception {
        return crudImpl.update(User.class, new String[]{choice}, new String[]{"contract_address"}, new Object[]{update, contractAddress});
    }

    @Override
    public List<User> selectToLogin(String username, String encryptPassword) throws Exception {
        return crudImpl.select(User.class, new String[]{"name", "AND", "password"}, new Object[]{username, encryptPassword});
    }

    @Override
    public int insert(String username, String profile, String address, String privateKey, String userPassword) throws Exception {
        return crudImpl.insert(User.class, new String[]{"name", "profile", "address", "private_key", "password"}, new Object[]{username, profile, address, privateKey, userPassword});
    }

    @Override
    public List<User> selectForList(String owner) throws Exception {
        return crudImpl.select(User.class, new String[]{"!contract_address"}, new Object[]{owner});
    }
}
