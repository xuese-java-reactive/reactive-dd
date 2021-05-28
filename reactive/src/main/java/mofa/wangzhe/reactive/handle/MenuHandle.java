package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.MenuService;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author LD
 */

@Slf4j
@Component
public class MenuHandle {

    private final MenuService service;

    @Autowired
    public MenuHandle(MenuService service) {
        this.service = service;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(MenuModel.class)
                .filter(f -> Objects.nonNull(f.getUuid()) && Objects.nonNull(f.getName()) && Objects.nonNull(f.getPId()))
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)));
    }

    /**
     * menu list
     *
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    public Mono<ServerResponse> list(ServerRequest request) {
        String pid = request.pathVariable("pid");
        return this.service.findAll(pid)
                .collectSortedList()
                .flatMap(ResultUtil2::ok);
    }
}
