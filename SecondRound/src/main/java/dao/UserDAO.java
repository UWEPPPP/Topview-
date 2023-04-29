package dao;

import entity.po.User;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IDao {
    private UserDAO() {

    }
    public static UserDAO getInstance(){
        return UserDAOHolder.INSTANCE;
    }
    public  static  class  UserDAOHolder{
        private static final UserDAO INSTANCE = new UserDAO();
    }

    @Override
    public int insert(Object obj) throws SQLException, ClassNotFoundException {
        User user = (User) obj;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into nft.nft_user(name, profile, contract_address, private_key, balance, password) values(?,?,?,?,?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getProfile());
        preparedStatement.setString(3, user.getContract_address());
        preparedStatement.setString(4, user.getPrivate_key());
        preparedStatement.setInt(5, Integer.parseInt(user.getBalance()));
        preparedStatement.setString(6, user.getPassword());
        int result = preparedStatement.executeUpdate();
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.close(preparedStatement, null);
        return result;
    }

    @Override
    public void delete(Object obj) {

    }

    @Override
    public void update(Object obj) {

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
            user.setContract_address(resultSet.getString("contract_address"));
            user.setPrivate_key(resultSet.getString("private_key"));
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
