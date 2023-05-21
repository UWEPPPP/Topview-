package tv.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.lang.reflect.Field;

/**
 * 数据绑定，自动转换类型
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class DataBinder {

    public static  <T> T bind(Class<T> clazz,HttpServletRequest request) throws Exception {
        T obj = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String paramName = field.getName();
            String file = "file";
            if(paramName.equals(file)){
                Part avatar = request.getPart("file");
                field.setAccessible(true);
                field.set(obj, avatar);
                continue;
            }
            String paramValue = request.getParameter(paramName);
            if (paramValue != null) {
                Object value = convert(field.getType(), paramValue);
                field.setAccessible(true);
                field.set(obj, value);
            }
        }
        return obj;
    }

    private static Object convert(Class<?> type, String value) {
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type== boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == String.class) {
            return value;
        } else  {
            throw new IllegalArgumentException("不支持的类型: " + type.getName());
        }
    }
}