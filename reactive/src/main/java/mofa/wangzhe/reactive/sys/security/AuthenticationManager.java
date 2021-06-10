package mofa.wangzhe.reactive.sys.security;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.util.jwt.JwtUtil;
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

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        try {
            String accId = Objects.requireNonNull(JwtUtil.getClaim(token, "aud")).asString();

            List<GrantedAuthority> authorities = new ArrayList<>();

//            所有权限
            List<String> rolesMap = new ArrayList<>(0);
            rolesMap.add("admin");

            for (String roleMap : rolesMap) {
                authorities.add(new SimpleGrantedAuthority(roleMap));
            }

            return Mono.just(new UsernamePasswordAuthenticationToken(accId, token, authorities));
        } catch (NullPointerException e) {
            return Mono.empty();
        }
    }

}
