package tv.factory;

import tv.aop.ProxyFactory;
import tv.dao.IDao;
import tv.service.*;
import tv.spring.AppConfig;
import tv.spring.ApplicationContext;
import tv.util.CastUtil;

/**
 * 工厂
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public class Factory {
    private Factory() {
        APPLICATION_CONTEXT = new ApplicationContext(AppConfig.class);
    }
    public static Factory getInstance() {
        return FactoryHolder.INSTANCE;
    }
    private static class FactoryHolder {
        private static final Factory INSTANCE = new Factory();
    }
    private  final ApplicationContext APPLICATION_CONTEXT;
    public  IRegisterService getRegisterService() {
        return
                CastUtil.cast(ProxyFactory.serviceProxy(APPLICATION_CONTEXT.getBean("registerServiceImpl")));
    }

    public  ILoginService getLoginService() {
        return CastUtil.cast( APPLICATION_CONTEXT.getBean("loginServiceImpl"));
    }

    public  IMintService getMintService() {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean("mintServiceImpl"));
    }

    public  IInfoService getInfoService() {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean("infoServiceImpl"));
    }

    public  IDisplayService getDisplayService() {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean("displayServiceImpl"));
    }

    public  ITraceService getTraceService() {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean("traceServiceImpl"));
    }

    public  IPurchaseService getPurchaseService() {
        return
                CastUtil.cast(APPLICATION_CONTEXT.getBean("purchaseServiceImpl"));
    }

    public  ITransferService getTransferService() {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean("transferServiceImpl"));
    }

    public  ISearchService getSearchService() {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean("searchServiceImpl"));
    }

    public  IAuctionService getAuctionService() {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean("auctionServiceImpl"));
    }

    public  IDao iDao() {
        return CastUtil.cast(APPLICATION_CONTEXT.getBean("dao"));
    }

}
