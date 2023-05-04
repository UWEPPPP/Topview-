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


}
