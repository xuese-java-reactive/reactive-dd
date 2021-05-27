package mofa.wangzhe.web.router;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;


/**
 * @author LD
 */

@Slf4j
@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/page/{path}/{page}")
    public Mono<String> page(@PathVariable("path") String path,
                             @PathVariable("page") String page) {
        return Mono.create(e -> e.success(path + "/" + page + ".html"));
    }
}
