package controller;

import entity.po.Nft;
import entity.po.User;
import service.FactoryService;
import service.MintService;
import service.wrapper.NftMarket;
import util.CastUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

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
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String type = req.getParameter("type");
        String price = req.getParameter("price");
        Part file = req.getPart("file");
        if (name == null || name.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                type == null || type.trim().isEmpty() ||
                price == null || price.trim().isEmpty() ||
                file == null) {
            resp.sendRedirect("mint.html?铸造失败");
            return;
        }
        User user = CastUtil.cast( req.getSession().getAttribute("user"));
        NftMarket market= CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        String contractAddress = user.getContract_address();
        Nft nft = new Nft();
        nft.setName(name);
        nft.setDescription(description);
        nft.setType(type);
        nft.setPrice(Integer.parseInt(price));
        nft.setOwner(contractAddress);
        MintService mintService = FactoryService.getMintService();
        try {
            int result = mintService.mint(nft, file,market);
            resp.setStatus(result);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
