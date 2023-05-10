import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.wrapper.NftMarket;
import service.wrapper.NftStorage;
import service.wrapper.Proxy;
import service.wrapper.Verifier;

public class Delpoy {
    public static void main(String[] args) throws ContractException {
        BcosSDK sdk = BcosSDK.build("SecondRound/config-example.toml");
        Client client = sdk.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();
        Verifier deploy = Verifier.deploy(client, cryptoKeyPair);
        NftStorage deploy1 = NftStorage.deploy(client, cryptoKeyPair, deploy.getContractAddress());
        NftMarket deploy2 = NftMarket.deploy(client, cryptoKeyPair, deploy1.getContractAddress(), deploy.getContractAddress());
        Proxy deploy3 = Proxy.deploy(client, cryptoKeyPair, deploy1.getContractAddress(), deploy.getContractAddress(), deploy2.getContractAddress());
        deploy.setLogic(deploy3.getContractAddress());
        deploy.setStor(deploy1.getContractAddress());
        System.out.println("Private key: " + hexPrivateKey);
        System.out.println("Storage address: " + deploy1.getContractAddress());
        System.out.println("Market address: " + deploy2.getContractAddress());
        System.out.println("Verifier address: " + deploy.getContractAddress());
        System.out.println("Proxy address: " + deploy3.getContractAddress());
    }
}
