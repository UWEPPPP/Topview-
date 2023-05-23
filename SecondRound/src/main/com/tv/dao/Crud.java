package tv.dao;

import java.util.List;

/**
 * crud
 *
 * @author 刘家辉
 * @date 2023/05/23
 */
public interface Crud {
    /**
     * 插入
     *
     * @param params 参数个数
     * @param values 值
     * @param clazz  clazz
     * @return int
     * @throws Exception 异常
     */
    int insert(Class<?> clazz, String[] params, Object[] values) throws Exception;

    /**
     * 更新
     *
     * @param setParams   设置参数
     * @param whereParams 在参数个数
     * @param values      值
     * @param clazz       clazz
     * @return int
     * @throws Exception 异常
     */
    int update(Class<?> clazz, String[] setParams, String[] whereParams, Object[] values) throws Exception;

    /**
     * 删除
     *
     * @param whereParams 在参数个数
     * @param values      值
     * @param clazz       clazz
     * @return int
     * @throws Exception 异常
     */
    int delete(Class<?> clazz, String[] whereParams, Object[] values) throws Exception;

    /**
     * 选择
     *
     * @param params 参数个数
     * @param values 值
     * @param clazz  clazz
     * @return {@link List}<{@link Object}>
     * @throws Exception 异常
     */
    <T> List<T> select(Class<T> clazz, String[] params, Object[] values) throws Exception;

}
