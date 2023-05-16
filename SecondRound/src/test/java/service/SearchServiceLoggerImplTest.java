package service;

import tv.entity.po.Nft;
import junit.framework.TestCase;
import tv.service.ISearchService;
import tv.service.impl.SearchServiceImpl;

import java.util.List;

public class SearchServiceLoggerImplTest extends TestCase {
    public void test() {
        ISearchService ISearchService = new SearchServiceImpl();
        List<Nft> search = null;
        try {
            search = ISearchService.search("name", "i");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(search);
    }
}