
package mofa.wangzhe.reactive.handler;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.JurModel;
import mofa.wangzhe.reactive.service.JurService;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */

@Slf4j
@Component
public class JurHandle {

    private final JurService service;

    @Autowired
    public JurHandle(JurService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('JurHandle-save')")
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(JurModel.class)
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    @PreAuthorize("hasAuthority('JurHandle-remove')")
    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return this.service.remove(uuid)
                .flatMap(f2 -> ResultUtil2.ok(null));
    }

    @PreAuthorize("hasAuthority('JurHandle-update')")
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
    @PreAuthorize("hasAuthority('JurHandle-page')")
    public Mono<ServerResponse> page(ServerRequest request) {
        int pageSize = Integer.parseInt(request.pathVariable("pageSize"));
        int pageNum = Integer.parseInt(request.pathVariable("pageNum"));
        String search = request.queryParam("search").orElse("");
        return this.service.page(pageSize, pageNum, search)
                .flatMap(ResultUtil2::ok);
    }

    /**
     * 根据id获取
     *
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    @PreAuthorize("hasAuthority('JurHandle-update')")
    public Mono<ServerResponse> one(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return this.service.one(uuid)
                .flatMap(ResultUtil2::ok);
    }

}
