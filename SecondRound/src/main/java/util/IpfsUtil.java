package util;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
            e.printStackTrace();
        }
        return data;
    }

    public static void download(String hash, String destFile) {
        byte[] data = null;
        try {
            data = IPFS.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        check(destFile, data);
    }

    public static void check(String destFile, byte[] data) {
        if (data != null && data.length > 0) {
            File file = new File(destFile);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
