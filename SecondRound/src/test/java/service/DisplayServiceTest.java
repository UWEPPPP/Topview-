package service;

import entity.po.Nft;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class DisplayServiceTest extends TestCase {
    public void testDisplay() throws Exception {
        DisplayService displayService = DisplayService.getInstance();
        List<Nft> display = displayService.display();
        System.out.println(display);
        List<Nft> list = displayService.displayByUser("0x3193dd99626bea94f2ab81c2cd772a3e38375913");
        System.out.println(list);
    }

}