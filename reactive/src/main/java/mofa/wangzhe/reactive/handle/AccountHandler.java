package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.AccountModel;
import mofa.wangzhe.reactive.service.AccountService;
import mofa.wangzhe.reactive.util.md5.Md5Util;
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
public class AccountHandler {

    private final AccountService service;

    @Autowired
    public AccountHandler(AccountService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('AccountHandler-save')")
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(AccountModel.class)
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    @PreAuthorize("hasAuthority('AccountHandler-remove')")
    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return this.service.remove(uuid)
                .flatMap(f2 -> ResultUtil2.ok(null));
    }

    @PreAuthorize("hasAuthority('AccountHandler-update')")
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

    @PreAuthorize("hasAuthority('AccountHandler-rest')")
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
    @PreAuthorize("hasAuthority('AccountHandler') or hasAnyRole('ROLE_ADMINS')")
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
    @PreAuthorize("isAuthenticated()")
    public Mono<ServerResponse> one(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return this.service.one(uuid)
                .flatMap(ResultUtil2::ok);
    }

}
