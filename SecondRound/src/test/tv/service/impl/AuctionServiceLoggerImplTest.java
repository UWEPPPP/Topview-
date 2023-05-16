package tv.service.impl;

import org.junit.Test;
import tv.factory.Factory;
import tv.service.IAuctionService;

import static org.junit.Assert.*;

public class AuctionServiceLoggerImplTest {

    @Test
    public void showAuction() throws Exception {
        IAuctionService auctionService = Factory.getInstance().getAuctionService();
       System.out.println(auctionService.showAuction());
    }
}