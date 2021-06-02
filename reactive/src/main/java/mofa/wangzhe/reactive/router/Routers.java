package mofa.wangzhe.reactive.router;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.handle.AccountHandle;
import mofa.wangzhe.reactive.handle.LoginHandle;
import mofa.wangzhe.reactive.handle.MenuHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author LD
 */

@Slf4j
@Configuration
public class Routers implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> initRouterFunction(
            LoginHandle loginHandle,
            MenuHandle menuHandle,
            AccountHandle accountHandle

    ) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/login"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/login"),
                                loginHandle::login
                        )
                ).andNest(
                        RequestPredicates.path("/menu"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/menu"),
                                menuHandle::save
                        ).andRoute(
                                RequestPredicates.DELETE("/menu/{uuid}"),
                                menuHandle::remove
                        ).andRoute(
                                RequestPredicates.PUT("/menu/{uuid}"),
                                menuHandle::update
                        ).andRoute(
                                RequestPredicates.GET("/menu/{pid}"),
                                menuHandle::list
                        )
                ).andNest(
                        RequestPredicates.path("/account"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/account"),
                                accountHandle::save
                        ).andRoute(
                                RequestPredicates.DELETE("/account/{uuid}"),
                                accountHandle::remove
                        ).andRoute(
                                RequestPredicates.PUT("/account/{uuid}"),
                                accountHandle::update
                        ).andRoute(
                                RequestPredicates.GET("/account"),
                                accountHandle::page
                        )
                )
        );
    }
}
