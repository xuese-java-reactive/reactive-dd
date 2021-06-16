package mofa.wangzhe.send.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenIdUtil implements Serializable {

    private String[] openid;
}
