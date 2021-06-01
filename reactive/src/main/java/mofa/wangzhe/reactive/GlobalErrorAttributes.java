package mofa.wangzhe.reactive;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 函数式全局异常处理
 * //类似@RestControllerAdvice
 *
 * @author LD
 */

@Slf4j
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        String path = request.path();
        Map<String, Object> map = super.getErrorAttributes(request, options);
        Throwable error = getError(request);
        log.info("当前被拦截的请求路径：{},token未验证通过,err:{}", path, error.getMessage());
        if (error instanceof FileNotFoundException
                || error instanceof ResponseStatusException
        ) {
            map.put("status", HttpStatus.NOT_FOUND);
            map.put("message", error.getMessage());
        } else if (error instanceof IllegalStateException) {
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            map.put("message", error.getMessage());
        } else if (
                error instanceof JWTDecodeException
                        || error instanceof AuthenticationCredentialsNotFoundException
                        || error instanceof TokenExpiredException
        ) {
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            map.put("message", "logout");
        } else {
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            map.put("message", error.getMessage());
        }
        return map;
    }

}
