package tv.service.impl;

import tv.dao.NftDao;
import tv.entity.po.Nft;
import tv.service.IDisplayService;
import tv.spring.annotate.*;

import java.util.List;

import static tv.util.JsonUtil.analysisJson;

/**
 * 显示服务impl
 *
 * @author 刘家辉
 * @date 2023/05/16
 */
@Component
@Scope("singleton")
@Service
public class DisplayServiceImpl implements IDisplayService {
    @AutoWired
    public NftDao nftDaoImpl;

    @Override
    public List<Nft> display(String display) throws Exception {
        List<Nft> list = nftDaoImpl.selectWhichChoice(display);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }

    @Override
    public List<Nft> displayByUser(String owner) throws Exception {
        List<Nft> list = nftDaoImpl.selectByOwner(owner);
        if (list.size() == 0) {
            return null;
        }
        return analysisJson(list);
    }


}
