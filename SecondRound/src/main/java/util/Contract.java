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
    private static final String CONTRACT_ADDRESS ="0x76a3f31e237dcb09da731237e0cf4204e9189b0a";
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
