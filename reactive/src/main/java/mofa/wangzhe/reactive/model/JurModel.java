package mofa.wangzhe.reactive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


/**
 * @author LD
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table("jur_table")
public class JurModel {

    @Id
    private String uuid;

    @Column("menu_id")
    private String menuId;

    @Column("acc_id")
    private String accId;


}