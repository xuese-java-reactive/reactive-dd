package mofa.wangzhe.reactive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author LD
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("account_table")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountModel {

    @Id
    private String uuid;

    private String account;

    private String password;

    private int state;

    private String org;

    @Transient
    private OrgModel orgModel = new OrgModel();
}
