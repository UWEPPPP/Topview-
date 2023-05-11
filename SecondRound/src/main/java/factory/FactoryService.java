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
                (IRegisterService) ProxyFactory.createProxy(RegisterServiceImpl.getInstance());
    }

    public static ILoginService getLoginService() {
        return (ILoginService) ProxyFactory.createProxy(LoginServiceImpl.getInstance());
    }

    public static IMintService getMintService() {
        return (IMintService) ProxyFactory.createProxy(MintServiceImpl.getInstance());
    }

    public static IInfoService getInfoService() {
        return (IInfoService) ProxyFactory.createProxy(InfoServiceImpl.getInstance());
    }

    public static IDisplayService getDisplayService() {
        return (IDisplayService) ProxyFactory.createProxy(DisplayServiceImpl.getInstance());
    }

    public static ITraceService getTraceService() {
        return (ITraceService) ProxyFactory.createProxy(TraceServiceImpl.getInstance());
    }

    public static IPurchaseService getPurchaseService() {
        return
                (IPurchaseService) ProxyFactory.createProxy(PurchaseServiceImpl.getInstance());
    }

    public static ITransferService getTransferService() {
        return (ITransferService) ProxyFactory.createProxy(TransferServiceImpl.getInstance());
    }

    public static ISearchService getSearchService() {
        return (ISearchService) ProxyFactory.createProxy(SearchServiceImpl.getInstance());
    }

    public static IAuctionService getAuctionService() {
        return (IAuctionService) ProxyFactory.createProxy(AuctionServiceImpl.getInstance() );
    }

}
