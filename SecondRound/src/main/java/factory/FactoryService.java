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
                (IRegisterService) ProxyFactory.serviceProxy(RegisterServiceImpl.getInstance());
    }

    public static ILoginService getLoginService() {
        return (ILoginService) ProxyFactory.serviceProxy(LoginServiceImpl.getInstance());
    }

    public static IMintService getMintService() {
        return (IMintService) ProxyFactory.serviceProxy(MintServiceImpl.getInstance());
    }

    public static IInfoService getInfoService() {
        return (IInfoService) ProxyFactory.serviceProxy(InfoServiceImpl.getInstance());
    }

    public static IDisplayService getDisplayService() {
        return (IDisplayService) ProxyFactory.serviceProxy(DisplayServiceImpl.getInstance());
    }

    public static ITraceService getTraceService() {
        return (ITraceService) ProxyFactory.serviceProxy(TraceServiceImpl.getInstance());
    }

    public static IPurchaseService getPurchaseService() {
        return
                (IPurchaseService) ProxyFactory.serviceProxy(PurchaseServiceImpl.getInstance());
    }

    public static ITransferService getTransferService() {
        return (ITransferService) ProxyFactory.serviceProxy(TransferServiceImpl.getInstance());
    }

    public static ISearchService getSearchService() {
        return (ISearchService) ProxyFactory.serviceProxy(SearchServiceImpl.getInstance());
    }

    public static IAuctionService getAuctionService() {
        return (IAuctionService) ProxyFactory.serviceProxy(AuctionServiceImpl.getInstance() );
    }

}
