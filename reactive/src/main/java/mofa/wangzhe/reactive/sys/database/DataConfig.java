package mofa.wangzhe.reactive.sys.database;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
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
//                .option(ConnectionFactoryOptions.SSL, true)
//                .option(Option.valueOf("sslMode"), "verify_identity")
//                .option(Option.valueOf("sslCa"), "/path/to/mysql/ca.pem")
//                .option(Option.valueOf("sslCert"), "/path/to/mysql/client-cert.pem")
//                .option(Option.valueOf("sslKey"), "/path/to/mysql/client-key.pem")
//                .option(Option.valueOf("sslKeyPassword"), "key-pem-password-in-here")
//                .option(Option.valueOf("tlsVersion"), "TLSv1.3,TLSv1.2,TLSv1.1")
//                .option(Option.valueOf("sslHostnameVerifier"), "com.example.demo.MyVerifier")
//                .option(Option.valueOf("sslContextBuilderCustomizer"), "com.example.demo.MyCustomizer")
//                .option(Option.valueOf("zeroDate"), "use_null")
//                .option(Option.valueOf("useServerPrepareStatement"), true)
//                .option(Option.valueOf("tcpKeepAlive"), true)
//                .option(Option.valueOf("tcpNoDelay"), true)
//                .option(Option.valueOf("autodetectExtensions"), false)
                .build();
        return ConnectionFactories.get(options);
    }

//    @Bean
//    public CommandLineRunner initDatabase(ConnectionFactory cf) {
//
//        return (args) ->
//                Flux.from(cf.create())
//                        .flatMap(c ->
//                                Flux.from(c.createBatch()
//                                        .add("drop table if exists Users")
//                                        .add("create table Users(" +
//                                                "id IDENTITY(1,1)," +
//                                                "firstname varchar(80) not null," +
//                                                "lastname varchar(80) not null)")
//                                        .add("insert into Users(firstname,lastname)" +
//                                                "values('flydean','ma')")
//                                        .add("insert into Users(firstname,lastname)" +
//                                                "values('jacken','yu')")
//                                        .execute())
//                                        .doFinally((st) -> c.close())
//                        )
//                        .log()
//                        .blockLast();
//    }


}
