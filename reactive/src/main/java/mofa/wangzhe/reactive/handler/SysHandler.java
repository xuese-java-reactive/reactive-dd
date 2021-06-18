package mofa.wangzhe.reactive.handler;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.Sys;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author LD
 */

@Slf4j
@Component
public class SysHandler {

    @Secured("DEVELOPMENT")
    public Mono<ServerResponse> shutdown(ServerRequest request) {
        Sys.shutdown();
        return Mono.empty();
    }

    @Secured("DEVELOPMENT")
    public Mono<ServerResponse> rest(ServerRequest request) {
        Sys.rest();
        return ServerResponse
                .temporaryRedirect(URI.create("/"))
                .build();
    }

}
