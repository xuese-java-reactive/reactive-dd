package mofa.wangzhe.reactive.sys.security;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 访问没有授权时的处理器
 *
 * @author LD
 */

@Slf4j
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        log.info("权限验证失败: {}", e.getMessage());

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().set("WWW-Authenticate", "Basic realm=\"Reactive\"");
        ServerResponse.status(HttpStatus.FORBIDDEN)
                .body(ResultUtil2.err("权限不足"), ResultUtil2.class);
        return Mono.empty();
    }
}
