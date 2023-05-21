package tv.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * dao接口
 *
 * @author 刘家辉
 * @date 2023/05/08
 */
public interface IDao {
    /**
     * 插入或更新或删除
     *
     * @param sql     sql
     * @param objects 对象
     * @return int
     * @throws SQLException           sqlexception异常
     * @throws InterruptedException   中断异常
     */
    int insertOrUpdateOrDelete(String sql, Object[] objects) throws SQLException, InterruptedException;

    /**
     * 选择
     * 选择更多
     *
     * @param sql     sql
     * @param objects 对象
     * @param tClass  t类
     * @return {@link List}<{@link T}>
     * @throws Exception 异常
     */
    <T> List<T> select(String sql, Object[] objects, Class<T> tClass) throws Exception;
}
