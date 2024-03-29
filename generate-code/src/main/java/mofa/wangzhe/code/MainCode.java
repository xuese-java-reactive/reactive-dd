package mofa.wangzhe.code;

import mofa.wangzhe.database.DataConfig;
import mofa.wangzhe.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author LD
 */
public class MainCode {

    /**
     * 代码生成器选择器
     *
     * @param java    是否生成java代码
     * @param statics 是否生成静态资源文件
     */
    public static void grenCode(boolean java, boolean statics) {
        DataConfigModel cfgModel = new DataConfigModel();
        DataConfig dataConfig = new DataConfig(cfgModel);
        if (java) {
            java(dataConfig);
        }
        if (statics) {
            statics(dataConfig);
        }
    }

    /**
     * 生成java文件
     */
    private static void java(DataConfig dataConfig) {
        Map<TableModel, List<ColumnModel>> map = dataConfig.dataTables();
        map.forEach((f, v) -> {
            JavaCodeModel javaCodeModel = new JavaCodeModel();
            javaCodeModel.setTableNo("_table");
            JavaCode javaCode = new JavaCode(javaCodeModel);
            javaCode.code(f, v);
        });
    }

    /**
     * 生成static文件
     */
    private static void statics(DataConfig dataConfig) {
        Map<TableModel, List<ColumnModel>> map = dataConfig.dataTables();
        map.forEach((k, v) -> {
            String f = k.getTableName();
            f = f.replaceAll("_table", "");
            StaticCodeModel staticCodeModel = new StaticCodeModel();
            staticCodeModel.setModularName(f);
            staticCodeModel.setFileName(f);
            StaticCode staticCode = new StaticCode(staticCodeModel, dataConfig);
            staticCode.code(k, v);
        });
    }
}
