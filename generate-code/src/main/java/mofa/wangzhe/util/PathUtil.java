package mofa.wangzhe.util;

/**
 * @author LD
 */
public class PathUtil {

    private static String getPath() {
        String property = System.getProperty("user.dir");
        return property + "/web/src/main/";
    }

    public static String getPathJava() {
        return getPath() + "java/";
    }

    public static String getPathStatic() {
        return getPath() + "resources/";
    }
}
