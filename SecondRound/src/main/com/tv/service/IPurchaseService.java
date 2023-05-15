package tv.service;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.service.wrapper.NftMarket;

import java.sql.SQLException;

public interface IPurchaseService {
    int buy(int id, String owner, NftMarket nftMarket) throws SQLException, ClassNotFoundException, ContractException, InterruptedException;
}
