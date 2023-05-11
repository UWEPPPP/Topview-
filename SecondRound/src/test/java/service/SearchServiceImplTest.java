package service;

import entity.po.Nft;
import junit.framework.TestCase;
import service.impl.SearchServiceImpl;

import java.util.List;

public class SearchServiceImplTest extends TestCase {
    public void test() {
        ISearchService ISearchService = SearchServiceImpl.getInstance();
        List<Nft> search = null;
        try {
            search = ISearchService.search("name", "i");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(search);
    }
}