package mofa.wangzhe.reactive.handler;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.JurModel;
import mofa.wangzhe.reactive.service.JurService;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */

@Slf4j
@Component
public class JurHandler {

    private final JurService service;

    @Autowired
    public JurHandler(JurService service) {
        this.service = service;
    }

    @Secured("ADMINS")
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(JurModel.class)
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    @Secured("ADMINS")
    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return this.service.remove(uuid)
                .flatMap(f2 -> ResultUtil2.ok(null));
    }

    @Secured("ADMINS")
    public Mono<ServerResponse> update(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return request.bodyToMono(JurModel.class)
                .flatMap(f -> {
                    f.setUuid(uuid);
                    return this.service.update(f)
                            .flatMap(f2 -> ResultUtil2.ok(null));
                })
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    /**
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    public Mono<ServerResponse> list(ServerRequest request) {
        String accId = request.pathVariable("accId");
        return this.service.list(accId)
                .collectList()
                .flatMap(ResultUtil2::ok);
    }
}
