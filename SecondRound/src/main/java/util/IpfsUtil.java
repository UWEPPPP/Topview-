package util;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

/**
 * ipf
 *
 * @author 刘家辉
 * @date 2023/04/26
 */
public class IpfsUtil {
    public static final IPFS IPFS = new IPFS("/ip4/127.0.0.1/tcp/5001");

    public static String upload(byte[] data) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(data);
        MerkleNode addResult = IPFS.add(file).get(0);
        return addResult.hash.toString();
    }

    public static byte[] download(String hash) {
        byte[] data = null;
        try {
            data = IPFS.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            Logger.logException(Level.SEVERE, "下载文件失败", e);
        }
        return data;
    }


}
