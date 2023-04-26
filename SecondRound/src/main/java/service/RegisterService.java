package service;

import dao.UserMapper;
import entity.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import util.Ipfs;

import java.io.IOException;
import java.io.InputStream;

public class RegisterService {
    private RegisterService() {
    }
    public static class  RegisterServiceInstance {
       public static final RegisterService INSTANCE = new RegisterService();
    }
    public static RegisterService getInstance() {
        return RegisterServiceInstance.INSTANCE;
    }
    public void register(String username, String password, String fileName ) throws IOException {
        // 在这里将用户数据和头像 IPFS 哈希值保存到数据库中
        String upload = Ipfs.upload(fileName);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName("man");
        user.setPassword("111");
        user.setProfile(upload);
        user.setContract_address("0x111");
        user.setPrivate_key("0x111");
        user.setBalance("1111");
        userMapper.insertUser(user);
        sqlSession.commit();
    }
}
