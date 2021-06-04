package mofa.wangzhe.util;

import java.util.Locale;

/**
 * jdbc类型转换成java类型
 *
 * @author LD
 */
public class JdbcToJavaType {

    public static String toJava(String jdbc) {
        if (jdbc != null) {
            jdbc = jdbc.toLowerCase(Locale.ROOT);
            switch (jdbc) {
                case "varchar":
                case "text":
                    return "String";
                case "datetime":
                case "date":
                    return "Date";
                case "bigint":
                    return "long";
                case "int":
                    return "int";
                default:
                    return "Object";
            }
        }
        return null;
    }
}
