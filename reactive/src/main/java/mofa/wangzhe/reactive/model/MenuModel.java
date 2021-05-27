package mofa.wangzhe.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    private String uuid;

    private String pId;

    private String name;

    private List<MenuModel> children = new ArrayList<>();
}
