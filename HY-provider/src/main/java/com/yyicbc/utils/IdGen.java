package com.yyicbc.utils;

import java.util.Random;
import java.util.UUID;

/**
 *  uuid的主键生成器
 *  推荐使用自增长主键
 */

public abstract class IdGen {
    //主键生成
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String randomString(int length){
        final Random r=new Random();

        char[] ch=new char[length];
        for(int i=0;i<ch.length;i++){
            int n=r.nextInt(62);
            if(n<26)
                ch[i]=(char) ('a'+n);
            else if(n<52)
                ch[i]=(char) ('A'+n-26);
            else
                ch[i]=(char)('0'+n-52);
        }
        return new String(ch);
    }

    public static String randomNumString(int length){
        final Random r=new Random();

        char[] ch=new char[length];
        for(int i=0;i<ch.length;i++){
            int n=r.nextInt(10);
            ch[i]=(char)('0'+n);
        }
        return new String(ch);
    }
}
