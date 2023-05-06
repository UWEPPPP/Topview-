package service;

public class TransferService {
    private TransferService() {
    }
    public static class TransferServiceHolder {
        private static final TransferService INSTANCE = new TransferService();
    }

    public static TransferService getInstance() {
        return TransferServiceHolder.INSTANCE;
    }
}
