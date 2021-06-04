package mofa.wangzhe.database;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static mofa.wangzhe.constant.JavaConstantUtil.*;

/**
 * 数据源配置
 *
 * @author LD
 */

public class DataConfig {

    private static final String SQL_1 = "SELECT" +
            "    A.TABLE_SCHEMA '数据库'," +
            "    A.TABLE_NAME 'table_name'," +
            "    A.TABLE_ROWS '表记录行数'," +
            "    A.CREATE_TIME '创表时间'," +
            "    A.TABLE_COMMENT 'table_remarks'" +
            " FROM INFORMATION_SCHEMA.TABLES A" +
            " WHERE" +
            "    A.TABLE_SCHEMA = ";

    private static R2dbcEntityTemplate getTemplate() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, DRIVER)
                .option(ConnectionFactoryOptions.HOST, HOST)
                .option(ConnectionFactoryOptions.USER, USER)
                .option(ConnectionFactoryOptions.PASSWORD, PASSWORD)
                .option(ConnectionFactoryOptions.PORT, PORT)
                .option(ConnectionFactoryOptions.DATABASE, DATABASE)
                .option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(3))
                .build();
        ConnectionFactory connectionFactory = ConnectionFactories.get(options);
        return new R2dbcEntityTemplate(connectionFactory);
    }

    public static void getTable() {
        R2dbcEntityTemplate template = getTemplate();
        template.getDatabaseClient()
                .sql(SQL_1 + DATABASE)
                .fetch()
                .all()
                .flatMap(f -> {
                    f.forEach((k, v) -> {
                        System.out.println("key:" + k);
                        System.out.println("vel:" + v);
                    });
                    return Mono.just(f);
                })
                .subscribe(System.out::println);

    }

    public static void main(String[] args) {
        getTable();
    }
}
