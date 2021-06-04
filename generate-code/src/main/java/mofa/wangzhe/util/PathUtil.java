package mofa.wangzhe.util;

/**
 * @author LD
 */
public class PathUtil {

    private static String getPath() {
        return System.getProperty("user.dir");
    }

    public static String getPathJava() {
        return getPath() + "/reactive/src/main/java/";
    }

    public static String getPathStatic() {
        return getPath() + "/web/src/main/resources/";
    }
}
