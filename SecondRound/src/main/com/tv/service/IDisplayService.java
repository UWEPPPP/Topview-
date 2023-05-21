package tv.service;

import tv.entity.po.Nft;

import java.util.List;

/**
 * 展示服务
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public interface IDisplayService {
    /**
     * 显示nft
     *
     * @param display 显示
     * @return {@link List}<{@link Nft}>
     * @throws Exception 异常
     */
    List<Nft> display(String display) throws Exception;

    /**
     * 显示私人的nft
     *
     * @param owner 老板
     * @return {@link List}<{@link Nft}>
     * @throws Exception 异常
     */
    List<Nft> displayByUser(String owner) throws Exception;
}
