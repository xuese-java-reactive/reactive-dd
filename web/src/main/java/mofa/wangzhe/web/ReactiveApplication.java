package mofa.wangzhe.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;

/**
 * springboot guanfang
 * //https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application
 * // * https://docs.spring.io/spring-boot/docs/2.4.6/reference/html/spring-boot-features.html#boot-features-webflux
 *
 * @author admin
 */
@SpringBootApplication(scanBasePackages = {"mofa.wangzhe"})
public class ReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

}
