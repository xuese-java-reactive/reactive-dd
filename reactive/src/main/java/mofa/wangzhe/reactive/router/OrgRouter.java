package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.handle.OrgHandler;
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
public class OrgRouter {

    @Bean
    public RouterFunction<ServerResponse> orgRouterFunction(OrgHandler handle) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/org"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/org"),
                                handle::save
                        ).andRoute(
                                RequestPredicates.DELETE("/org/{uuid}"),
                                handle::remove
                        ).andRoute(
                                RequestPredicates.PUT("/org/{uuid}"),
                                handle::update
                        ).andRoute(
                                RequestPredicates.GET("/org/{pid}"),
                                handle::list
                        )
                )
        );
    }
}
