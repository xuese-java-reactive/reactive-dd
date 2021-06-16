package mofa.wangzhe.send.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqMsgUtil implements Serializable {

    private String touser;

    private String template_id;

    //    回调地址
    private String url;

    private String topcolor;

    private Map<String, Map<String, String>> data = new HashMap<>();

}
