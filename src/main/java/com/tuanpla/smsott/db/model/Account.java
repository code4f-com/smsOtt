/*
 *  Copyright 2022 by Tuanpla
 *  https://tuanpla.com
 */
package com.tuanpla.smsott.db.model;

import com.github.jgonian.ipmath.Ipv4;
import com.github.jgonian.ipmath.Ipv4Range;
import com.github.jgonian.ipmath.Ipv6;
import com.github.jgonian.ipmath.Ipv6Range;
import com.tuanpla.utils.common.Nullable;
import com.tuanpla.utils.logging.LogUtils;
import java.util.List;
import org.apache.commons.net.util.SubnetUtils;

/**
 *
 * @author tuanp
 */
public class Account {

    private Integer id;
    private String user;
    private String pass;
    private List<String> ipAllow;

    public Account() {
    }

    public Account(@Nullable Integer id, @Nullable String user, @Nullable String pass, @Nullable List<String> ipAllow) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.ipAllow = ipAllow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<String> getIpAllow() {
        return ipAllow;
    }

    public void setIpAllow(List<String> ipAllow) {
        this.ipAllow = ipAllow;
    }

    public boolean validIP(String ipRequest) {
        boolean flag = false;
        try {
            if (ipAllow != null) {
                for (String one : ipAllow) {
//                    LogUtils.out("IP Request:[" + ipRequest + "]==>" + "IP Allow: [" + one + "]");
                    if (one.equals("\\*") || ipRequest.equals(one.trim())) {
                        flag = true;
                        break;
                    }
                }
            } else {
                LogUtils.error("IP Allow is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag || true;
    }

    public static boolean checkIPv4IsInRange(String inputIP, String rangeStartIP, String rangeEndIP) {
        Ipv4 startIPAddress = Ipv4.of(rangeStartIP);
        Ipv4 endIPAddress = Ipv4.of(rangeEndIP);
        Ipv4Range ipRange = Ipv4Range.from(startIPAddress).to(endIPAddress);
        Ipv4 inputIPAddress = Ipv4.of(inputIP);
        return ipRange.contains(inputIPAddress);
    }

    public static boolean checkIPv6IsInRange(String inputIP, String rangeStartIP, String rangeEndIP) {
        Ipv6 startIPAddress = Ipv6.of(rangeStartIP);
        Ipv6 endIPAddress = Ipv6.of(rangeEndIP);
        Ipv6Range ipRange = Ipv6Range.from(startIPAddress).to(endIPAddress);
        Ipv6 inputIPAddress = Ipv6.of(inputIP);
        return ipRange.contains(inputIPAddress);
    }

    public static void main(String[] args) {
        SubnetUtils utils = new SubnetUtils("103.85.106.32/26");
        String[] allIps = utils.getInfo().getAllAddresses();
        for (String one : allIps) {
            LogUtils.debug(one);
        }
    }

}
