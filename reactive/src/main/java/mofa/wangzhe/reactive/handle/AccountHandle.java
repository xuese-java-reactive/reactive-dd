package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.AccountModel;
import mofa.wangzhe.reactive.service.AccountService;
import mofa.wangzhe.reactive.util.md5.Md5Util;
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
        return this.service.remove(uuid)
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

    public Mono<ServerResponse> rest(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        AccountModel model = new AccountModel();
        model.setUuid(uuid);
        String md5Str = Md5Util.getMd5Str("123456");
        model.setPassword(md5Str);
        return this.service.update(model)
                .flatMap(f2 -> ResultUtil2.ok(null));
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
        Mono<List<AccountModel>> listMono = this.service.page(pageSize, pageNum, search)
                .collectList();
        return Mono.zip(mono, listMono)
                .flatMap(ResultUtil2::ok);
    }

}
