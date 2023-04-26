package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class FactoryDAO {
    private static class FactoryDAOInstance {
        private static final FactoryDAO INSTANCE;

        static {
            try {
                INSTANCE = new FactoryDAO();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static FactoryDAO getInstance() {
        return FactoryDAOInstance.INSTANCE;
    }
    private final SqlSessionFactory sqlSessionFactory;

    private FactoryDAO() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    public static UserMapper getUserMapper() throws IOException {
        FactoryDAO instance = getInstance();
        try(SqlSession sqlSession = instance.sqlSessionFactory.openSession()){
            return sqlSession.getMapper(UserMapper.class);
        }
    }

}
