package factory;

import service.*;
import service.impl.*;

/**
 * 工厂
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public class FactoryService {
    public static IRegisterService getRegisterService() {
        return
                (IRegisterService) ProxyFactory.createProxy(RegisterService.getInstance());
    }

    public static ILoginService getLoginService() {
        return (ILoginService) ProxyFactory.createProxy(LoginService.getInstance());
    }

    public static IMintService getMintService() {
        return (IMintService) ProxyFactory.createProxy(MintService.getInstance());
    }

    public static IInfoService getInfoService() {
        return (IInfoService) ProxyFactory.createProxy(InfoService.getInstance());
    }

    public static IDisplayService getDisplayService() {
        return (IDisplayService) ProxyFactory.createProxy(DisplayService.getInstance());
    }

    public static ITraceService getTraceService() {
        return (ITraceService) ProxyFactory.createProxy(TraceService.getInstance());
    }

    public static IPurchaseService getPurchaseService() {
        return
                (IPurchaseService) ProxyFactory.createProxy(PurchaseService.getInstance());
    }

    public static ITransferService getTransferService() {
        return (ITransferService) ProxyFactory.createProxy(TransferService.getInstance());
    }

    public static ISearchService getSearchService() {
        return (ISearchService) ProxyFactory.createProxy(SearchService.getInstance());
    }

    public static IAuctionService getAuctionService() {
        return (IAuctionService) ProxyFactory.createProxy(AuctionService.getInstance() );
    }

}
