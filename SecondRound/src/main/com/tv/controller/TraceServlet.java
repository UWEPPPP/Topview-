package tv.controller;

import com.alibaba.fastjson.JSON;
import tv.factory.Factory;
import tv.service.wrapper.NftMarket;
import tv.service.wrapper.NftStorage;
import tv.util.CastUtil;
import tv.util.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * 跟踪servlet
 *
 * @author 刘家辉
 * @date 2023/05/12
 */
@WebServlet("/trace")
@MultipartConfig
public class TraceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid = req.getParameter("cid");
        if (cid == null) {
            Logger.info("追溯参数异常");
            resp.setStatus(500);
            return;
        }
        NftMarket nftMarket = CastUtil.cast(req.getSession().getAttribute("nftMarket"));
        try {
            List<NftStorage.ItemLife> life = Factory.getInstance().getTraceService().getLife(cid, nftMarket);
            String jsonString = JSON.toJSONString(life);
            System.out.println(jsonString);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (Exception e) {
            Logger.logException(Level.WARNING,"获取Nft生命周期失败", e);
        }
    }
}