package service;

/**
 * 工厂
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public class FactoryService {
    public static RegisterService getRegisterService(){
        return RegisterService.getInstance();
    }
    public static LoginService getLoginService(){
        return LoginService.getInstance();
    }

    public static MintService getMintService(){
        return MintService.getInstance();
    }
    public static InfoService getInfoService(){
        return InfoService.getInstance();
    }
    public static DisplayService getDisplayService(){
        return DisplayService.getInstance();
    }
    public static TraceService getTraceService(){
        return TraceService.getInstance();
    }
    public static PurchaseService getPurchaseService(){
        return PurchaseService.getInstance();
    }
    public static TransferService getTransferService(){
        return TransferService.getInstance();
    }
    public static SearchService getSearchService(){
        return SearchService.getInstance();
    }


}
