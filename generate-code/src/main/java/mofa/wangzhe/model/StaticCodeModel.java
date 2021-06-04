package mofa.wangzhe.model;

import java.io.Serializable;

/**
 * @author LD
 */
public class StaticCodeModel implements Serializable {

    private String author = "LD";
    private String modularName = "test";
    private String modularNameText = "测试管理页面";
    private String path = "";
    private String fileName = modularName;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModularName() {
        return modularName;
    }

    public void setModularName(String modularName) {
        this.modularName = modularName;
    }

    public String getModularNameText() {
        return modularNameText;
    }

    public void setModularNameText(String modularNameText) {
        this.modularNameText = modularNameText;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
