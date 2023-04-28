package util;

import service.NftMarket;

public class Contract {
    private static final Contract INSTANCE = new Contract();
    private Contract() {}
    public static Contract getInstance() {
        return INSTANCE;
    }
    private NftMarket nftMarket;

    public NftMarket getNftMarket() {
        return nftMarket;
    }

    public void setNftMarket(NftMarket nftMarket) {
        this.nftMarket = nftMarket;
    }
}
