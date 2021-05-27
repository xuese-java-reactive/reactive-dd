package mofa.wangzhe.reactive.sys.wenflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;

import java.util.concurrent.TimeUnit;

/**
 * @author LD
 */

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Autowired
    private ThymeleafReactiveViewResolver thymeleafReactiveViewResolver;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }

    /**
     * 加入thymeleaf试图解析器，不然找不到view name
     *
     * @param registry ViewResolverRegistry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafReactiveViewResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "/public",
                        "classpath:/static/"
                )
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

//    /**
//     * 映射静态资源文件映射
//     *
//     * @param registry ResourceHandlerRegistry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(
//                "/webjars/**",
//                "/js/**",
//                "/css/**",
//                "/img/**",
//                "/favicon.ico"
//        ).addResourceLocations(
//                "classpath:/META-INF/resources/webjars/",
//                "classpath:/static/js/",
//                "classpath:/static/css/",
//                "classpath:/static/img/",
//                "classpath:/static/favicon.ico"
//        );
//    }
}
