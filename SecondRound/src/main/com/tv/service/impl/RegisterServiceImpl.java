package tv.service.impl;

import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.factory.Factory;
import tv.service.IRegisterService;
import tv.service.wrapper.NftMarket;
import tv.spring.Component;
import tv.spring.Scope;
import tv.util.CastUtil;
import tv.util.Contract;
import tv.util.CryptoUtil;
import tv.util.IpfsUtil;

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

@Component
@Scope("singleton")
public class RegisterServiceImpl implements IRegisterService {
    @Override
    public int register(String username, String password, Part avatarPart) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
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
        int insert = Factory.getInstance().iDao().insertOrUpdateOrDelete(sql, new Object[]{username, upload, keyPair.getAddress(), privateKey, userPassword});
        NftMarket nftMarket = CastUtil.cast(map.get("nftMarket"));
        TransactionReceipt register = nftMarket.regiter();
        String status = register.getStatus();
        if (!Contract.checkStatus.equals(status) || insert == 0) {
            return 500;
        } else {
            return 200;
        }
    }

}