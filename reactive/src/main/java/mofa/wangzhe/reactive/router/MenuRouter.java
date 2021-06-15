package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.handle.MenuHandle;
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
public class MenuRouter {

    @Bean
    public RouterFunction<ServerResponse> menuRouterFunction(MenuHandle handle) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/menu"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/menu"),
                                handle::save
                        ).andRoute(
                                RequestPredicates.DELETE("/menu/{uuid}"),
                                handle::remove
                        ).andRoute(
                                RequestPredicates.PUT("/menu/{uuid}"),
                                handle::update
                        ).andRoute(
                                RequestPredicates.GET("/menu/{pid}"),
                                handle::list
                        ).andRoute(
                                RequestPredicates.GET("/menu/jur/{pid}"),
                                handle::list2
                        )
                )
        );
    }
}
