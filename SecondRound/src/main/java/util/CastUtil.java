package util;

/**
 * 为了消除泛型警告
 *
 * @author 刘家辉
 * @date 2023/05/04
 */
public  class CastUtil {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    private CastUtil() {
        throw new UnsupportedOperationException();
    }

}