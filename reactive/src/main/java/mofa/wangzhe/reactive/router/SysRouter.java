package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.handler.SysHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author LD
 */

@Component
public class SysRouter {

    @Bean
    public RouterFunction<ServerResponse> SysRouterFunction(SysHandler handle) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/sys"),
                        RouterFunctions.route(
                                RequestPredicates.GET("/shutdown"),
                                handle::shutdown
                        ).andRoute(
                                RequestPredicates.GET("/rest"),
                                handle::rest
                        )
                )
        );
    }
}