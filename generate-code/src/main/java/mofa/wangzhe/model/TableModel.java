package mofa.wangzhe.model;

import java.io.Serializable;

/**
 * @author LD
 */

public class TableModel implements Serializable {
    //    表名
    private String tableName;
    //    表备注
    private String tableComment;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}
