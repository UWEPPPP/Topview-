package service;

import com.alibaba.fastjson.JSON;
import dao.FactoryDAO;
import entity.po.Nft;
import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import util.CastUtil;
import util.Contract;
import util.Ipfs;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 薄荷服务
 *
 * @author 刘家辉
 * @date 2023/04/29
 */
public class MintService {
    private MintService() {
    }
    public static class MintServiceHolder {
        private static final MintService INSTANCE = new MintService();
    }
    public static MintService getInstance() {
        return MintServiceHolder.INSTANCE;
    }

    public int mint(Nft nft, Part file) throws IOException, SQLException, ClassNotFoundException {
        InputStream inputStream = file.getInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        Map<String,Object> map = new HashMap<>(3);
        map.put("name",nft.getName());
        map.put("description",nft.getDescription());
        map.put("data",byteArray);
        String jsonString = JSON.toJSONString(map);
        String hash = Ipfs.upload(jsonString.getBytes());
        nft.setIpfsCid(hash);
        NftMarket nftMarket = Contract.getNftMarket();
        TransactionReceipt transactionReceipt = nftMarket.issueNft(hash);
        String status = transactionReceipt.getStatus();
        int insert = CastUtil.cast(FactoryDAO.getNftDaoInstance().insert(nft));
        String check = ("0x0");
        return insert != 0 || status.equals(check)? 200:500;
    }
}
