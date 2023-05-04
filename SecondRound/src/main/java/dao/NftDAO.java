package dao;

import entity.po.Nft;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * nft
 *
 * @author 刘家辉
 * @date 2023/04/29
 */
public class NftDAO implements  IDao{
    private NftDAO() {
    }
    public static class NftDaoHolder {
        private static final NftDAO INSTANCE = new NftDAO();
    }
    public static NftDAO getInstance() {
        return NftDaoHolder.INSTANCE;
    }
    @Override
    public Object insert(Object obj) throws SQLException, ClassNotFoundException {
        Nft nft = (Nft) obj;
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "insert into nft.nfts(name, ipfs_cid, price, type, owner, description) values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nft.getName());
        preparedStatement.setString(2, nft.getIpfsCid());
        preparedStatement.setInt(3, Integer.parseInt(nft.getPrice()));
        preparedStatement.setString(4, nft.getType());
        preparedStatement.setString(5, nft.getOwner());
        preparedStatement.setString(6, nft.getDescription());
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
    public Object update(Object obj) {
         return null;
    }

    @Override
    public Object select(Object obj) throws SQLException, ClassNotFoundException {
        return null;
    }
}
