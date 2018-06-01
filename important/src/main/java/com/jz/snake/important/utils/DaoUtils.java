package com.jz.snake.important.utils;

import org.apache.log4j.Logger;
import org.nutz.ioc.Ioc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接服务类
 * Created by jzshi on 2018/5/29.
 */
public class DaoUtils {

    private static Logger logger = Logger.getLogger(DaoUtils.class);
    private Ioc ioc;
    private static DaoUtils daoUtils = new DaoUtils();
    private DaoUtils(){}
    public final static DaoUtils getDaoUtils(){return daoUtils;}

    public void setIoc(Ioc ioc){this.ioc = ioc;}

    public DataSource getDataSource(){return ioc.get(DataSource.class,"dataSource");}

    /**
     * 根据分表索引获取数据库连接
     * @return
     */
    public Connection getConnection() {
        DataSource ds = getDataSource();
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
        return conn;
    }

    public void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * 关闭 Statement
     * @param stmt
     */
    public void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {}
        }
    }

    /**
     * 关闭 ResultSet
     * @param rs
     */
    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {}
        }
    }

    /**
     * 依次关闭 ResultSet,Statement,没有 ResultSet,可以设为 null
     * @param rs
     * @param stmt
     */
    public void close(ResultSet rs,Statement stmt) {
        close(rs);
        close(stmt);
    }

    /**
     * 依次关闭 ResultSet,Statement,Connection
     * @param rs ResultSet,可以设为 Null
     * @param stmt Statement,可以设为 Null
     * @param conn
     */
    public void close(ResultSet rs,Statement stmt,Connection conn) {
        close(rs);
        close(stmt);
        close(conn);
    }
}

