package mofa.wangzhe;

import mofa.wangzhe.code.JavaCode;
import mofa.wangzhe.database.DataConfig;
import mofa.wangzhe.model.ColumnModel;
import mofa.wangzhe.model.DataConfigModel;
import mofa.wangzhe.model.JavaCodeModel;

import java.util.List;
import java.util.Map;

/**
 * @author LD
 */
public class Main {

    public static void main(String[] args) {
        java();
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
}
