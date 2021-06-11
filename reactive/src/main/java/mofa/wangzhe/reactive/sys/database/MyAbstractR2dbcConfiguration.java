package mofa.wangzhe.reactive.sys.database;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

/**
 * @author LD
 */

@Configuration
public class MyAbstractR2dbcConfiguration {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public R2dbcEntityTemplate setR2dbcEntityTemplate() {
        return new R2dbcEntityTemplate(connectionFactory);
    }

}
