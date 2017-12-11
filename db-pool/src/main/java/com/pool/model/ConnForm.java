package com.pool.model;

/**
 * Created by Xuzz on 2017/11/14.
 * 数据连接表单
 */
public class ConnForm {
    //用户名
    private String userName;
    //密码
    private String passwd;
    //驱动
    private String driverName;
    //连接路径
    private String dbUrl;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }
}
