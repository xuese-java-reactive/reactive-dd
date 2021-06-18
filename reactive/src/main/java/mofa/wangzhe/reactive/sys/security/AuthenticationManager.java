package mofa.wangzhe.reactive.sys.security;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.service.AccountService;
import mofa.wangzhe.reactive.service.MenuService;
import mofa.wangzhe.reactive.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 身份在验证管理器
 *
 * @author LD
 */

@Slf4j
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MenuService menuService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        try {
            Claim aud = JwtUtil.getClaim(token, "aud");
            assert aud != null : "令牌错误，请从新登录";
            String accId = aud.asString();

            List<GrantedAuthority> authorities = new ArrayList<>();

            return accountService.one(accId)
                    .flatMap(f -> {
                        if (Objects.equals("development", f.getOrg())) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_DEVELOPMENT"));
                        } else if (Objects.equals("admins", f.getOrg())) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_ADMINS"));
                        } else {
                            menuService.findAll2(accId, null)
                                    .flatMap(fm -> {
                                        authorities.add(new SimpleGrantedAuthority(fm.getJur()));
                                        return Mono.empty();
                                    });
                        }
                        return Mono.just(new UsernamePasswordAuthenticationToken(accId, token, authorities));
                    });
        } catch (NullPointerException e) {
            return Mono.empty();
        }
    }

}
