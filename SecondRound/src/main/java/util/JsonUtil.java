package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.po.Nft;

import java.util.List;

/**
 * json跑龙套
 *
 * @author 刘家辉
 * @date 2023/05/10
 */
public class JsonUtil {
    public static List<Nft> analysisJson(List<Nft> list) {
        for (int i = 0; i < list.size(); i++) {
            Nft nft = list.get(i);
            String ipfsCid = nft.getIpfs_cid();
            byte[] download = IpfsUtil.download(ipfsCid);
            String jsonString = new String(download);
            // 解析JSON字符串
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String data = jsonObject.getString("data");
            // 修改Nft对象的属性值
            nft.setShow(data);
            list.set(i, nft);
        }
        return list;
    }
}
