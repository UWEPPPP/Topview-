package service;

import entity.po.Nft;
import junit.framework.TestCase;
import service.impl.DisplayService;

import java.util.List;

public class DisplayServiceTest extends TestCase {
    public void testDisplay() throws Exception {
        IDisplayService IDisplayService = DisplayService.getInstance();
        List<Nft> display = IDisplayService.display();
        System.out.println(display);
        List<Nft> list = IDisplayService.displayByUser("0x3193dd99626bea94f2ab81c2cd772a3e38375913");
        System.out.println(list);
    }

}