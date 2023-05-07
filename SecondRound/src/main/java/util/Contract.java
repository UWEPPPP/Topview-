package util;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import service.wrapper.NftMarket;

import java.util.HashMap;
import java.util.Map;

public class Contract {

    private static final BcosSDK SDK = BcosSDK.build("D:\\AE\\blockchain-liujiahui-Traceability-SecondRound\\SecondRound\\config-example.toml");
    private static final Client CLIENT = SDK.getClient(1);
    private static final CryptoSuite CRYPTO_SUITE = CLIENT.getCryptoSuite();
    private static final String CONTRACT_ADDRESS ="0x939f33037351103a0fa3b7a8472890d335725698";

    public static NftMarket setNftMarket(String privateKey){
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair(privateKey);
        return NftMarket.load(CONTRACT_ADDRESS, CLIENT, keyPair);
    }

    public static Map<String,Object> setNftMarket() {
        CryptoKeyPair keyPair = CRYPTO_SUITE.createKeyPair();
        Map<String,Object> map=new HashMap<>(2);
        map.put("nftMarket",NftMarket.load(CONTRACT_ADDRESS, CLIENT, keyPair));
        map.put("keyPair",keyPair);
        return map;
    }

}
