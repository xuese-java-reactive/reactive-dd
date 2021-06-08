package mofa.wangzhe.reactive.sys.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 过滤连
 * 参考网络从新实现security相关配置
 * 参考：https://github.com/ard333/spring-boot-webflux-jjwt/blob/master/src/main/java/com/ard333/springbootwebfluxjjwt/security/WebSecurityConfig.java
 * //WebSecurityConfigurerAdapter
 *
 * @author LD
 */

@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private SecurityContextRepository securityContextRepository;
    @Autowired
    private RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http

//              关闭普通表单之类的
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()

                .securityContextRepository(securityContextRepository)

//                配置授权
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(
                        "/",
                        "/api/login/login",
                        "/page/**",
                        "/webjars/**",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/fav**",
                        "/actuator/**"
                ).permitAll()
                .anyExchange().authenticated()
                .and()

//                关闭缓存
                .headers().cache().disable()
                .and()

//                配置异常情况
                .exceptionHandling()
//              配置在通过身份验证的用户不拥有所需权限时的处理方式
                .accessDeniedHandler(restfulAccessDeniedHandler)
//                配置应用程序请求身份验证时的操作
                .authenticationEntryPoint(restfulAuthenticationEntryPoint)
                .and()

                .build();
    }

}
