package mofa.wangzhe.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LD
 */

@Slf4j
@Component
public class Sys {

    protected static ConfigurableApplicationContext context;
    protected static String[] arg;

    public static void shutdown() {
        log.info("系统关闭中...");
        context.close();
    }

    public static void rest() {
        ExecutorService threadPool = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPool.execute(() -> {
            log.info("系统重启中...");
            context.close();
            context = SpringApplication.run(ReactiveApplication.class, arg);
        });
        threadPool.shutdown();
    }
}
