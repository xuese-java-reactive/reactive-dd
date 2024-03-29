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
@Table("menu_table")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuModel {

    @Id
    private String uuid;

    private String pid;

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

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 类型 0:菜单，1内部按钮
     */
    private Integer menuType;

    @Transient
    private List<MenuModel> children = new ArrayList<>();
}
