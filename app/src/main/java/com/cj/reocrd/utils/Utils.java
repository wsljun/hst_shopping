package com.cj.reocrd.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 验证手机号码1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
