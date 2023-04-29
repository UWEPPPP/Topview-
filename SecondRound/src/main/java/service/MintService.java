package service;

import dao.FactoryDAO;
import entity.po.Nft;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import util.Contract;
import util.Ipfs;

import java.io.IOException;
import java.sql.SQLException;

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

    public Boolean mint(Nft nft, String jsonString) throws IOException, SQLException, ClassNotFoundException {
        String hash = Ipfs.upload(jsonString.getBytes());
        nft.setIpfsCid(hash);
        NftMarket nftMarket = Contract.getNftMarket();
        TransactionReceipt transactionReceipt = nftMarket.issueNft(hash);
        String status = transactionReceipt.getStatus();
        int insert = FactoryDAO.getNftDaoInstance().insert(nft);
        String check = ("0x0");
        return insert == 0 || !status.equals(check);
    }
}
