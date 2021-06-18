package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.handler.JurHandler;
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
public class JurRouter {

    @Bean
    public RouterFunction<ServerResponse> jurRouterFunction(JurHandler handle) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/jur"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/jur"),
                                handle::save
                        ).andRoute(
                                RequestPredicates.GET("/jur/{accId}"),
                                handle::list
                        )
                )
        );
    }
}