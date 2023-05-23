package tv.spring.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用于service层的注解
 *
 * @author 刘家辉
 * @date 2023/05/16
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLogger {
}
