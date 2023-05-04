package service;

import dao.FactoryDAO;
import dao.UserDAO;
import entity.po.User;
import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import util.CastUtil;
import util.Contract;
import util.CryptoUtil;
import util.Ipfs;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * 注册服务
 *
 * @author 刘家辉
 * &#064;date  2023/04/28
 */
public class RegisterService {
    private RegisterService() {
    }
    public static class  RegisterServiceInstance {
       public static final RegisterService INSTANCE = new RegisterService();
    }
    public static RegisterService getInstance() {
        return RegisterServiceInstance.INSTANCE;
    }
    public int register(String username, String password, Part avatarPart) throws IOException, ContractException, SQLException, ClassNotFoundException {
        CryptoKeyPair keyPair = Contract.setNftMarket();
        String hexPrivateKey = keyPair.getHexPrivateKey();
        // 保存用户头像到服务器
        InputStream inputStream = avatarPart.getInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        String paddedStr = String.format("%-32s", password).replace(' ', '0');
        String privateKey = CryptoUtil.encryptHexPrivateKey(hexPrivateKey);
        String userPassword = CryptoUtil.encryptHexPrivateKey(paddedStr);
        String upload = Ipfs.upload(byteArray);
        User user = new User();
        user.setName(username);
        user.setPassword(userPassword);
        user.setProfile(upload);
        user.setContractAddress(keyPair.getAddress());
        user.setPrivateKey(privateKey);
        user.setBalance(String.valueOf(1000));
        UserDAO userDaoInstance = FactoryDAO.getUserDaoInstance();
        int insert = CastUtil.cast(userDaoInstance.insert(user));
        NftMarket nftMarket = Contract.getNftMarket();
        TransactionReceipt register = nftMarket.regiter();
        String status = register.getStatus();
        String check= "0x0";
        if (!check.equals(status)||insert==0) {
           return 500;
        }else {
            return 200;
        }
    }
}
