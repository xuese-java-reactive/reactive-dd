package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.AccountModel;
import mofa.wangzhe.reactive.service.AccountService;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */

@Slf4j
@Component
public class AccountHandle {

    private final AccountService service;

    @Autowired
    public AccountHandle(AccountService service) {
        this.service = service;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(AccountModel.class)
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        AccountModel model = new AccountModel();
        model.setUuid(uuid);
        return this.service.remove(model)
                .flatMap(f2 -> ResultUtil2.ok(null));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return request.bodyToMono(AccountModel.class)
                .flatMap(f -> {
                    f.setUuid(uuid);
                    return this.service.update(f)
                            .flatMap(f2 -> ResultUtil2.ok(null));
                })
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    /**
     * menu list
     *
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    public Mono<ServerResponse> page(ServerRequest request) {
        int pageSize = Integer.parseInt(request.queryParam("pageSize").orElse("10"));
        int pageNum = Integer.parseInt(request.queryParam("pageNum").orElse("0"));
        return this.service.page(pageSize, pageNum)
                .collectList()
                .flatMap(ResultUtil2::ok);
    }

}
