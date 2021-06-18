package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.handler.TeHandler;
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
public class TeRouter {

    @Bean
    public RouterFunction<ServerResponse> teRouterFunction(TeHandler handle) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/te"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/te"),
                                handle::save
                        ).andRoute(
                                RequestPredicates.DELETE("/te/{uuid}"),
                                handle::remove
                        ).andRoute(
                                RequestPredicates.PUT("/te/{uuid}"),
                                handle::update
                        ).andRoute(
                                RequestPredicates.GET("/te/{pageSize}/{pageNum}"),
                                handle::page
                        ).andRoute(
                                RequestPredicates.GET("/te/{uuid}"),
                                handle::one
                        )
                )
        );
    }
}