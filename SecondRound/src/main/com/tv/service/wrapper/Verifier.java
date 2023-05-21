package tv.service.wrapper;

import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
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
 * 验证器
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
@SuppressWarnings("all")
public class Verifier extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610373806100326000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063718570001161005b5780637185700014610108578063b5a1984114610130578063f078d65514610156578063ff776f551461017c5761007d565b806337166c1e1461008257806362b1eeea146100bc5780636e5dee19146100e2575b600080fd5b6100a86004803603602081101561009857600080fd5b50356001600160a01b03166101a2565b604080519115158252519081900360200190f35b6100a8600480360360208110156100d257600080fd5b50356001600160a01b03166101c0565b6100a8600480360360208110156100f857600080fd5b50356001600160a01b03166101d4565b61012e6004803603602081101561011e57600080fd5b50356001600160a01b03166101e8565b005b61012e6004803603602081101561014657600080fd5b50356001600160a01b0316610254565b6100a86004803603602081101561016c57600080fd5b50356001600160a01b03166102c0565b61012e6004803603602081101561019257600080fd5b50356001600160a01b03166102d4565b6001600160a01b031660009081526003602052604090205460ff1690565b6000546001600160a01b0390811691161490565b6002546001600160a01b0390811691161490565b6000546001600160a01b03163314610232576040805162461bcd60e51b81526020600482015260086024820152671b9bc81c9a59da1d60c21b604482015290519081900360640190fd5b600180546001600160a01b0319166001600160a01b0392909216919091179055565b6000546001600160a01b0316331461029e576040805162461bcd60e51b81526020600482015260086024820152671b9bc81c9a59da1d60c21b604482015290519081900360640190fd5b600280546001600160a01b0319166001600160a01b0392909216919091179055565b6001546001600160a01b0390811691161490565b6102dd336102c0565b610319576040805162461bcd60e51b81526020600482015260086024820152671b9bc81c9a59da1d60c21b604482015290519081900360640190fd5b6001600160a01b03166000908152600360205260409020805460ff1916600117905556fea2646970667358221220a826876152f0c0d05aef57e0645aa5a03982f02fb69383ca4b4cd8af48bb891064736f6c634300060a0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610376806100326000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063a78caee41161005b578063a78caee41461010a578063ce9ea5eb14610130578063ebc2c22b14610156578063fb5821501461017c5761007d565b806324793be7146100825780635d0d40df146100bc57806381ff5b15146100e2575b600080fd5b6100a86004803603602081101561009857600080fd5b50356001600160a01b03166101a2565b604080519115158252519081900360200190f35b6100a8600480360360208110156100d257600080fd5b50356001600160a01b03166101c0565b610108600480360360208110156100f857600080fd5b50356001600160a01b03166101d4565b005b6101086004803603602081101561012057600080fd5b50356001600160a01b0316610241565b6101086004803603602081101561014657600080fd5b50356001600160a01b03166102ab565b6100a86004803603602081101561016c57600080fd5b50356001600160a01b0316610318565b6100a86004803603602081101561019257600080fd5b50356001600160a01b031661032c565b6001600160a01b031660009081526003602052604090205460ff1690565b6000546001600160a01b0390811691161490565b6000546001600160a01b0316331461021f5760408051636381e58960e11b81526020600482015260086024820152671b9bc81c9a59da1d60c21b604482015290519081900360640190fd5b600180546001600160a01b0319166001600160a01b0392909216919091179055565b61024a3361032c565b6102875760408051636381e58960e11b81526020600482015260086024820152671b9bc81c9a59da1d60c21b604482015290519081900360640190fd5b6001600160a01b03166000908152600360205260409020805460ff19166001179055565b6000546001600160a01b031633146102f65760408051636381e58960e11b81526020600482015260086024820152671b9bc81c9a59da1d60c21b604482015290519081900360640190fd5b600280546001600160a01b0319166001600160a01b0392909216919091179055565b6002546001600160a01b0390811691161490565b6001546001600160a01b039081169116149056fea26469706673582212209a890c627ea30aa02167c54981ef89ed6346367b8405b7b1e2ddfe24bf7e6ed064736f6c634300060a0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"is_logic\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"is_logic_Admin\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"is_regist\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"is_stora\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"regist\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"setLogic\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"setStor\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_IS_LOGIC = "is_logic";

    public static final String FUNC_IS_LOGIC_ADMIN = "is_logic_Admin";

    public static final String FUNC_IS_REGIST = "is_regist";

    public static final String FUNC_IS_STORA = "is_stora";

    public static final String FUNC_REGIST = "regist";

    public static final String FUNC_SETLOGIC = "setLogic";

    public static final String FUNC_SETSTOR = "setStor";

    protected Verifier(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static Verifier load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Verifier(contractAddress, client, credential);
    }

    public static Verifier deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Verifier.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public Boolean is_logic(String addr) throws ContractException {
        final Function function = new Function(FUNC_IS_LOGIC,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public Boolean is_logic_Admin(String addr) throws ContractException {
        final Function function = new Function(FUNC_IS_LOGIC_ADMIN,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public Boolean is_regist(String addr) throws ContractException {
        final Function function = new Function(FUNC_IS_REGIST,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public Boolean is_stora(String addr) throws ContractException {
        final Function function = new Function(FUNC_IS_STORA,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public TransactionReceipt regist(String addr) {
        final Function function = new Function(
                FUNC_REGIST,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] regist(String addr, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGIST,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegist(String addr) {
        final Function function = new Function(
                FUNC_REGIST,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getRegistInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGIST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }

    public TransactionReceipt setLogic(String addr) {
        final Function function = new Function(
                FUNC_SETLOGIC,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] setLogic(String addr, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETLOGIC,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetLogic(String addr) {
        final Function function = new Function(
                FUNC_SETLOGIC,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSetLogicInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETLOGIC,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }

    public TransactionReceipt setStor(String addr) {
        final Function function = new Function(
                FUNC_SETSTOR,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] setStor(String addr, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETSTOR,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetStor(String addr) {
        final Function function = new Function(
                FUNC_SETSTOR,
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSetStorInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETSTOR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }
}
