package dao;

import entity.po.User;
import util.CastUtil;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 用户刀
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public class UserDAO implements IDao {
    private UserDAO() {

    }
    public static UserDAO getInstance(){
        return UserDaoHolder.INSTANCE;
    }
    public  static  class  UserDaoHolder{
        private static final UserDAO INSTANCE = new UserDAO();
    }

    @Override
    public Object insert(Object obj) throws SQLException, ClassNotFoundException {
        User user = (User) obj;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into nft.nft_user(name, profile, contract_address, private_key, balance, password) values(?,?,?,?,?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getProfile());
        preparedStatement.setString(3, user.getContractAddress());
        preparedStatement.setString(4, user.getPrivateKey());
        preparedStatement.setInt(5, Integer.parseInt(user.getBalance()));
        preparedStatement.setString(6, user.getPassword());
        int result = preparedStatement.executeUpdate();
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.close(preparedStatement, null);
        return result;
    }

    @Override
    public Object delete(Object obj) {
         return null;

    }

    @Override
    public Object update(Object obj) throws ClassNotFoundException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Map<String,Object> map = CastUtil.cast(obj);
        PreparedStatement preparedStatement = connection.prepareStatement("update nft.nft_user set "+ map.get("choice") +" = ? where contract_address = ?");
        preparedStatement.setString(1, (String)map.get("update"));
        preparedStatement.setString(2, (String)map.get("contractAddress"));
        int result = preparedStatement.executeUpdate();
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.close(preparedStatement, null);
        return result;
    }

    @Override
    public User select(Object obj) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from nft.nft_user where name = ? and password = ?");
        preparedStatement.setString(1, ((User) obj).getName());
        preparedStatement.setString(2, ((User) obj).getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setProfile(resultSet.getString("profile"));
            user.setContractAddress(resultSet.getString("contract_address"));
            user.setPrivateKey(resultSet.getString("private_key"));
            user.setBalance(resultSet.getString("balance"));
            user.setPassword(resultSet.getString("password"));
            ConnectionPool.getInstance().releaseConnection(connection);
            ConnectionPool.close(preparedStatement, resultSet);
            return user;
        }else {
            return null;
        }
    }
}
