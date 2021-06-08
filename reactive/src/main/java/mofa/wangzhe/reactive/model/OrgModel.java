package mofa.wangzhe.reactive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;


/**
 * @author LD
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table("org_table")
public class OrgModel {

    @Id
    private String uuid;

    private String pid;

    private String name;

    @Transient
    private List<OrgModel> children = new ArrayList<>();
}