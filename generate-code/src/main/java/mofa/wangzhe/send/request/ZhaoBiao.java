package mofa.wangzhe.send.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZhaoBiao implements Serializable {

//    采购需求单位a,公告类型b,标题c,时间d,链接e

    private String a = "";

    private String b = "";

    private String c = "";

    private String d = "";

    private String e = "";
}
