package service;

import tv.entity.po.Nft;
import junit.framework.TestCase;
import tv.service.IDisplayService;
import tv.service.impl.DisplayServiceImpl;

import java.util.List;

public class DisplayServiceImplTest extends TestCase {
    public void testDisplay() throws Exception {
        IDisplayService IDisplayService = new DisplayServiceImpl();
        List<Nft> display = IDisplayService.display("image");
        System.out.println(display);
        List<Nft> list = IDisplayService.displayByUser("0x3193dd99626bea94f2ab81c2cd772a3e38375913");
        System.out.println(list);
    }

}