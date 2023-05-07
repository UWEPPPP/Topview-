package service;

import dao.FactoryDAO;
import dao.UserDAO;
import entity.po.User;
import org.apache.commons.io.IOUtils;
import util.CastUtil;
import util.Ipfs;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 信息服务
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public class InfoService {
    private InfoService() {
    }
    public static class InfoServiceHolder {
        private static final InfoService INSTANCE = new InfoService();
    }
    public static InfoService getInstance() {
        return InfoServiceHolder.INSTANCE;
    }

    public String changeInfo(String newName, Part avatar, String contractAddress) throws IOException, SQLException, ClassNotFoundException {
        Map<String,Object> map = new HashMap<>(3);
        String update;
        if(newName!=null){
           map.put("choice","name");
           update=newName;
        }else {
            InputStream inputStream = avatar.getInputStream();
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            update = Ipfs.upload(byteArray);
            map.put("choice","profile");
        }
        map.put("update",update);
        map.put("contractAddress",contractAddress);
        int result =(int) FactoryDAO.getUserDaoInstance().update(map);
        if(result==0){
            return null;
        }
        return update;
    }

    public int upAndDown(String cid,String choice) throws SQLException, ClassNotFoundException {
        boolean result= Objects.equals(choice, "false");
        int size= CastUtil.cast(FactoryDAO.getNftDaoInstance().updateUpAndDown(cid,result));
        if(size==0){
            return 500;
        }
        return 200;
    }

}
