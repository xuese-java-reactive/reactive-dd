package mofa.wangzhe.code;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.model.ColumnModel;
import mofa.wangzhe.model.JavaCodeModel;
import mofa.wangzhe.util.PathUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * @author LD
 */

@Slf4j
public class JavaCode {

    private final JavaCodeModel javaCodeModel;

    public JavaCode(JavaCodeModel javaCodeModel) {
        this.javaCodeModel = javaCodeModel;
    }

    private static final VelocityContext CTX = new VelocityContext();
    private static final VelocityEngine VE = new VelocityEngine();

    public void code(String table, List<ColumnModel> columns) {


        VE.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        VE.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        VE.init();

//        java文件首字母大写
        String t = table.toLowerCase();
        t = t.substring(0, 1).toUpperCase().concat(t.substring(1).toLowerCase());

        javaCodeModel.setModularPrefix(t);

        log.info("开始生成java文件");
        CTX.put("author", javaCodeModel.getAuthor());
        CTX.put("tableName", table);
        CTX.put("modeName", javaCodeModel.getModularPrefix() + javaCodeModel.getModelFileSuffix());
        CTX.put("handName", javaCodeModel.getModularPrefix() + javaCodeModel.getHandleFileSuffix());
        CTX.put("serviceName", javaCodeModel.getModularPrefix() + javaCodeModel.getServiceFileSuffix());
        CTX.put("serviceImplName", javaCodeModel.getModularPrefix() + javaCodeModel.getServiceImplFileSuffix());

        CTX.put("modelPackage", javaCodeModel.getPackageModelPath());
        CTX.put("handlePackage", javaCodeModel.getPackageHandlePath());
        CTX.put("servicePackage", javaCodeModel.getPackageServicePath());
        CTX.put("serviceImplPackage", javaCodeModel.getPackageServiceImplPath());

        model(columns);
        handle();
        service();
        serviceImpl();
    }

    /**
     * model
     */
    private void model(List<ColumnModel> columns) {
        // 获取模板文件
        Template t = VE.getTemplate("/java/model.vm");
        CTX.put("columns", columns);
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile(javaCodeModel.getPackageModelPath(),
                javaCodeModel.getModularPrefix() + javaCodeModel.getModelFileSuffix(), sw);
    }

    /**
     * handle
     */
    private void handle() {
        // 获取模板文件
        Template t = VE.getTemplate("/java/handle.vm");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile(javaCodeModel.getPackageHandlePath(),
                javaCodeModel.getModularPrefix() + javaCodeModel.getHandleFileSuffix(), sw);
    }

    private void service() {
        // 获取模板文件
        Template t = VE.getTemplate("/java/service.vm");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile(javaCodeModel.getPackageServicePath(),
                javaCodeModel.getModularPrefix() + javaCodeModel.getServiceFileSuffix(), sw);
    }

    private void serviceImpl() {
        // 获取模板文件
        Template t = VE.getTemplate("/java/serviceImpl.vm");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile(javaCodeModel.getPackageServiceImplPath(),
                javaCodeModel.getModularPrefix() + javaCodeModel.getServiceImplFileSuffix(), sw);
    }

    /**
     * 生成文件
     *
     * @param p  String
     * @param sw StringWriter
     */
    private void createFile(String p, String fileName, StringWriter sw) {
        p = p.replaceAll("\\.", "/");
//        业务包根目录
        String path = StringUtils.hasText(javaCodeModel.getPath()) ? javaCodeModel.getPath() : PathUtil.getPathJava();
        path = path + p + "/";
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdir = file.mkdirs();
            log.info("文件创建" + (mkdir ? "成功" : "失败"));
        }

        path = path + fileName + ".java";
        File file2 = new File(path);
        if (file2.exists()) {
            boolean delete = file2.delete();
            if (delete) {
                boolean newFile = false;
                try {
                    newFile = file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!newFile) {
                    log.info("创建文件失败");
                    System.exit(-1);
                }
            } else {
                log.info("删除文件失败");
                System.exit(-1);
            }
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file2, false));
            bw.write(sw.toString());
            log.info("已生成:" + path);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!Objects.isNull(bw)) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
