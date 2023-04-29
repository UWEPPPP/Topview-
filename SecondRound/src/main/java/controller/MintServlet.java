package controller;

import com.alibaba.fastjson.JSON;
import entity.po.Nft;
import entity.po.User;
import org.apache.commons.io.IOUtils;
import service.Factory;
import service.MintService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuJiaHui
 */
@WebServlet("/mint")
@MultipartConfig
public class MintServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String type = req.getParameter("type");
        String price = req.getParameter("price");
        Part file = req.getPart("file");
        if(name==null||name.trim().isEmpty()||
                description==null||description.trim().isEmpty()||
                type==null||type.trim().isEmpty()||
                price==null||price.trim().isEmpty()||
                file==null){
            resp.sendRedirect("mint.html?铸造失败");
            return;
        }
        InputStream inputStream = file.getInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        User user = (User) req.getSession().getAttribute("user");
        String contractAddress = user.getContract_address();
        Map<String,Object> map = new HashMap<>(3);
        map.put("name",name);
        map.put("description",description);
        map.put("data",byteArray);
        String jsonString = JSON.toJSONString(map);
        Nft nft = new Nft();
        nft.setName(name);
        nft.setDescription(description);
        nft.setType(type);
        nft.setPrice(price);
        nft.setOwner(contractAddress);
        MintService mintService = Factory.getMintService();
        try {
            Boolean mint = mintService.mint(nft, jsonString);
            if(mint){

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
