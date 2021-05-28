package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.MenuService;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

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
                .flatMap(f -> service.save(f)
                        .flatMap(k -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("不能为空"));
    }

    /**
     * menu list
     *
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    public Mono<ServerResponse> list(ServerRequest request) {
        String pid = request.pathVariable("pid");

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.service.findAll(pid), MenuModel.class);
    }
}
