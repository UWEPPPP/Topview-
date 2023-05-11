package service.impl;

import factory.FactoryDao;
import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import service.IRegisterService;
import service.wrapper.NftMarket;
import util.CastUtil;
import util.Contract;
import util.CryptoUtil;
import util.IpfsUtil;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

/**
 * 注册服务
 *
 * @author 刘家辉
 * &#064;date  2023/04/28
 */
public class RegisterServiceImpl implements IRegisterService {
    private RegisterServiceImpl() {
    }

    public static IRegisterService getInstance() {
        return RegisterServiceInstance.INSTANCE;
    }

    @Override
    public int register(String username, String password, Part avatarPart) throws IOException, SQLException, ClassNotFoundException {
        Map<String, Object> map = Contract.setNftMarket();
        CryptoKeyPair keyPair = CastUtil.cast(map.get("keyPair"));
        String hexPrivateKey = keyPair.getHexPrivateKey();
        // 保存用户头像到服务器
        InputStream inputStream = avatarPart.getInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        String paddedStr = String.format("%-32s", password).replace(' ', '0');
        String privateKey = CryptoUtil.encryptHexPrivateKey(hexPrivateKey);
        String userPassword = CryptoUtil.encryptHexPrivateKey(paddedStr);
        String upload = IpfsUtil.upload(byteArray);
        String sql = "insert into nft.nft_user(name, profile, contract_address, private_key, password) values(?,?,?,?,?)";
        int insert = FactoryDao.getDao().insertOrUpdateOrDelete(sql, new Object[]{username, upload, keyPair.getAddress(), privateKey, userPassword});
        NftMarket nftMarket = CastUtil.cast(map.get("nftMarket"));
        TransactionReceipt register = nftMarket.regiter();
        String status = register.getStatus();
        if (!Contract.checkStatus.equals(status) || insert == 0) {
            return 500;
        } else {
            return 200;
        }
    }

    public static class RegisterServiceInstance {
        public static final IRegisterService INSTANCE = new RegisterServiceImpl();
    }
}