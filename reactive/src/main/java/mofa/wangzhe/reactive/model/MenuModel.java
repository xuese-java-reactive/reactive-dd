package mofa.wangzhe.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
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
@Table("menu_table")
public class MenuModel {

    @Id
    private String uuid;

    @Column("p_id")
    private String pId;

    private String name;

    /**
     * 跳转路径  例如：menu/menu
     */
    private String p;

    /**
     * 图标
     */
    private String ico;

    /**
     * 权限标识
     */
    private String jur;

    @Transient
    private List<MenuModel> children = new ArrayList<>();
}
