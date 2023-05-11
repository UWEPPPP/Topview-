package service;

import entity.po.Nft;

import java.util.List;

public interface IDisplayService {
    List<Nft> display(String display) throws Exception;

    List<Nft> displayByUser(String owner) throws Exception;
}
