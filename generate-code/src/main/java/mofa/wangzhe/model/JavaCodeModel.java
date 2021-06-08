package mofa.wangzhe.model;

import java.io.Serializable;

/**
 * @author LD
 */
public class JavaCodeModel implements Serializable {

    private String author = "LD";
    private String modularPrefix = "Test";
    private String path = "";
    /**
     * 表名中需要剔除的内容
     */
    private String tableNo = "";
    /**
     * 是否替换已经存在的文件
     */
    private boolean isReplace = false;

    private String packageParentPath = "mofa.wangzhe.reactive.";
    private String packageRouter = "router";
    private String packageRouterPath = packageParentPath + packageRouter;
    private String packageModel = "model";
    private String packageModelPath = packageParentPath + packageModel;
    private String packageHandle = "handle";
    private String packageHandlePath = packageParentPath + packageHandle;
    private String packageService = "service";
    private String packageServicePath = packageParentPath + packageService;
    private String packageServiceImpl = "impl";
    private String packageServiceImplPath = packageServicePath + "." + packageServiceImpl;
    private String routerFileSuffix = "Router";
    private String modelFileSuffix = "Model";
    private String handleFileSuffix = "Handle";
    private String serviceFileSuffix = "Service";
    private String serviceImplFileSuffix = "ServiceImpl";

    public String getRouterFileSuffix() {
        return routerFileSuffix;
    }

    public void setRouterFileSuffix(String routerFileSuffix) {
        this.routerFileSuffix = routerFileSuffix;
    }

    public String getPackageRouter() {
        return packageRouter;
    }

    public void setPackageRouter(String packageRouter) {
        this.packageRouter = packageRouter;
    }

    public String getPackageRouterPath() {
        return packageRouterPath;
    }

    public void setPackageRouterPath(String packageRouterPath) {
        this.packageRouterPath = packageRouterPath;
    }

    public boolean isReplace() {
        return isReplace;
    }

    public void setReplace(boolean replace) {
        isReplace = replace;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModularPrefix() {
        return modularPrefix;
    }

    public void setModularPrefix(String modularPrefix) {
        this.modularPrefix = modularPrefix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPackageParentPath() {
        return packageParentPath;
    }

    public void setPackageParentPath(String packageParentPath) {
        this.packageParentPath = packageParentPath;
    }

    public String getPackageModel() {
        return packageModel;
    }

    public void setPackageModel(String packageModel) {
        this.packageModel = packageModel;
    }

    public String getPackageModelPath() {
        return packageModelPath;
    }

    public void setPackageModelPath(String packageModelPath) {
        this.packageModelPath = packageModelPath;
    }

    public String getPackageHandle() {
        return packageHandle;
    }

    public void setPackageHandle(String packageHandle) {
        this.packageHandle = packageHandle;
    }

    public String getPackageHandlePath() {
        return packageHandlePath;
    }

    public void setPackageHandlePath(String packageHandlePath) {
        this.packageHandlePath = packageHandlePath;
    }

    public String getPackageService() {
        return packageService;
    }

    public void setPackageService(String packageService) {
        this.packageService = packageService;
    }

    public String getPackageServicePath() {
        return packageServicePath;
    }

    public void setPackageServicePath(String packageServicePath) {
        this.packageServicePath = packageServicePath;
    }

    public String getPackageServiceImpl() {
        return packageServiceImpl;
    }

    public void setPackageServiceImpl(String packageServiceImpl) {
        this.packageServiceImpl = packageServiceImpl;
    }

    public String getPackageServiceImplPath() {
        return packageServiceImplPath;
    }

    public void setPackageServiceImplPath(String packageServiceImplPath) {
        this.packageServiceImplPath = packageServiceImplPath;
    }

    public String getModelFileSuffix() {
        return modelFileSuffix;
    }

    public void setModelFileSuffix(String modelFileSuffix) {
        this.modelFileSuffix = modelFileSuffix;
    }

    public String getHandleFileSuffix() {
        return handleFileSuffix;
    }

    public void setHandleFileSuffix(String handleFileSuffix) {
        this.handleFileSuffix = handleFileSuffix;
    }

    public String getServiceFileSuffix() {
        return serviceFileSuffix;
    }

    public void setServiceFileSuffix(String serviceFileSuffix) {
        this.serviceFileSuffix = serviceFileSuffix;
    }

    public String getServiceImplFileSuffix() {
        return serviceImplFileSuffix;
    }

    public void setServiceImplFileSuffix(String serviceImplFileSuffix) {
        this.serviceImplFileSuffix = serviceImplFileSuffix;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }
}
