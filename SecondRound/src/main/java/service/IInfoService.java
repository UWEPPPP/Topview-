package service;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

public interface IInfoService {
    String changeInfo(String newName, Part avatar, String contractAddress) throws IOException, SQLException, ClassNotFoundException;

    int upAndDown(String cid, String choice) throws SQLException, ClassNotFoundException;
}
