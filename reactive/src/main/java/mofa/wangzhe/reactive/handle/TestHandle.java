
package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.TestModel;
import mofa.wangzhe.reactive.service.TestService;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author LD
 */

@Slf4j
@Component
public class TestHandle {

    private final TestService service;

    @Autowired
    public TestHandle(TestService service) {
        this.service = service;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(TestModel.class)
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        TestModel model = new TestModel();
        model.setUuid(uuid);
        return this.service.remove(model)
                .flatMap(f2 -> ResultUtil2.ok(null));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return request.bodyToMono(TestModel.class)
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
    public Mono<ServerResponse> page(ServerRequest request) {
        int pageSize = Integer.parseInt(request.pathVariable("pageSize"));
        int pageNum = Integer.parseInt(request.pathVariable("pageNum"));
        String search = request.queryParam("search").orElse("");
        Mono<Long> mono = this.service.count(search);
        Mono<List<TestModel>> listMono = this.service.page(pageSize, pageNum, search)
                .collectList();
        return Mono.zip(mono, listMono)
                .flatMap(ResultUtil2::ok);
    }

}
