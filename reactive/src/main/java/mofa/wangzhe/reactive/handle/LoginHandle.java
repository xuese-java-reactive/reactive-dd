package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.LoginModel;
import mofa.wangzhe.reactive.util.jwt.JwtUtil;
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
public class LoginHandle {

    private final JwtUtil jwtUtil;

    @Autowired
    public LoginHandle(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 登录
     *
     * @param request 请求
     * @return 结果
     */
    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<LoginModel> people = request.bodyToMono(LoginModel.class);
        return people
                .filter(f -> Objects.nonNull(f.getAccount()) && Objects.nonNull(f.getPassword()))
                .flatMap(f -> {
//                    if ("123".equals(f.getAccount()) && "123".equals(f.getPassword())) {
//                        return ResultUtil2.ok("token");
//                    } else {
//                        return ResultUtil2.err("账号或密码错误");
//                    }
                    return ResultUtil2.ok(jwtUtil.createJwt(String.valueOf(1), f.getAccount()));
                })
                .switchIfEmpty(ResultUtil2.err("账号或密码不能为空"));
    }

}
