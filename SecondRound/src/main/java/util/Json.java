package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.po.Nft;

import java.sql.SQLException;
import java.util.List;

public class Json {
    public static List<Nft> analysisJson(List<Nft> list) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < list.size(); i++) {
            Nft nft = list.get(i);
            String ipfsCid = nft.getIpfsCid();
            byte[] download = Ipfs.download(ipfsCid);
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
