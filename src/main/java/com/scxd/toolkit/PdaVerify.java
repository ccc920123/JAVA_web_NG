package com.scxd.toolkit;

import com.scxd.beans.PdaBeans;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * PDA验证
 */
public class PdaVerify {

    private static final String PRI_KEY = "scxdics";

    public static boolean verify(PdaBeans beans)throws Exception{

        if (UtilClass.strIsEmpty(beans.getSjc()))return false;//判断PDA传入时间戳是否为空
        if (UtilClass.strIsEmpty(beans.getWym()))return false;//判断PDA传入唯一码是否为空
        if (UtilClass.strIsEmpty(beans.getJkxlh()))return false;//判断接口序列号是否为空

        //组成未加密的安全密钥 = 唯一码 + scxdics + 时间戳
        String key = beans.getWym() + PRI_KEY + beans.getSjc();

        //进行MD5加密
        String keyMd5 = new BASE64Encoder().encode(MessageDigest.getInstance("MD5").digest(key.getBytes("utf-8")));

       return keyMd5.equals(beans.getJkxlh());
    }
}
