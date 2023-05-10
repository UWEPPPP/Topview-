package service;

import entity.po.Nft;

import java.util.List;

public interface ISearchService {
    List<Nft> search(String type, String text) throws Exception;
}
