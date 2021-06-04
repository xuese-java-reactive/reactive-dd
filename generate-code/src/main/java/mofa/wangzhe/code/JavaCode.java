package mofa.wangzhe.code;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.util.PathUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Objects;

import static mofa.wangzhe.constant.JavaConstantUtil.*;

/**
 * @author LD
 */

@Slf4j
public class JavaCode {

    private static final VelocityContext CTX = new VelocityContext();
    private static final VelocityEngine VE = new VelocityEngine();

    public static void code() {

        VE.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        VE.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        VE.init();

        log.info("开始生成java文件");
        CTX.put("author", AUTHOR);
        CTX.put("tableName", TABLE_NAME);
        CTX.put("modeName", MODEL_NAME);
        CTX.put("handName", HANDLE_NAME);
        CTX.put("serviceName", SERVICE_NAME);
        CTX.put("serviceImplName", SERVICE_IMPL_NAME);

        CTX.put("modelPackage", PACKAGE_MODEL_PATH);
        CTX.put("handlePackage", PACKAGE_HANDLE_PATH);
        CTX.put("servicePackage", PACKAGE_SERVICE_PATH);
        CTX.put("serviceImplPackage", PACKAGE_SERVICE_IMPL_PATH);

        model();
        handle();
        service();
        serviceImpl();
    }

    /**
     * model
     */
    private static void model() {
        // 获取模板文件
        Template t = VE.getTemplate("/java/model.vm");
//        List<Object> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        ctx.put("list", list);
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile(PACKAGE_MODEL_PATH, MODEL_NAME, sw);
    }

    /**
     * handle
     */
    private static void handle() {
        // 获取模板文件
        Template t = VE.getTemplate("/java/handle.vm");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile(PACKAGE_HANDLE_PATH, HANDLE_NAME, sw);
    }

    private static void service() {
        // 获取模板文件
        Template t = VE.getTemplate("/java/service.vm");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile(PACKAGE_SERVICE_PATH, SERVICE_NAME, sw);
    }

    private static void serviceImpl() {
        // 获取模板文件
        Template t = VE.getTemplate("/java/serviceImpl.vm");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile(PACKAGE_SERVICE_IMPL_PATH, SERVICE_IMPL_NAME, sw);
    }

    /**
     * 生成文件
     *
     * @param p  String
     * @param sw StringWriter
     */
    private static void createFile(String p, String fileName, StringWriter sw) {
        p = p.replaceAll("\\.", "/");
//        业务包根目录
        String path = StringUtils.hasText(PATH) ? PATH : PathUtil.getPathJava();
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
