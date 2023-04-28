package service;

import dao.FactoryDAO;
import dao.UserDAO;
import entity.po.User;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import util.Contract;
import util.CryptoUtil;
import util.Ipfs;

import java.io.IOException;
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
    public void register(String username, String password, String fileName, byte[] byteArray) throws IOException, ContractException, SQLException, ClassNotFoundException {
        CryptoKeyPair keyPair = Contract.setNftMarket();
        String hexPrivateKey = keyPair.getHexPrivateKey();
        System.out.println(hexPrivateKey);
        String paddedStr = String.format("%-32s", password).replace(' ', '0');
        String privateKey = CryptoUtil.encryptHexPrivateKey(hexPrivateKey, "D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\password.txt");
        String userPassword = CryptoUtil.encryptHexPrivateKey(paddedStr, "D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\password.txt");
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
        NftMarket nftMarket = Contract.getNftMarket();
        TransactionReceipt register = nftMarket.regiter();
        String status = register.getStatus();
        String check= "0x0";
        if (!check.equals(status)||insert==0) {
            throw  new RuntimeException("注册失败");
        }
    }
}
