package mofa.wangzhe.reactive.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;


/**
 * @author LD
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table("ttt_table")
public class TttModel {

    @Id
    private String uuid;

    private String test1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date testDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date testTime;


}