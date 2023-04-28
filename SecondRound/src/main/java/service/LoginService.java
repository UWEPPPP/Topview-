package service;

public class LoginService {
    private LoginService() {
    }
    public static class LoginServiceHolder {
        private static final LoginService INSTANCE = new LoginService();
    }
    public static LoginService getInstance() {
        return LoginServiceHolder.INSTANCE;
    }

    public boolean login(String name, String password) {
        return true;
    }
}
