package service;

public class SearchService {
    private SearchService() {
    }
    public static class SearchServiceHolder {
        private static final SearchService INSTANCE = new SearchService();
    }

    public static SearchService getInstance() {
        return SearchServiceHolder.INSTANCE;
    }
}
