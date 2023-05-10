package service;

import org.junit.Test;
import service.impl.InfoService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class InfoServiceTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void changeInfo() throws SQLException, IOException, ClassNotFoundException {
        String s = InfoService.getInstance().changeInfo("allen", null, "0x3193dd99626bea94f2ab81c2cd772a3e38375913");
        assert Objects.equals(s, "allen");
    }

    @Test
    public void upAndDown() {
    }
}