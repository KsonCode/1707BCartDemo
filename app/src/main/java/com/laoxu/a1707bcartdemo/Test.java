package com.laoxu.a1707bcartdemo;

import com.blankj.utilcode.util.EncryptUtils;

public class Test {

    public static void main(String[] args) {
        String pwd = "111111";
        System.out.println("加密前的："+pwd);
        System.out.println("加密后的："+EncryptUtils.encryptMD5ToString(pwd));
    }
}
