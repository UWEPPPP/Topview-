package tv.spring;

/**
 * 豆后置处理程序
 *
 * @author 刘家辉
 * @date 2023/05/15
 */
public interface BeanPostProcessor {
    /**
     * 在初始化过程
     *
     * @param bean     豆
     * @param beanName bean名字
     * @return {@link Object}
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * 发布过程初始化后
     *
     * @param bean     豆
     * @param beanName bean名字
     * @return {@link Object}
     */
    Object postProcessAfterInitialization(Object bean, String beanName);
}
