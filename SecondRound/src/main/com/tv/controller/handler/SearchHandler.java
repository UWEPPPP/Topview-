package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.service.ISearchService;
import tv.spring.*;
import tv.util.exception.InputException;

import javax.servlet.http.HttpServletRequest;

/**
 * 搜索处理程序
 *
 * @author 刘家辉
 * @date 2023/05/17
 */

@Component
@Scope("singleton")
@Controller
public class SearchHandler implements ServletHandler {
    @AutoWired
    public ISearchService searchServiceImpl;
    @Override
    public Object handle(HttpServletRequest request) throws Exception {
        String type = request.getParameter("searchType");
        String searchText = request.getParameter("searchText");
        if (type == null || searchText == null) {
            throw new InputException("输入为空");
        }
        return searchServiceImpl.search(type, searchText);
    }
}
