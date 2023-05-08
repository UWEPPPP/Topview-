package service;

import dao.FactoryDao;
import org.apache.commons.io.IOUtils;
import util.IpfsUtil;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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
        String update;
        String choice;
        if(newName!=null){
           choice="name";
           update=newName;
        }else {
            InputStream inputStream = avatar.getInputStream();
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            update = IpfsUtil.upload(byteArray);
            choice="profile";
        }
        String sql= "update nft.nft_user set "+ choice +" = ? where contract_address = ?";
        int result = FactoryDao.getDao().insertOrUpdateOrDelete(sql,new Object[]{update,contractAddress});
        if(result==0){
            return null;
        }
        return update;
    }

    public int upAndDown(String cid,String choice) throws SQLException, ClassNotFoundException {
        boolean result= Objects.equals(choice, "false");
        String sql= "update nft.nfts set is_sold = ? where cid = ?";
        int size= FactoryDao.getDao().insertOrUpdateOrDelete(sql,new Object[]{false,cid});
        if(size==0){
            return 500;
        }
        return 200;
    }

}
