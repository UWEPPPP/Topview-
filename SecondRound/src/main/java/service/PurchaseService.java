package service;

public class PurchaseService {
    private PurchaseService() {
    }
    public static class PurchaseServiceHolder {
        private static final PurchaseService INSTANCE = new PurchaseService();
    }

    public static PurchaseService getInstance() {
        return PurchaseServiceHolder.INSTANCE;
    }
}
