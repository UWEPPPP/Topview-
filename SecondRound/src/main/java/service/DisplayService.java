package service;

public class DisplayService {
    private DisplayService() {
    }
    public static class DisplayServiceHolder {
        private static final DisplayService INSTANCE = new DisplayService();
    }

    public static DisplayService getInstance() {
        return DisplayServiceHolder.INSTANCE;
    }

}
