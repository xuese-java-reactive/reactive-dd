package mofa.wangzhe.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springBoot guanFang
 * //https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application
 * // * https://docs.spring.io/spring-boot/docs/2.4.6/reference/html/spring-boot-features.html#boot-features-webflux
 *
 * @author admin
 */

@Slf4j
@SpringBootApplication(scanBasePackages = {"mofa.wangzhe", "mofa.wangzhe.reactive.interfaces"})
public class ReactiveApplication {

    public static void main(String[] args) {
        Sys.arg = args;
        Sys.context = SpringApplication.run(ReactiveApplication.class, args);
    }

}
