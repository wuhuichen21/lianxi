package com.xiaowu.crowd.util;


import com.xiaowu.crowd.constant.CrowdConstant;


import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;

public class CrowdUtil {
    public static boolean judgeRequestType(HttpServletRequest request){
//        1，获取请求头
        String accept = request.getHeader("Accept");
        String header = request.getHeader("X-Requested-With");
       return  (accept!=null && accept.contains("application/json"))
            || (header != null && header.equals("XMLHttpRequest"));

    }


    public static String md5(String source){
        if (source == null || source.length() == 0 ){
            // 2.如果不是有效的字符串抛出异常
             throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
//          获取MessageDigest;
            String algorithm = "MD5";

            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
//          获取明文字符串对应的字节数组
            byte[] input = source.getBytes();

//          执行加密
            byte[] output = messageDigest.digest(input);

//          创建BigInteger对象
            int signum =  1;
            BigInteger bigInteger = new BigInteger(signum, output);

//          按照16进制将BigInteger转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
