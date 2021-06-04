package mofa.wangzhe.constant;

/**
 * @author LD
 */
public class JavaConstantUtil {

    public static String AUTHOR = "LD";
    public static String TABLE_NAME = "test_table";
    public static String MODULAR_PREFIX = "Test";

    public static String PATH = "";

    public static String PACKAGE_PARENT_PATH = "mofa.wangzhe.reactive.";
    public static String PACKAGE_MODEL = "model";
    public static String PACKAGE_MODEL_PATH = PACKAGE_PARENT_PATH + PACKAGE_MODEL;
    public static String PACKAGE_HANDLE = "handle";
    public static String PACKAGE_HANDLE_PATH = PACKAGE_PARENT_PATH + PACKAGE_HANDLE;
    public static String PACKAGE_SERVICE = "service";
    public static String PACKAGE_SERVICE_PATH = PACKAGE_PARENT_PATH + PACKAGE_SERVICE;
    public static String PACKAGE_SERVICE_IMPL = "impl";
    public static String PACKAGE_SERVICE_IMPL_PATH = PACKAGE_SERVICE_PATH + "." + PACKAGE_SERVICE_IMPL;

    public static String MODEL_FILE_SUFFIX = "Model";
    public static String HANDLE_FILE_SUFFIX = "Handle";
    public static String SERVICE_FILE_SUFFIX = "Service";
    public static String SERVICE_IMPL_FILE_SUFFIX = "ServiceImpl";

    public static final String MODEL_NAME = MODULAR_PREFIX + MODEL_FILE_SUFFIX;
    public static final String HANDLE_NAME = MODULAR_PREFIX + HANDLE_FILE_SUFFIX;
    public static final String SERVICE_NAME = MODULAR_PREFIX + SERVICE_FILE_SUFFIX;
    public static final String SERVICE_IMPL_NAME = MODULAR_PREFIX + SERVICE_IMPL_FILE_SUFFIX;

    //    database
    public static String DRIVER = "mysql";
    public static String HOST = "127.0.0.1";
    public static String USER = "root";
    public static String PASSWORD = "root";
    public static int PORT = 3306;
    public static String DATABASE = "management";

}
