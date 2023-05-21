package tv.service;

import tv.entity.bo.SearchBo;
import tv.entity.po.Nft;

import java.util.List;

/**
 * 查找藏品服务
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public interface ISearchService {
    /**
     * 搜索
     *
     * @param bo 薄
     * @return {@link List}<{@link Nft}>
     * @throws Exception 异常
     */
    List<Nft> search(SearchBo bo) throws Exception;
}
