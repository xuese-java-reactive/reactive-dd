package mofa.wangzhe.send;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class WebClientUtil {

    public static WebClient webClient() {
        //        设置ssl
        HttpClient secure = HttpClient.create()
                .secure(t -> t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(secure))
                .build();
    }
}
