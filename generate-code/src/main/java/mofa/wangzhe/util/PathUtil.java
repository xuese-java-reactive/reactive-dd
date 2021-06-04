package mofa.wangzhe.util;

/**
 * @author LD
 */
public class PathUtil {

    public static String getPath() {
        String property = System.getProperty("user.dir");
        property = property + "/reactive/src/main/java/";
        return property;
    }
}
