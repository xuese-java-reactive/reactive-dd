package mofa.wangzhe.code;

import mofa.wangzhe.database.DataConfig;
import mofa.wangzhe.model.ColumnModel;
import mofa.wangzhe.model.DataConfigModel;
import mofa.wangzhe.model.JavaCodeModel;
import mofa.wangzhe.model.StaticCodeModel;

import java.util.List;
import java.util.Map;

/**
 * @author LD
 */
public class MainCode {

    /**
     * 代码生成器选择器
     *
     * @param a 是否生成java代码
     * @param b 是否生成静态资源文件
     */
    public static void grenCode(boolean a, boolean b) {
        if (a) {
            java();
        }
        if (b) {
            statics();
        }
    }

    /**
     * 生成java文件
     */
    private static void java() {
        DataConfigModel cfgModel = new DataConfigModel();
        DataConfig dataConfig = new DataConfig(cfgModel);
        Map<String, List<ColumnModel>> map = dataConfig.dataTables();
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
    private static void statics() {
        DataConfigModel cfgModel = new DataConfigModel();
        DataConfig dataConfig = new DataConfig(cfgModel);
        Map<String, List<ColumnModel>> map = dataConfig.dataTables();
        map.forEach((f, v) -> {
            StaticCodeModel staticCodeModel = new StaticCodeModel();
            staticCodeModel.setModularName(f);
            staticCodeModel.setFileName(f);
            StaticCode staticCode = new StaticCode(staticCodeModel);
            staticCode.code(v);
        });
    }
}