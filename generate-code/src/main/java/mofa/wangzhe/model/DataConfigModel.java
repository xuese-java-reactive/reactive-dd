package mofa.wangzhe.model;

import java.io.Serializable;

/**
 * @author LD
 */

public class DataConfigModel implements Serializable {

    public  String host = "127.0.0.1";
    public  String user = "root";
    public  String password = "root";
    public  int port = 3306;
    public  String database = "management";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
