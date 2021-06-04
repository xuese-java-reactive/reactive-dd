package mofa.wangzhe;

import mofa.wangzhe.constant.ConstantUtil;
import mofa.wangzhe.util.PathUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.*;
import java.util.Objects;

/**
 * @author LD
 */
public class Code {

    private static final VelocityContext CTX = new VelocityContext();
    private static final VelocityEngine VE = new VelocityEngine();

    static {
        // 设置变量
        CTX.put(ConstantUtil.AUTHOR_KEY, ConstantUtil.AUTHOR);
        CTX.put(ConstantUtil.TABLE_NAME_KEY, ConstantUtil.TABLE_NAME);
        CTX.put(ConstantUtil.MODULAR_PREFIX_KEY, ConstantUtil.MODULAR_PREFIX);
    }

    public static void main(String[] args) {
        code();
    }

    public static void code() {

        VE.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        VE.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        VE.init();

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
        Template t = VE.getTemplate("model.vm");

//        List<Object> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        ctx.put("list", list);

        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);

        createFile("model", "Model", sw);
    }

    /**
     * handle
     */
    private static void handle() {

        // 获取模板文件
        Template t = VE.getTemplate("handle.vm");

        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);

        createFile("handle", "Handle", sw);
    }

    private static void service() {

        // 获取模板文件
        Template t = VE.getTemplate("service.vm");

        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);

        createFile("service", "Service", sw);
    }

    private static void serviceImpl() {

        // 获取模板文件
        Template t = VE.getTemplate("serviceImpl.vm");

        // 输出
        StringWriter sw = new StringWriter();
        t.merge(CTX, sw);

        createFile("service/impl", "ServiceImpl", sw);
    }

    /**
     * 生成文件
     *
     * @param p  String
     * @param sw StringWriter
     */
    private static void createFile(String p, String fileSuffix, StringWriter sw) {
//        业务包根目录
        String path = PathUtil.getPath();
        path = path + p + "/";
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdir = file.mkdirs();
            System.out.println("文件创建" + (mkdir ? "成功" : "失败"));
        }

        path = path + ConstantUtil.MODULAR_PREFIX + fileSuffix + ".java";
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
                    System.out.println("创建文件失败");
                    System.exit(-1);
                }
            } else {
                System.out.println("删除文件失败");
                System.exit(-1);
            }
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(sw.toString());
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
