package dao;

import entity.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author LiuJiaHui
 */
public interface UserMapper {
    /**
     * 选择用户id
     *
     * @param id id
     * @return int
     */
    @SuppressWarnings("unchecked")
    @Select("select * from nft.ntf_user where id = #{id}")
    int selectUserById(int id);

    @Insert("insert into nft.ntf_user ( name, profile, contract_address, private_key, balance, password) values ( #{name}, #{profile}, #{contract_address}, #{private_key}, #{balance}, #{password})")
    void insertUser(User user);
}
