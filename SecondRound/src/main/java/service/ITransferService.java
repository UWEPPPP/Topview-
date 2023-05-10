package service;

import entity.po.User;
import service.wrapper.NftMarket;

import java.util.List;

public interface ITransferService {
    List<User> getTransferList(String owner) throws Exception;

    int transfer(String to, String cid, String from, NftMarket nftMarket) throws Exception;
}
