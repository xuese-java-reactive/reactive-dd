package mofa.wangzhe.reactive.sys.database;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 数据源配置
 *
 * @author LD
 */

@Component
public class DataConfig {

    @Bean
    ConnectionFactory connectionFactory() {

        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(ConnectionFactoryOptions.HOST, "127.0.0.1")
                .option(ConnectionFactoryOptions.USER, "root")
                .option(ConnectionFactoryOptions.PASSWORD, "root")
                .option(ConnectionFactoryOptions.PORT, 3306)
                .option(ConnectionFactoryOptions.DATABASE, "reactive-dd")
                .option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(3))
                .option(Option.sensitiveValueOf("character_set_database"), "UTF-8")
                .build();
        return ConnectionFactories.get(options);
    }

}
