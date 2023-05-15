package tv.service.impl;

import tv.entity.po.Nft;
import tv.factory.Factory;
import tv.service.IDisplayService;
import tv.spring.Component;
import tv.spring.Scope;

import java.util.List;

import static tv.util.JsonUtil.analysisJson;

@Component
@Scope("singleton")
public class DisplayServiceImpl implements IDisplayService {


    @Override
    public List<Nft> display(String display) throws Exception {
        String sql = "select * from nft.nfts where is_sold = false and type = ?";
        List<Nft> list = Factory.getInstance().iDao().select(sql, new Object[]{display}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }

    @Override
    public List<Nft> displayByUser(String owner) throws Exception {
        String sql = "select * from nft.nfts where owner = ?";
        List<Nft> list = Factory.getInstance().iDao().select(sql, new Object[]{owner}, Nft.class);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }




}
