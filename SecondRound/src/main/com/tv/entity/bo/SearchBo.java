package tv.entity.bo;

/**
 * 查找
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class SearchBo {
    private String searchType;
    private String searchText;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
