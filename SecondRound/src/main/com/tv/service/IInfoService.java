package tv.service;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.service.wrapper.NftMarket;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

public interface IInfoService {
    String changeInfo(String newName, Part avatar, String contractAddress) throws IOException, SQLException, ClassNotFoundException;

    int upAndDown(String cid, String choice) throws SQLException, ClassNotFoundException, InterruptedException;

    /**
     * 改变平衡
     *
     * @param nftMarket 非功能性测试市场
     * @return int
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    int changeBalance(NftMarket nftMarket) throws SQLException, ClassNotFoundException, ContractException;
}
