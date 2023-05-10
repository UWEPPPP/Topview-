package service.impl;

import com.alibaba.fastjson.JSON;
import entity.po.Nft;
import factory.FactoryDao;
import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import service.IMintService;
import service.wrapper.NftMarket;
import util.Contract;
import util.IpfsUtil;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 薄荷服务
 *
 * @author 刘家辉
 * @date 2023/04/29
 */
public class MintService implements IMintService {
    private MintService() {
    }

    public static IMintService getInstance() {
        return MintServiceHolder.INSTANCE;
    }

    @Override
    public int mint(Nft nft, Part file, NftMarket nftMarket) throws IOException, SQLException, ClassNotFoundException {
        InputStream inputStream = file.getInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        String upload = IpfsUtil.upload(byteArray);
        inputStream.close();
        Map<String, Object> map = new HashMap<>(3);
        map.put("name", nft.getName());
        map.put("description", nft.getDescription());
        map.put("data", upload);
        String jsonString = JSON.toJSONString(map);
        String hash = IpfsUtil.upload(jsonString.getBytes());
        nft.setIpfs_cid(hash);
        TransactionReceipt transactionReceipt = nftMarket.issueNft(hash, BigInteger.valueOf(nft.getPrice()));
        Tuple1<BigInteger> issueNftOutput = nftMarket.getIssueNftOutput(transactionReceipt);
        nft.setNftId(issueNftOutput.getValue1().intValue());
        String status = transactionReceipt.getStatus();
        String sql = "insert into nft.nfts(name, ipfs_cid, price, type, owner, description,is_sold,nftId) values(?,?,?,?,?,?,false,?)";
        int insert = FactoryDao.getDao().insertOrUpdateOrDelete(sql, new Object[]{nft.getName(), nft.getIpfs_cid(), nft.getPrice(), nft.getType(), nft.getOwner(), nft.getDescription(), nft.getNftId()});
        return insert != 0 || status.equals(Contract.checkStatus) ? 200 : 500;
    }

    public static class MintServiceHolder {
        private static final IMintService INSTANCE = new MintService();
    }
}
