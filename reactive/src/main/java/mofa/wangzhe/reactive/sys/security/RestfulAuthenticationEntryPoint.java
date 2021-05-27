package mofa.wangzhe.reactive.sys.security;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证信息失效（未认证或者认证信息过期）处理器
 *
 * @author LD
 */

@Slf4j
@Component
public class RestfulAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        log.error("当前路径【{}】验证token失败", serverWebExchange.getRequest().getPath());
        return Mono.create(m -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ResultUtil2(false, "logout", null)));
    }
}
