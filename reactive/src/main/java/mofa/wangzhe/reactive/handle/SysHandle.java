package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.Sys;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class SysHandle {

    @PreAuthorize("hasRole('DEVELOPMENT')")
    public Mono<ServerResponse> shutdown(ServerRequest request) {
        Sys.shutdown();
        return Mono.empty();
    }

    @PreAuthorize("hasRole('DEVELOPMENT')")
    public Mono<ServerResponse> rest(ServerRequest request) {
        Sys.rest();
        return ServerResponse
                .temporaryRedirect(URI.create("/"))
                .build();
    }

}
