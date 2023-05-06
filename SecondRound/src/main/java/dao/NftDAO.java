package dao;

import entity.po.Nft;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "insert into nft.nfts(name, ipfs_cid, price, type, owner, description,is_sold,nftId) values(?,?,?,?,?,?,false,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nft.getName());
        preparedStatement.setString(2, nft.getIpfsCid());
        preparedStatement.setInt(3, Integer.parseInt(nft.getPrice()));
        preparedStatement.setString(4, nft.getType());
        preparedStatement.setString(5, nft.getOwner());
        preparedStatement.setString(6, nft.getDescription());
        preparedStatement.setInt(7, nft.getTokenId().intValue());
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
    public Object update(Object...obj) throws ClassNotFoundException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "update nft.nfts set is_sold = true,owner = ? where nftId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, (String) obj[0]);
        preparedStatement.setInt(2, (Integer) obj[1]);
        int result = preparedStatement.executeUpdate();
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.close(preparedStatement, null);
        return result;
    }

    @Override
    public Object select(Object obj) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "select * from nft.nfts where  is_sold = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1, false);
        return selectSame(connection, preparedStatement);
    }

    private List<Nft> selectSame(Connection connection, PreparedStatement preparedStatement) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Nft> nftList = new ArrayList<>();
        while (resultSet.next()) {
            Nft nft = selectForNft(resultSet);
            nftList.add(nft);
        }
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.close(preparedStatement, resultSet);
        return nftList;
    }

    public List<Nft> selectByUser(String owner) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "select * from nft.nfts where  owner = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, owner);
        return selectSame(connection, preparedStatement);
    }

    private Nft selectForNft(ResultSet resultSet) throws SQLException {
        Nft nft = new Nft();
        nft.setName(resultSet.getString("name"));
        nft.setIpfsCid(resultSet.getString("ipfs_cid"));
        nft.setPrice(resultSet.getString("price"));
        nft.setType(resultSet.getString("type"));
        nft.setOwner(resultSet.getString("owner"));
        nft.setDescription(resultSet.getString("description"));
        nft.setTokenId(resultSet.getBigDecimal("nftId").toBigInteger());
        return nft;
    }
}
