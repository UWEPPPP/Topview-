package tv.controller.handler;

import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import tv.util.Logger;

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
    public static void handleIllegalArgumentException (IllegalArgumentException e, HttpServletResponse response) {
        response.setStatus(500);
        try {
            response.sendRedirect("/Jerror.html");
        } catch (IOException ex) {
            Logger.logException(Level.WARNING,"遇到无法转换的类型,需要进行修复", ex);
        }
    }

    public static void handleRuntimeException (RuntimeException e, HttpServletResponse response) {
        response.setStatus(500);
        try {
            response.sendRedirect("/Jerror.html");
        } catch (IOException ex) {
            Logger.logException(Level.WARNING,"运行逻辑异常", ex);
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

    public static void handleAllException(Exception e, HttpServletResponse response) {
        if(e instanceof  IllegalArgumentException){
            handleIllegalArgumentException((IllegalArgumentException) e, response);
        }else if(e instanceof RuntimeException) {
            handleRuntimeException((RuntimeException) e, response);
        } else if(e instanceof ContractException) {
            handleContractException((ContractException) e, response);
        } else {
            response.setStatus(500);
            try {
                response.sendRedirect("/error.html");
            } catch (IOException ex) {
                Logger.logException(Level.WARNING,"未知异常", ex);
            }
        }
    }
}