package tv.service;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.entity.bo.UpAndDownBo;
import tv.service.wrapper.NftMarket;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

/**
 * iinfo服务
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
public interface IInfoService {
    /**
     * 改变信息
     *
     * @param newName         新名字
     * @param avatar          《阿凡达》
     * @param contractAddress 合同地址
     * @return {@link String}
     * @throws IOException            ioexception
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    String changeInfo(String newName, Part avatar, String contractAddress) throws IOException, SQLException, ClassNotFoundException;

    /**
     * 向上和向下
     * 上架和下架
     *
     * @param cid    cid
     * @param choice 选择
     * @return int
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     * @throws InterruptedException   中断异常
     */
    int upAndDown(UpAndDownBo bo) throws SQLException, ClassNotFoundException, InterruptedException;

    /**
     * 改变平衡
     *
     * @return int
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    int changeBalance(NftMarket nftMarket) throws SQLException, ClassNotFoundException, ContractException;
}
