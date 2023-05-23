package tv.dao.impl;

import tv.dao.Dao;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Scope;
import tv.dao.util.SqlBuilder;

import java.util.List;

/**
 * crud impl
 *
 * @author 刘家辉
 * @date 2023/05/23
 */
@Component
@Scope("singleton")
public class CrudImpl implements tv.dao.Crud {
    @AutoWired
    public Dao daoImpl;

    @Override
    public int insert(Class<?> clazz, String[] params, Object[] values) throws Exception {
        String sqlToInsert = new SqlBuilder().insert("nft." + clazz.getSimpleName().toLowerCase()).
                setInsert(params).
                buildInsert();
        return daoImpl.insertOrUpdateOrDelete(sqlToInsert, values);
    }

    @Override
    public int update(Class<?> clazz, String[] setParams, String[] whereParams, Object[] values) throws Exception {
        String sql1 = new SqlBuilder().update("nft." + clazz.getSimpleName().toLowerCase()).
                set(setParams).
                where(whereParams).
                buildUpdate();
        return daoImpl.insertOrUpdateOrDelete(sql1, values);
    }

    @Override
    public int delete(Class<?> clazz, String[] whereParams, Object[] values) throws Exception {
        String sql = new SqlBuilder().delete("nft." + clazz.getSimpleName().toLowerCase()).
                where(whereParams).
                buildDelete();
        return daoImpl.insertOrUpdateOrDelete(sql, values);
    }

    @Override
    public <T> List<T> select(Class<T> clazz, String[] params, Object[] values) throws Exception {
        String sql = new SqlBuilder().select("*").
                from("nft." + clazz.getSimpleName().toLowerCase()).
                where(params).
                buildSelect();
        return daoImpl.select(sql, values, clazz);

    }
}
