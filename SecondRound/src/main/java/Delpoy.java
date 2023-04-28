
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
        CryptoKeyPair cryptoKeyPair1 = client.getCryptoSuite().createKeyPair();
        NftMarket load = NftMarket.load(deploy.getContractAddress(), client, cryptoKeyPair1);
        CryptoKeyPair cryptoKeyPair11 = client.getCryptoSuite().createKeyPair();
        NftMarket load1 = NftMarket.load(deploy.getContractAddress(), client, cryptoKeyPair11);
        TransactionReceipt regiter = load.regiter();
        TransactionReceipt regiter1 = deploy.regiter();
        TransactionReceipt regiter2 = load1.regiter();
        String status2 = regiter2.getStatus();
        String status1 = regiter1.getStatus();
        BigInteger balance = load.getBalance();
        String status = regiter.getStatus();
        System.out.println("status = " + status);
        System.out.println("balance = " + balance);
        System.out.println("status1 = " + status1);
        System.out.println("status2 = " + status2);
    }
}
