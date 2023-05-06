package service;

public class TraceService {
    private TraceService() {
    }
    public static class TraceServiceHolder {
        private static final TraceService INSTANCE = new TraceService();
    }

    public static TraceService getInstance() {
        return TraceServiceHolder.INSTANCE;
    }

}
