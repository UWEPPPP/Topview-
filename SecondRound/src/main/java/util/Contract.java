package util;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import service.NftMarket;

public class Contract {

    private static final BcosSDK SDK = BcosSDK.build("D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\config-example.toml");
    private static final Client CLIENT = SDK.getClient(1);
    private static final CryptoSuite CRYPTO_SUITE = CLIENT.getCryptoSuite();
    private static final String CONTRACT_ADDRESS ="0x83052261624b7ef8596431f3708c5259b68876ea";
    private static NftMarket nftMarket;

    public static void setNftMarket(String privateKey){
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair(privateKey);
        nftMarket = NftMarket.load(CONTRACT_ADDRESS, CLIENT, keyPair);
    }

    public static CryptoKeyPair setNftMarket() {
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair();
        nftMarket = NftMarket.load(CONTRACT_ADDRESS, CLIENT, keyPair);
        return keyPair;
    }

    public static NftMarket getNftMarket() {
        return nftMarket;
    }
}
