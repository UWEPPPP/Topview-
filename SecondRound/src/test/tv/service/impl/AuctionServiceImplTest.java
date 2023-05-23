package tv.service.impl;

import org.junit.Test;
import tv.entity.dto.AuctionDto;
import tv.factory.HandlerFactory;
import tv.service.IAuctionService;

import java.util.List;

public class AuctionServiceImplTest {

    @Test
    public void showAuction() throws Exception {
        HandlerFactory instance = HandlerFactory.getInstance();
        List<AuctionDto> auctionDtos = ((IAuctionService) (instance.getBean("auctionServiceImpl"))).showAuction();
        System.out.println(auctionDtos);
    }

    @Test
    public void offer() {
    }

    @Test
    public void auctionBegin() {
    }
}