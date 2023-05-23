package tv.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import tv.dao.NftDao;
import tv.entity.bo.MintNftBo;
import tv.service.IMintService;
import tv.service.wrapper.NftMarket;
import tv.spring.annotate.*;
import tv.util.Contract;
import tv.util.IpfsUtil;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 薄荷服务
 *
 * @author 刘家辉
 * @date 2023/04/29
 */

@Component
@Scope("singleton")
@SecurityLogger
@Service
public class MintServiceImpl implements IMintService {
    @AutoWired
    public NftDao nftDaoImpl;

    @Override
    public int mint(MintNftBo bo, NftMarket nftMarket) throws Exception {
        InputStream inputStream = bo.getFile().getInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        String upload = IpfsUtil.upload(byteArray);
        inputStream.close();
        Map<String, Object> map = new HashMap<>(3);
        map.put("name", bo.getName());
        map.put("description", bo.getDescription());
        map.put("data", upload);
        String jsonString = JSON.toJSONString(map);
        String hash = IpfsUtil.upload(jsonString.getBytes());
        bo.setIpfs_cid(hash);
        TransactionReceipt transactionReceipt = nftMarket.issueNft(hash, BigInteger.valueOf(bo.getPrice()));
        Tuple1<BigInteger> issueNftOutput = nftMarket.getIssueNftOutput(transactionReceipt);
        bo.setNftId(issueNftOutput.getValue1().intValue());
        String status = transactionReceipt.getStatus();
        if (status.equals(Contract.checkStatus)) {
            int insert = nftDaoImpl.insert(bo.getName(), bo.getIpfs_cid(), bo.getPrice(), bo.getType(), bo.getOwner(), bo.getDescription(), false, bo.getNftId());
            if (insert != 0) {
                return 200;
            }
        }
        return 500;
    }


}
