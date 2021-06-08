package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.handle.TttHandle;
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
public class TttRouter {

    @Bean
    public RouterFunction<ServerResponse> tttRouterFunction(TttHandle handle) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/ttt"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/ttt"),
                                handle::save
                        ).andRoute(
                                RequestPredicates.DELETE("/ttt/{uuid}"),
                                handle::remove
                        ).andRoute(
                                RequestPredicates.PUT("/ttt/{uuid}"),
                                handle::update
                        ).andRoute(
                                RequestPredicates.GET("/ttt/{pageSize}/{pageNum}"),
                                handle::page
                        ).andRoute(
                                RequestPredicates.GET("/ttt/{uuid}"),
                                handle::one
                        )
                )
        );
    }
}