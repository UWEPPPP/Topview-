package tv.service.impl;

import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.dao.UserDao;
import tv.entity.bo.RegisterBo;
import tv.service.IRegisterService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.*;
import tv.util.CastUtil;
import tv.util.Contract;
import tv.util.CryptoUtil;
import tv.util.IpfsUtil;

import java.io.InputStream;
import java.util.Map;

/**
 * 注册服务
 *
 * @author 刘家辉
 * &#064;date  2023/04/28
 */

@Component
@Scope("singleton")
@Service
public class RegisterServiceImpl implements IRegisterService {
    @AutoWired
    public UserDao userDaoImpl;

    @Override
    @Transaction
    public int register(RegisterBo bo) throws Exception {
        Map<String, Object> map = Contract.setNftMarket();
        CryptoKeyPair keyPair = CastUtil.cast(map.get("keyPair"));
        String hexPrivateKey = keyPair.getHexPrivateKey();
        InputStream inputStream = bo.getFile().getInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        String paddedStr = String.format("%-32s", bo.getPassword()).replace(' ', '0');
        String privateKey = CryptoUtil.encryptHexPrivateKey(hexPrivateKey);
        String userPassword = CryptoUtil.encryptHexPrivateKey(paddedStr);
        String upload = IpfsUtil.upload(byteArray);
        int insert = userDaoImpl.insert(bo.getUsername(), upload, keyPair.getAddress(), privateKey, userPassword);
        NftMarket nftMarket = CastUtil.cast(map.get("nftMarket"));
        if (insert != 0) {
            TransactionReceipt register = nftMarket.regiter();
            String status = register.getStatus();
            if (Contract.checkStatus.equals(status)) {
                return 200;
            }
        }
        return 500;
    }

}
