package tv.controller.handler;

import tv.controller.ServletHandler;
import tv.entity.bo.SearchBo;
import tv.service.ISearchService;
import tv.spring.annotate.AutoWired;
import tv.spring.annotate.Component;
import tv.spring.annotate.Controller;
import tv.spring.annotate.Scope;
import tv.util.DataBinder;

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
        SearchBo bo = DataBinder.bind(SearchBo.class, request);
        return searchServiceImpl.search(bo);
    }
}
