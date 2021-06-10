package mofa.wangzhe.reactive.util.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultUtil2 {

    /**
     * 成功与否的状态
     */
    private boolean state;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的数据信息
     */
    private Object data;

    public static Mono<ServerResponse> ok(Object data) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ResultUtil2(true, "操作成功", data));
    }

    public static Mono<ServerResponse> err(String msg) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ResultUtil2(false, msg, null));
    }
}
