package service;

import entity.po.Nft;
import junit.framework.TestCase;

import java.util.List;

public class SearchServiceTest extends TestCase {
    public void test(){
        SearchService searchService = SearchService.getInstance();
        List<Nft> search = null;
        try {
            search = searchService.search("name","i");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(search);
    }
}