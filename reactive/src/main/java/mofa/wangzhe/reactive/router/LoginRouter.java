package mofa.wangzhe.reactive.router;

import mofa.wangzhe.reactive.handle.LoginHandler;
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
public class LoginRouter {

    @Bean
    public RouterFunction<ServerResponse> loginRouterFunction(LoginHandler handle) {
        return RouterFunctions.nest(
                RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                RouterFunctions.nest(
                        RequestPredicates.path("/login"),
                        RouterFunctions.route(
                                RequestPredicates.POST("/login"),
                                handle::login
                        )
                )
        );
    }
}
