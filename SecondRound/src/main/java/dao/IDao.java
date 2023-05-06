package dao;

import java.sql.SQLException;

public interface IDao {
    public Object insert(Object obj) throws SQLException, ClassNotFoundException;
    public Object delete(Object obj);
    public Object update(Object...obj) throws ClassNotFoundException, SQLException;
    public Object select(Object obj) throws SQLException, ClassNotFoundException;
}
