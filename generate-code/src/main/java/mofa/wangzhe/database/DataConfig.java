package mofa.wangzhe.database;

import mofa.wangzhe.model.ColumnModel;
import mofa.wangzhe.model.DataConfigModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源配置
 *
 * @author LD
 */

public class DataConfig {

    private final DataConfigModel dataModel;

    public DataConfig(DataConfigModel dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * @return Connection
     * @throws ClassNotFoundException 驱动找不到异常
     * @throws SQLException           sql异常
     */
    private Connection link() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://"
                + dataModel.host + ":"
                + dataModel.port + "/"
                + dataModel.database
                + "?serverTimezone=GMT%2B8";
        return DriverManager.getConnection(url, dataModel.user, dataModel.password);

    }

    /**
     * 获取所有的表名
     *
     * @return List<String>
     */
    private List<String> tables() {
        final String sql1 = "SELECT" +
                "    A.TABLE_SCHEMA '数据库'," +
                "    A.TABLE_NAME 'table_name'," +
                "    A.TABLE_ROWS '表记录行数'," +
                "    A.CREATE_TIME '创表时间'," +
                "    A.TABLE_COMMENT 'table_remarks'" +
                " FROM INFORMATION_SCHEMA.TABLES A" +
                " WHERE" +
                "    A.TABLE_SCHEMA = ";
        List<String> list = new ArrayList<>();
        try (
                Connection conn = link();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql1 + "'" + dataModel.database + "'")
        ) {
            // 展开结果集数据库
            while (rs.next()) {
                // 获取所有表名
                String tableName = rs.getString("table_name");
                list.add(tableName);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取指定表的列
     *
     * @param table 表名
     * @return List<String> 表中字段
     */
    private List<ColumnModel> columns(String table) {
        final String sql2 = "select" +
                " column_name," +
                " column_type," +
                " data_type," +
                " character_maximum_length," +
                " is_nullable," +
                " column_default," +
                " column_comment " +
                " from" +
                " information_schema.columns" +
                " where" +
                " table_schema ='" + dataModel.database + "'" +
                " AND" +
                " table_name  = ";
        List<ColumnModel> list = new ArrayList<>();
        try (
                Connection conn = link();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql2 + "'" + table + "'")
        ) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String[] s = columnName.split("_");
                StringBuilder str = new StringBuilder();
                if (s.length > 1) {
                    for (int i = 0; i < s.length; i++) {
                        if (i != 0) {
                            str.append(s[i].substring(0, 1).toUpperCase().concat(s[i].substring(1).toLowerCase()));
                        }
                    }
                    columnName = s[0] + str;
                } else {
                    columnName = s[0];
                }
                String columnType = rs.getString("COLUMN_TYPE");
                String dataType = rs.getString("DATA_TYPE");
                String columnComment = rs.getString("COLUMN_COMMENT");
                ColumnModel columnModel = new ColumnModel();
                columnModel.setColumnName(columnName);
                columnModel.setColumnType(columnType);
                columnModel.setDataType(dataType);
                columnModel.setColumnComment(columnComment);
                list.add(columnModel);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Map<String, List<ColumnModel>> dataTables() {
        Map<String, List<ColumnModel>> map = new HashMap<>(0);
        List<String> tables = tables();
        tables.forEach(k -> {
            List<ColumnModel> columns = columns(k);
            map.put(k, columns);
        });
        return map;
    }
}
