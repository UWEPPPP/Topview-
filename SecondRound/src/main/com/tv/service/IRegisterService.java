package tv.service;

import tv.entity.bo.RegisterBo;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 注册服务
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public interface IRegisterService {
    /**
     * 注册
     *
     * @param bo 薄
     * @return int
     * @throws IOException            ioexception
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     * @throws InterruptedException   中断异常
     */
    int register(RegisterBo bo) throws IOException, SQLException, ClassNotFoundException, InterruptedException;
}
