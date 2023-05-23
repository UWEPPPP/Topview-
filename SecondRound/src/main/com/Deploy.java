import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.service.wrapper.NftMarket;
import tv.service.wrapper.Proxy;

public class Deploy {
    public static void main(String[] args) throws ContractException {
        BcosSDK sdk = BcosSDK.build("SecondRound/config-example.toml");
        Client client = sdk.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair("a7dc106e8ce088d92d00251147ab53a5822b91c9a11206b10363ba2304229580");
//        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();
//        Verifier deploy = Verifier.deploy(client, cryptoKeyPair);
//        NftStorage deploy1 = NftStorage.deploy(client, cryptoKeyPair, deploy.getContractAddress());
//        NftMarket deploy2 = NftMarket.deploy(client, cryptoKeyPair, deploy1.getContractAddress(), deploy.getContractAddress());
//        Proxy deploy3 = Proxy.deploy(client, cryptoKeyPair, deploy1.getContractAddress(), deploy.getContractAddress(), deploy2.getContractAddress());
//        deploy.setLogic(deploy3.getContractAddress());
//        deploy.setStor(deploy1.getContractAddress());
//        System.out.println("Private key: " + hexPrivateKey);
//        System.out.println("Storage address: " + deploy1.getContractAddress());
//        System.out.println("Market address: " + deploy2.getContractAddress());
//        System.out.println("Verifier address: " + deploy.getContractAddress());
//        System.out.println("Proxy address: " + deploy3.getContractAddress());
//
        NftMarket deploy = NftMarket.deploy(client, cryptoKeyPair, "0xe4d158f92bdde6d0dc3a2ba5597f74f0e3b61af5", "0xe8ea84e6aec0238beeab07c07aeffa7c58051c70");
        Proxy proxy = Proxy.load("0x941a238fd27260c955b1fa15947435ee990b7692", client, cryptoKeyPair);
        proxy.upgrade(deploy.getContractAddress());
    }
}
