package com.pool.tools;

import com.pool.model.ConnForm;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xuzz on 2017/11/14.
 * 创建连接池
 */
public class ConnectionGet {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    //数据连接集合
    private final Map<String,List<Connection>> connections = new HashMap<String,List<Connection>>();
    //最小连接数
    private final Integer CONNSIZEMIN = 10;
    //最大连接数
    private final Integer CONNSIZEMAX = 20;



    /**
     * 创建连接池
     */
    public void createConnPool(ConnForm connForm) throws InterruptedException, SQLException, ClassNotFoundException {

        String key = connForm.getDbUrl();
        //创建连接池
        List<Connection> connList = connections.get(key);
        Integer connSize = connList.size();
        Integer count;
        if (connSize==0){
            count = CONNSIZEMIN;
        }else if (connSize>CONNSIZEMIN){
            count = CONNSIZEMAX - connSize;
        }else {
            count = CONNSIZEMIN;
        }
        List<Connection> connectionList = new ArrayList<Connection>();
        Connection connection = null;
        //创建连接
        for (int i = 0; i<count; i++){
            try {
                connection = getConn(connForm);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("创建连接失败...");
                Thread.sleep(2000l);
                getConn(connForm);
            }
            connectionList.add(connection);
        }

    }

    /**
     * 获取连接
     * @param connForm
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConn(ConnForm connForm) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName(connForm.getDriverName());
        connection = DriverManager.getConnection(connForm.getDbUrl(),connForm.getUserName(),connForm.getPasswd());
        return connection;
    }

    /**
     * 获取连接池
     * @return
     */
    public Map<String,List<Connection>> getConnectionMap(){
        return connections;
    }

    /**
     * 清空连接池并重新初始化
     */
    public void clearAndInit(ConnForm connForm) throws InterruptedException, SQLException, ClassNotFoundException {
        for (String key:connections.keySet()){
            connections.put(key,new ArrayList<Connection>());
        }
        //初始化
        createConnPool(connForm);
    }
}
