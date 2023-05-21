package tv.service;

import tv.entity.bo.RegisterBo;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

public interface IRegisterService {
    int register(RegisterBo bo) throws IOException, SQLException, ClassNotFoundException, InterruptedException;
}
