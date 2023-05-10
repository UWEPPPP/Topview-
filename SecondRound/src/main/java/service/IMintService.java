package service;

import entity.po.Nft;
import service.wrapper.NftMarket;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

public interface IMintService {
    int mint(Nft nft, Part file, NftMarket nftMarket) throws IOException, SQLException, ClassNotFoundException;
}
