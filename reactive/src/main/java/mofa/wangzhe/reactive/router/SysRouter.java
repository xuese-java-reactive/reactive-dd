package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.Sys;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author LD
 */

@Component
public class SysRouter {

    @Bean
    public RouterFunction<ServerResponse> tttRouterFunction() {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/sys"),
                        RouterFunctions.route(
                                RequestPredicates.GET("/shutdown"),
                                e -> {
                                    Sys.shutdown();
                                    return Mono.empty();
                                }
                        ).andRoute(
                                RequestPredicates.GET("/rest"),
                                e -> {
                                    Sys.rest();
                                    return ServerResponse
                                            .temporaryRedirect(URI.create("/"))
                                            .build();
                                }
                        )
                )
        );
    }
}