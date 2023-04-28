package dao;

import java.sql.SQLException;

public interface IDao {
    public int insert(Object obj) throws SQLException;
    public void delete(Object obj);
    public void update(Object obj);
    public Object select(Object obj) throws SQLException;
}
