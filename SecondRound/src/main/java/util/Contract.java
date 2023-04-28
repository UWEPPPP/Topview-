package util;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import service.NftMarket;

import java.security.KeyPair;

public class Contract {

    private static final BcosSDK SDK = BcosSDK.build("D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\config-example.toml");
    private static final Client CLIENT = SDK.getClient(1);
    private static final CryptoSuite CRYPTO_SUITE = CLIENT.getCryptoSuite();
    private static final Contract INSTANCE = new Contract();
    private static NftMarket nftMarket;

    public static void setNftMarket(String privateKey){
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair(privateKey);
        nftMarket = NftMarket.load("0xb32041f6200e9f79ab3b53426a678b4621e0f237", CLIENT, keyPair);
    }

    public static CryptoKeyPair setNftMarket() {
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair();
        nftMarket = NftMarket.load("0xb32041f6200e9f79ab3b53426a678b4621e0f237", CLIENT, keyPair);
        return keyPair;
    }

    public static NftMarket getNftMarket() {
        return nftMarket;
    }
}
