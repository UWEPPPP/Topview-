package tv.service.wrapper;

import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 代理
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
@SuppressWarnings("all")
public class Proxy extends Contract {
    public static final String[] BINARY_ARRAY = {"6080604052600160035534801561001557600080fd5b506040516102cb3803806102cb83398101604081905261003491610083565b600080546001600160a01b039485166001600160a01b031991821617909155600180549385169382169390931790925560028054919093169082161790915560048054909116331790556100e7565b600080600060608486031215610097578283fd5b83516100a2816100cf565b60208501519093506100b3816100cf565b60408501519092506100c4816100cf565b809150509250925092565b6001600160a01b03811681146100e457600080fd5b50565b6101d5806100f66000396000f3fe60806040526004361061002d5760003560e01c80630900f0101461005157806359679b0f146100715761003c565b3661003c5761003a61009c565b005b34801561004857600080fd5b5061003a61009c565b34801561005d57600080fd5b5061003a61006c36600461013b565b6100b3565b34801561007d57600080fd5b50610086610108565b6040516100939190610169565b60405180910390f35b6002546100b1906001600160a01b0316610117565b565b6004546001600160a01b031633146100e65760405162461bcd60e51b81526004016100dd9061017d565b60405180910390fd5b600280546001600160a01b0319166001600160a01b0392909216919091179055565b6002546001600160a01b031681565b3660008037600080366000845af43d6000803e808015610136573d6000f35b3d6000fd5b60006020828403121561014c578081fd5b81356001600160a01b0381168114610162578182fd5b9392505050565b6001600160a01b0391909116815260200190565b6020808252600890820152671b9bc81c9a59da1d60c21b60408201526060019056fea264697066735822122001022c541dc58932d5118608f807d2b7a40053ee24f8464d786d25d80e32682764736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"6080604052600160035534801561001557600080fd5b506040516102cc3803806102cc83398101604081905261003491610083565b600080546001600160a01b039485166001600160a01b031991821617909155600180549385169382169390931790925560028054919093169082161790915560048054909116331790556100e7565b600080600060608486031215610097578283fd5b83516100a2816100cf565b60208501519093506100b3816100cf565b60408501519092506100c4816100cf565b809150509250925092565b6001600160a01b03811681146100e457600080fd5b50565b6101d6806100f66000396000f3fe60806040526004361061002d5760003560e01c8063344a7717146100515780639b8376e41461007c5761003c565b3661003c5761003a61009c565b005b34801561004857600080fd5b5061003a61009c565b34801561005d57600080fd5b506100666100b3565b604051610073919061016a565b60405180910390f35b34801561008857600080fd5b5061003a61009736600461013c565b6100c2565b6002546100b1906001600160a01b0316610118565b565b6002546001600160a01b031681565b6004546001600160a01b031633146100f657604051636381e58960e11b81526004016100ed9061017e565b60405180910390fd5b600280546001600160a01b0319166001600160a01b0392909216919091179055565b3660008037600080366000845af43d6000803e808015610137573d6000f35b3d6000fd5b60006020828403121561014d578081fd5b81356001600160a01b0381168114610163578182fd5b9392505050565b6001600160a01b0391909116815260200190565b6020808252600890820152671b9bc81c9a59da1d60c21b60408201526060019056fea2646970667358221220e11cb3f4b58318ba4102995ae4a62724506ad98d39e142cbb33038cb67a0c8f764736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"ntro\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"veri\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"imple\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"stateMutability\":\"nonpayable\",\"type\":\"fallback\"},{\"inputs\":[],\"name\":\"_implementation\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"newCont\",\"type\":\"address\"}],\"name\":\"upgrade\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"stateMutability\":\"payable\",\"type\":\"receive\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC__IMPLEMENTATION = "_implementation";

    public static final String FUNC_UPGRADE = "upgrade";

    protected Proxy(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static Proxy load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Proxy(contractAddress, client, credential);
    }

    public static Proxy deploy(Client client, CryptoKeyPair credential, String ntro, String veri, String imple) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(ntro),
                new Address(veri),
                new Address(imple)));
        return deploy(Proxy.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }

    public String _implementation() throws ContractException {
        final Function function = new Function(FUNC__IMPLEMENTATION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt upgrade(String newCont) {
        final Function function = new Function(
                FUNC_UPGRADE,
                Arrays.<Type>asList(new Address(newCont)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] upgrade(String newCont, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_UPGRADE,
                Arrays.<Type>asList(new Address(newCont)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUpgrade(String newCont) {
        final Function function = new Function(
                FUNC_UPGRADE,
                Arrays.<Type>asList(new Address(newCont)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getUpgradeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UPGRADE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }
}
