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
    public void register(String username, String password, String fileName, byte[] byteArray) throws IOException {
        // 在这里将用户数据和头像 IPFS 哈希值保存到数据库中
        String upload = Ipfs.upload(fileName,byteArray);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setProfile(upload);
        user.setContract_address();
        sqlSession.commit();
    }
}
