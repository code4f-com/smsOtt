/*
 *  Copyright 2022 by Tuanpla
 *  https://tuanpla.com
 */
package com.tuanpla.smsott.dao.impl;

import com.tuanpla.smsott.dao.SmsMessageDaoIF;
import com.tuanpla.smsott.db.DBPool;
import com.tuanpla.smsott.db.model.SmsMessage;
import com.tuanpla.smsott.db.model.ext.ResponseResult;
import com.tuanpla.smsott.myenum.RESULT;
import com.tuanpla.utils.logging.LogUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuanp
 */
@Repository
public class SmsMessageDaoImpl implements SmsMessageDaoIF {

    static final Logger logger = LoggerFactory.getLogger(SmsMessageDaoImpl.class);

    @Override
    public SmsMessage findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResponseResult create(SmsMessage one, Object... opParam) {
        ResponseResult result = new ResponseResult();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "INSERT INTO sms_message(source,destination,message,time_send,status,trans_id) VALUES(?,?,?,?,?,?)";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, one.getSource());
            pstm.setString(i++, one.getDestination());
            pstm.setString(i++, one.getMessage());
            pstm.setString(i++, one.getTimeSend());
            pstm.setBoolean(i++, one.getStatus());
            pstm.setString(i++, one.getTransId());
            if (pstm.executeUpdate() == 1) {
                result.done(RESULT.SUCCESS.msg);
            }
        } catch (SQLException ex) {
            result.fail(RESULT.FAIL);
            logger.error(LogUtils.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    @Override
    public ResponseResult update(SmsMessage one, Object... opParam) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResponseResult delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
