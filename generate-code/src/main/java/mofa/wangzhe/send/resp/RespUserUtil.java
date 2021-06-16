package mofa.wangzhe.send.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespUserUtil implements Serializable {

    private Integer errcode;

    private String errmsg;

    private Integer total;

    private Integer count;

    private OpenIdUtil data;

    private String next_openid;

}
