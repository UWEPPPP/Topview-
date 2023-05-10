package service;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

public interface IRegisterService {
    int register(String username, String password, Part avatarPart) throws IOException, SQLException, ClassNotFoundException;
}
