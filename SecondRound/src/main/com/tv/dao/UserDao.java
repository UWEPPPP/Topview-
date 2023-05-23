package tv.dao;

import tv.entity.po.User;

import java.util.List;

/**
 * 用户刀
 *
 * @author 刘家辉
 * @date 2023/05/23
 */
public interface UserDao {
    /**
     * 更新信息
     *
     * @param choice          选择
     * @param update          更新
     * @param contractAddress 合同地址
     * @return int
     */
    int updateInfo(String choice, String update, String contractAddress) throws Exception;

    /**
     * 选择登录
     *
     * @param username        用户名
     * @param encryptPassword 加密密码
     * @return {@link List}<{@link User}>
     * @throws Exception 异常
     */
    List<User> selectToLogin(String username, String encryptPassword) throws Exception;

    /**
     * 插入
     *
     * @param username     用户名
     * @param upload       上传
     * @param address      地址
     * @param privateKey   私钥
     * @param userPassword 用户密码
     * @return int
     */
    int insert(String username, String upload, String address, String privateKey, String userPassword) throws Exception;

    /**
     * 选择列表
     *
     * @param owner 老板
     * @return {@link List}<{@link User}>
     */
    List<User> selectForList(String owner) throws Exception;
}
