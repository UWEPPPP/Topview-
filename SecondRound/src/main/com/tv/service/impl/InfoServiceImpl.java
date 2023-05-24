package tv.service.impl;

import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.dao.NftDao;
import tv.dao.UserDao;
import tv.entity.bo.UpAndDownBo;
import tv.service.IInfoService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.*;
import tv.util.IpfsUtil;

import javax.servlet.http.Part;
import java.io.InputStream;

/**
 * 信息服务
 *
 * @author 刘家辉
 * @date 2023/05/04
 */

@Component
@Scope("singleton")
@Service
public class InfoServiceImpl implements IInfoService {
    @AutoWired
    public UserDao userDaoImpl;
    @AutoWired
    public NftDao nftDaoImpl;

    @Override
    @Transaction
    public String changeInfo(String newName, Part avatar, String contractAddress) throws Exception {
        String update;
        String choice;
        if (newName != null) {
            choice = "name";
            update = newName;
        } else {
            InputStream inputStream = avatar.getInputStream();
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            update = IpfsUtil.upload(byteArray);
            choice = "profile";
            inputStream.close();
        }
        int result = userDaoImpl.updateInfo(choice, update, contractAddress);
        if (result == 0) {
            return null;
        }
        return update;
    }

    @Override
    @Transaction
    public int upAndDown(UpAndDownBo bo) throws Exception {
        boolean result = !bo.getOnSale();
        int size = nftDaoImpl.updateSold(result, bo.getCid());
        if (size == 0) {
            return 500;
        }
        return 200;
    }

    @Override
    public int changeBalance(NftMarket nftMarket) throws ContractException {
        return nftMarket.getBalance().intValue();
    }


}
