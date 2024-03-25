package com.indinner.wallserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author indinner
 * @Date 2023/6/1 16:34
 * @Version 1.0
 * @Doc:
 */
public class MyUtil {

    public static String getDate(){
        // 创建SimpleDateFormat对象，指定格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 获取当前时间
        Date date = new Date();

        // 格式化时间
        String formattedDate = formatter.format(date);

        return formattedDate;
    }

}
