package tv.util;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import tv.service.wrapper.NftMarket;

import java.util.HashMap;
import java.util.Map;

public class Contract {
    private static final BcosSDK SDK = BcosSDK.build("D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\config-example.toml");
    private static final Client CLIENT = SDK.getClient(1);
    private static final CryptoSuite CRYPTO_SUITE = CLIENT.getCryptoSuite();
    private static final CryptoKeyPair ADMIN = CRYPTO_SUITE.createKeyPair("a7dc106e8ce088d92d00251147ab53a5822b91c9a11206b10363ba2304229580");
    private static final String CONTRACT_ADDRESS = "0x941a238fd27260c955b1fa15947435ee990b7692";
    public static String checkStatus = "0x0";

    public static NftMarket getAdmin() {
        return NftMarket.load(CONTRACT_ADDRESS, CLIENT, ADMIN);
    }

    public static NftMarket setNftMarket(String privateKey) {
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair(privateKey);
        return NftMarket.load(CONTRACT_ADDRESS, CLIENT, keyPair);
    }

    public static Map<String, Object> setNftMarket() {
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair();
        Map<String, Object> map = new HashMap<>(2);
        map.put("nftMarket", NftMarket.load(CONTRACT_ADDRESS, CLIENT, keyPair));
        map.put("keyPair", keyPair);
        return map;
    }

}