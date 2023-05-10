package factory;

import dao.Dao;
import dao.IDao;

/**
 * 工厂刀
 *
 * @author 刘家辉
 * @date 2023/05/08
 */
public class FactoryDao {
    public static IDao getDao() {
        return Dao.getInstance();
    }
}
