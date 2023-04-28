package service;

import dao.FactoryDAO;
import dao.UserDAO;
import entity.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import util.Contract;
import util.CryptoUtil;
import util.Ipfs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class RegisterService {
    private static final BcosSDK SDK = BcosSDK.build("config-example.toml");
    private static final Client CLIENT = SDK.getClient(1);
    private static final CryptoSuite CRYPTO_SUITE = CLIENT.getCryptoSuite();
    private RegisterService() {
    }
    public static class  RegisterServiceInstance {
       public static final RegisterService INSTANCE = new RegisterService();
    }
    public static RegisterService getInstance() {
        return RegisterServiceInstance.INSTANCE;
    }
    public void register(String username, String password, String fileName, byte[] byteArray) throws IOException, ContractException, SQLException {
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair();
        String hexPrivateKey = keyPair.getHexPrivateKey();
        String privateKey = CryptoUtil.encryptHexPrivateKey(hexPrivateKey, "src/resource/password.txt");
        String userPassword = CryptoUtil.encryptHexPrivateKey(password, "src/resource/password.txt");
        Contract.getInstance().setNftMarket(NftMarket.deploy(CLIENT, keyPair));
        String upload = Ipfs.upload(fileName,byteArray);
        User user = new User();
        user.setName(username);
        user.setPassword(userPassword);
        user.setProfile(upload);
        user.setContract_address(keyPair.getAddress());
        user.setPrivate_key(privateKey);
        user.setBalance(String.valueOf(1000));
        UserDAO userDaoInstance = FactoryDAO.getUserDaoInstance();
        int insert = userDaoInstance.insert(user);
        NftMarket nftMarket = Contract.getInstance().getNftMarket();
        TransactionReceipt register = nftMarket.regiter();
        String status = register.getStatus();
        String check= "0x0";
        if (!check.equals(status)||insert==0) {
            throw  new RuntimeException("注册失败");
        }
    }
}
