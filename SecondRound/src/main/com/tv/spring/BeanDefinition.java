package tv.spring;

/**
 * bean定义
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class BeanDefinition {
    private Class type;
private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
