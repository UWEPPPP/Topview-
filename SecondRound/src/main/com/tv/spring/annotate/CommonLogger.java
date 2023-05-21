package tv.spring.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 一些普通类应该使用的记录器
 *
 * @author 刘家辉
 * @date 2023/05/16
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonLogger {
}
