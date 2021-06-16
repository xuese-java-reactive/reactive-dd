package mofa.wangzhe.send.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * openId 返回类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespUtil implements Serializable {

    private Integer errcode;

    private String errmsg;

    private String access_token;

    private Long expires_in;
}
