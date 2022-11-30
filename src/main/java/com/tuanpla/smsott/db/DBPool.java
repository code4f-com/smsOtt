/*
 *  Copyright 2022 by Tuanpla
 *  https://tuanpla.com
 */
package com.tuanpla.smsott.db;

import com.tuanpla.utils.logging.LogUtils;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author tuanp
 */
@Component
public class DBPool {

    static final Logger logger = LoggerFactory.getLogger(DBPool.class);

    private static HikariDataSource dataSource;

    @Autowired
    public DBPool(HikariDataSource dataSource) {
        DBPool.dataSource = dataSource;
        LogUtils.out("DBPool.Autowired  HikariDataSourceStarted ok... ===>");
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            LogUtils.debug("---------------->>> get connection error !  <<<------------- ");
            logger.error(LogUtils.getLogMessage(e));
        }
        return conn;
    }

    public static void releaseDB() {
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        if (drivers != null) {
            while (drivers.hasMoreElements()) {
                final Driver driver = drivers.nextElement();
                try {
                    DriverManager.deregisterDriver(driver);
                    LogUtils.debug("Deregis jdbc Driver: " + driver.toString());
                } catch (SQLException e) {
                    logger.error(String.format("Error deregistering driver %s", driver), e);
                }
            }
        }
        LogUtils.debug("releaseDB Finish............");
    }

    public static void freeConn(ResultSet rs, PreparedStatement pstm, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(LogUtils.getLogMessage(e));
        }
    }

    public static void freeConn(PreparedStatement pstm, Connection conn) {
        try {
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(LogUtils.getLogMessage(e));
        }
    }

    public static void releadPstm(PreparedStatement pstm) {
        try {
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            logger.error(LogUtils.getLogMessage(e));
        }
    }

    public static void releadRs(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.error(LogUtils.getLogMessage(e));
        }
    }

    public static void releadRsPstm(ResultSet rs, PreparedStatement pstm) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            logger.error(LogUtils.getLogMessage(e));
        }
    }

    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            logger.error(LogUtils.getLogMessage(e));
        }
    }
}
