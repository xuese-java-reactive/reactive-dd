package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.AccountModel;
import mofa.wangzhe.reactive.service.AccountService;
import mofa.wangzhe.reactive.util.jwt.JwtUtil;
import mofa.wangzhe.reactive.util.md5.Md5Util;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginHandler {

    private final AccountService service;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginHandler(AccountService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 登录
     *
     * @param request 请求
     * @return 结果
     */
    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(AccountModel.class)
                .flatMap(f ->
                        service.oneByAccount(f.getAccount())
                                .flatMap(fm -> {
                                    String md5Str = Md5Util.getMd5Str(f.getPassword());
                                    if (Objects.equals(md5Str, fm.getPassword())) {
                                        String jwt = jwtUtil.createJwt(fm.getUuid());
                                        return ResultUtil2.ok(jwt);
                                    } else {
                                        return ResultUtil2.err("账号或密码错误");
                                    }
                                })
                                .switchIfEmpty(ResultUtil2.err("账号不存在"))
                )
                .switchIfEmpty(ResultUtil2.err("账号或密码不能为空"));
    }

}
