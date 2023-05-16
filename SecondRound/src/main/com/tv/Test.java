package tv;

import tv.factory.Factory;
import tv.service.IAuctionService;

public class Test {
    public static void main(String[] args) throws Exception {
        IAuctionService auctionService = Factory.getInstance().getAuctionService();
        System.out.println(auctionService.showAuction());
    }
}
