package mofa.wangzhe.reactive.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LD
 */

@Slf4j
@Component
public class JwtUtil {

    /**
     * 密钥
     */
    private static final String SECRET = "com.web.flux";

    /**
     * 签发者
     */
    public static String ISS = "webFlux";

    /**
     * 生成token
     *
     * @param userId 用户id
     * @return token串
     */
    public String createJwt(String userId, String account) {

        Calendar instance = Calendar.getInstance();
        Date time1 = instance.getTime();
//        当前时间增加15分钟
        instance.add(Calendar.MINUTE, 60);
        Date time2 = instance.getTime();
//头部信息
        Map<String, Object> heardMap = new HashMap<>(1);
//        最好不要设置alg
        heardMap.put("typ", "JWT");

        return JWT.create()
//                头部
                .withHeader(heardMap)
//                当前登陆人账号
                .withClaim("user", account)
//                签发者
                .withIssuer(ISS)
//                接收者信息，一般是登录的用户
                .withAudience(userId)
//                签发时间
                .withIssuedAt(time1)
//                过期时间
                .withExpiresAt(time2)
//                加密
                .sign(Algorithm.HMAC512(SECRET));
    }

    /**
     * 解密token
     *
     * @param token token串
     * @return 解析结果
     */
    public static Map<String, Claim> verifyToken(String token) throws JWTDecodeException, TokenExpiredException {
        Algorithm algorithm = Algorithm.HMAC512(SECRET);
        JWTVerifier build = JWT.require(algorithm).build();
        DecodedJWT verify = build.verify(token);
        return verify.getClaims();
    }

    /**
     * 获取令牌指定的值
     *
     * @param token token串
     * @param s     key
     * @return Claim
     */
    public static Claim getClaim(String token, String s) {
        Map<String, Claim> map = verifyToken(token);
        if (map == null || map.size() <= 0) {
            return null;
        } else {
            return map.get(s);
        }
    }
}
