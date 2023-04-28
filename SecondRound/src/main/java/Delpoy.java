
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import service.NftMarket;

import java.math.BigInteger;

public class Delpoy {
    public static void main(String[] args) throws ContractException {
        BcosSDK sdk = BcosSDK.build("SecondRound/config-example.toml");
        Client client = sdk.getClient(1);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        String hexPrivateKey = cryptoKeyPair.getHexPrivateKey();
        NftMarket deploy = NftMarket.deploy(client, cryptoKeyPair);
        System.out.println("hexPrivateKey = " + hexPrivateKey);
        System.out.println("合约地址 = " + deploy.getContractAddress());
        
    }
}
