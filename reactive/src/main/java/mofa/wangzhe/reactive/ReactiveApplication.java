package mofa.wangzhe.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * springboot guanfang
 * //https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application
 * // * https://docs.spring.io/spring-boot/docs/2.4.6/reference/html/spring-boot-features.html#boot-features-webflux
 *
 * @author admin
 */

@Slf4j
@SpringBootApplication(scanBasePackages = {"mofa.wangzhe"})
public class ReactiveApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(ReactiveApplication.class, args);
    }

    public static void shutdown() {
        log.info("系统关闭中...");
        context.close();
    }

}
