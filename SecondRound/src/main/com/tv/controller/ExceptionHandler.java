package tv.controller;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.util.Logger;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

/**
 * 异常处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */
public class ExceptionHandler {
    public  static void handleInputException(InputException e, HttpServletResponse response)  {
        response.setStatus(400);
        try {
            response.getWriter().write("输入为空");
        } catch (IOException ex) {
            Logger.logException(Level.WARNING,"用户输入异常", ex);
        }
    }

    public static void handleNullPointException(NullPointerException e, HttpServletResponse response) {
        response.setStatus(500);
        try {
            response.sendRedirect("/Jerror.html");
        } catch (IOException ex) {
            Logger.logException(Level.WARNING,"空指针异常", ex);
        }
    }

    public static void handleContractException(ContractException e, HttpServletResponse response) {
        response.setStatus(500);
        try {
            response.sendRedirect("/BLCerror.html");
        } catch (IOException ex) {
            Logger.logException(Level.WARNING,"合约异常", ex);
        }
    }

    public static void handleException(Exception e, HttpServletResponse response) {
        Logger.logException(Level.WARNING,"未知异常", e);
    }
}
