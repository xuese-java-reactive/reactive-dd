package mofa.wangzhe.reactive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author LD
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("login_table")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountModel {

    private String uuid;

    @Column("acc")
    private String account;

    @Column("pwd")
    private String password;
}
