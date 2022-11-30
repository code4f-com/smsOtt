/*
 *  Copyright 2022 by Tuanpla
 *  https://tuanpla.com
 */
package com.tuanpla.smsott.rest;

import com.tuanpla.smsott.common.PathURI;
import com.tuanpla.smsott.dao.SmsMessageDaoIF;
import com.tuanpla.smsott.db.model.Account;
import com.tuanpla.smsott.db.model.SmsMessage;
import com.tuanpla.smsott.db.model.ext.ResponseResult;
import com.tuanpla.smsott.myenum.RESULT;
import com.tuanpla.utils.date.DateProc;
import com.tuanpla.utils.encrypt.Md5;
import com.tuanpla.utils.http.HttpUtil;
import com.tuanpla.utils.logging.LogUtils;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tuanp
 */
@RestController
@RequestMapping({PathURI.MYTEL})
public class SmsMyTelRest {
//--

    private static final String USER = "mytel";
    private static final String PASS = "Kme!$jokO3979";
//    private static final String[] ips = {"*", "127.0.0.1"};
    private static final String RANG_1 = "65.18.116.160/27";
    private static final String RANG_2 = "103.85.106.32/27";
    private static final List<String> IP_ALLOW = new ArrayList<>();

    static {
//        {"*", "210.245.75.44", "65.18.116.160", "103.85.106.32","103.85.106.83"}
        IP_ALLOW.add("103.85.106.83");
        IP_ALLOW.addAll(Arrays.asList(toArray(RANG_1)));
        IP_ALLOW.addAll(Arrays.asList(toArray(RANG_2)));
    }
    private static final Account account = new Account(null, USER, PASS, IP_ALLOW);

    public static void main(String[] args) {
        LogUtils.debug(account.validIP("103.85.106.89"));
    }
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession session;
    @Autowired
    protected SmsMessageDaoIF smsMessageDao;

    @PostMapping("/smsott/receiver")
    public ResponseResult receiver() {
        ResponseResult result = new ResponseResult();
        try {
            String ip = HttpUtil.getClientIpAddr(request);
//            String host = request.getHeader("host") + "-" + request.getRemoteHost();
//            HttpUtil.debugParam(request);
            if (!account.validIP(ip)) {
                LogUtils.debug("IP not valid:" + ip);
                return result.fail(RESULT.IP_NOT_ALLOW);
            }
            var _user = HttpUtil.getString(request, "user");
            var _source = HttpUtil.getString(request, "source");
            var _destination = HttpUtil.getString(request, "destination");
            var _message = HttpUtil.getString(request, "message");
            var _timeSend = HttpUtil.getString(request, "timeSend");
            var _transId = HttpUtil.getString(request, "transId");
            var _scret = HttpUtil.getString(request, "secret");
            HttpUtil.debugParam(request);
            if (!account.getUser().equals(_user)) {
                return result.fail(RESULT.USER_NOT_FOUND);
            }
            String ownerScret = Md5.encryptMD5(account.getPass() + _transId);
            if (!ownerScret.equals(_scret)) {
                return result.fail(RESULT.SECRET_NOT_VALID);
            }
            Date sendTime = DateProc.string2Date(_timeSend, "yyyy-MM-dd HH:mm:ss");
            if (sendTime == null) {
                return result.fail(RESULT.TIMESEND_NOT_VALID);
            }
            SmsMessage message = new SmsMessage();
            message.setSource(_source);
            message.setDestination(_destination);
            message.setMessage(_message);
            message.setTimeSend(_timeSend);
            message.setTransId(_transId);
            result = smsMessageDao.create(message);
        } catch (Exception e) {
            result.fail(RESULT.UNKNOW_ERROR);
            e.printStackTrace();
        }
        return result;
    }

    public static String[] toArray(String ipRang) {
        SubnetUtils utils = new SubnetUtils(ipRang);
        String[] allIps = utils.getInfo().getAllAddresses();
        return allIps;
    }
}
