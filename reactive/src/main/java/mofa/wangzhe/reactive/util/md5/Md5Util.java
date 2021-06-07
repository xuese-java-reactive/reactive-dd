package mofa.wangzhe.reactive.util.md5;

import org.springframework.util.DigestUtils;

/**
 * @author admin
 */
public class Md5Util {

    public static String getMd5Str(String str) {
        str += "!@#$%^&*()_+";
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

}
