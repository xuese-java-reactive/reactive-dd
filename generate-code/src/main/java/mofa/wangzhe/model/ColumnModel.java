package mofa.wangzhe.model;

import mofa.wangzhe.util.JdbcToJavaType;

import java.io.Serializable;

/**
 * @author LD
 */

public class ColumnModel implements Serializable {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 数据类型
     */
    private String columnType;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 备注
     */
    private String columnComment;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getDataType() {
        return JdbcToJavaType.toJava(dataType);
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    @Override
    public String toString() {
        return "ColumnModel{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", dataType='" + dataType + '\'' +
                ", columnComment='" + columnComment + '\'' +
                '}';
    }
}
