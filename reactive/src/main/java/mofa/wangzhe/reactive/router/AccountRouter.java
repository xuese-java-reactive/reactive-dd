package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.handler.AccountHandler;
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
public class AccountRouter {

    @Bean
    public RouterFunction<ServerResponse> accountRouterFunction(AccountHandler handle) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/account"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/account"),
                                handle::save
                        ).andRoute(
                                RequestPredicates.DELETE("/account/{uuid}"),
                                handle::remove
                        ).andRoute(
                                RequestPredicates.PUT("/account/{uuid}"),
                                handle::update
                        ).andRoute(
                                RequestPredicates.PUT("/account/rest/{uuid}"),
                                handle::rest
                        ).andRoute(
                                RequestPredicates.GET("/account/{pageSize}/{pageNum}"),
                                handle::page
                        ).andRoute(
                                RequestPredicates.GET("/account/{uuid}"),
                                handle::one
                        )
                )
        );
    }
}
