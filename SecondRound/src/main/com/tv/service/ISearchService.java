package tv.service;

import tv.entity.bo.SearchBo;
import tv.entity.po.Nft;

import java.util.List;

public interface ISearchService {
    List<Nft> search(SearchBo bo) throws Exception;
}
