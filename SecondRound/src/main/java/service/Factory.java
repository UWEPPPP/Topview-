package service;

public class Factory {
    public static RegisterService getRegisterService(){
        return RegisterService.getInstance();
    }
    public static LoginService getLoginService(){
        return LoginService.getInstance();
    }

    public static MintService getMintService(){
        return MintService.getInstance();
    }



}
