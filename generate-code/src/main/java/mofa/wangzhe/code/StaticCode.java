package mofa.wangzhe.code;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.database.DataConfig;
import mofa.wangzhe.model.ColumnModel;
import mofa.wangzhe.model.StaticCodeModel;
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
import java.util.UUID;

/**
 * @author LD
 */

@Slf4j
public class StaticCode {

    private final StaticCodeModel staticCodeModel;
    private final DataConfig dataConfig;

    public StaticCode(StaticCodeModel staticCodeModel, DataConfig dataConfig) {
        this.staticCodeModel = staticCodeModel;
        this.dataConfig = dataConfig;
    }

    private static final VelocityContext CTX = new VelocityContext();
    private static final VelocityEngine VE = new VelocityEngine();

    public void code(List<ColumnModel> columns) {

        VE.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        VE.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        VE.init();

        log.info("开始生成静态文件");
        CTX.put("author", staticCodeModel.getAuthor());
        CTX.put("fileName", staticCodeModel.getFileName());
        CTX.put("modularName", staticCodeModel.getModularName());
        CTX.put("modularNameText", staticCodeModel.getModularNameText());

        CTX.put("columns", columns);


        html();
        js();
    }

    /**
     * html
     */
    private void html() {
        // 获取模板文件
        Template t = VE.getTemplate("/static/html.vm");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile2(staticCodeModel.getModularName(), staticCodeModel.getFileName(), sw);
    }

    /**
     * js
     */
    private void js() {
        // 获取模板文件
        Template t = VE.getTemplate("/static/js.vm");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);
        createFile("static/js/" + staticCodeModel.getModularName(), staticCodeModel.getFileName() + ".js", sw);
    }

    private void createFile2(String p2, String fileName, StringWriter sw) {
        boolean b = createFile("templates/" + p2, fileName + ".html", sw);
        if (b) {
            String uuid = UUID.randomUUID().toString();
            dataConfig.insertPage(uuid, "0", staticCodeModel.getModularNameText(), p2 + "/" + fileName, staticCodeModel.getModularJur() + "-page", 0);
            dataConfig.insertPage(UUID.randomUUID().toString(), uuid, "新增", "", staticCodeModel.getModularJur() + "-save", 1);
            dataConfig.insertPage(UUID.randomUUID().toString(), uuid, "修改", "", staticCodeModel.getModularJur() + "-update", 1);
            dataConfig.insertPage(UUID.randomUUID().toString(), uuid, "删除", "", staticCodeModel.getModularJur() + "-remove", 1);
        }
    }

    /**
     * 生成文件
     *
     * @param p  String
     * @param sw StringWriter
     */
    private boolean createFile(String p, String fileName, StringWriter sw) {
        p = p.replaceAll("\\.", "/");
//        业务包根目录
        String path = StringUtils.hasText(staticCodeModel.getPath()) ? staticCodeModel.getPath() : PathUtil.getPathStatic();
        path = path + p + "/";
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdir = file.mkdirs();
            log.info("文件创建" + (mkdir ? "成功" : "失败"));
        }

        path = path + fileName;
        File file2 = new File(path);
        if (file2.exists()) {
            if (staticCodeModel.isReplace()) {
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
            } else {
                log.info("文件已存在，不在生成");
                return false;
            }
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file2, false));
            bw.write(sw.toString());
            log.info("已生成:" + path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
