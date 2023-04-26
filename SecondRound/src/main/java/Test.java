import dao.UserMapper;
import entity.po.User;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import util.Ipfs;

import java.io.*;
import java.net.URLEncoder;

public class Test {
    public static void main(String[] args) throws IOException {
        IPFS ipfs=new IPFS("/ip4/127.0.0.1/tcp/5001");
        Test test = new Test();
        String upload = test.upload(ipfs, "SecondRound/下载.jpg");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName("man");
        user.setPassword("123");
        user.setProfile(upload);
        user.setContract_address("0x123");
        user.setPrivate_key("0x123");
        user.setBalance("100");
        userMapper.insertUser(user);
        sqlSession.commit();
    }

    public String upload(IPFS ipfs, String fileName) throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(fileName));
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    public String upload(IPFS ipfs, byte[] data) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(data);
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    public byte[] download(IPFS ipfs, String hash) {
        byte[] data = null;
        try {
            data = ipfs.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void download(IPFS ipfs, String hash, String destFile) {
        byte[] data = null;
        try {
            data = ipfs.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Ipfs.check(destFile, data);
    }
}
