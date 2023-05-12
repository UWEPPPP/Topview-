package service.impl;

import factory.FactoryDao;
import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.IInfoService;
import service.wrapper.NftMarket;
import util.IpfsUtil;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 信息服务
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public class InfoServiceImpl implements IInfoService {
    private InfoServiceImpl() {
    }

    public static IInfoService getInstance() {
        return InfoServiceHolder.INSTANCE;
    }

    @Override
    public String changeInfo(String newName, Part avatar, String contractAddress) throws IOException, SQLException, ClassNotFoundException {
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
        }
        String sql = "update nft.nft_user set " + choice + " = ? where contract_address = ?";
        int result = 0;
        try {
            result = FactoryDao.getDao().insertOrUpdateOrDelete(sql, new Object[]{update, contractAddress});
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (result == 0) {
            return null;
        }
        return update;
    }

    @Override
    public int upAndDown(String cid, String choice) throws SQLException, ClassNotFoundException, InterruptedException {
        boolean result = Objects.equals(choice, "false");
        String sql = "update nft.nfts set is_sold = ? where ipfs_cid = ?";
        int size = FactoryDao.getDao().insertOrUpdateOrDelete(sql, new Object[]{result, cid});
        if (size == 0) {
            return 500;
        }
        return 200;
    }

    @Override
    public int changeBalance(NftMarket nftMarket) throws ContractException {
        return nftMarket.getBalance().intValue();
    }

    public static class InfoServiceHolder {
        private static final IInfoService INSTANCE = new InfoServiceImpl();
    }

}
