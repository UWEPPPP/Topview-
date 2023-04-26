package service;

public class Factory {
    public static RegisterService getRegisterService(){
        return RegisterService.getInstance();
    }
}
