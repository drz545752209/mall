package com.deng.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class StrUntils {
    public static List<Integer> getIds(String idStr){
        String[] idStrs=idStr.split(",");
        List<Integer> ids=new ArrayList<>();
        for (String id:idStrs){
            ids.add(Integer.valueOf(id));
        }
        return ids;
    }

    /**
     * @param strDate
     * */
    public static Date str2Date(String strDate) {
        if (StringUtils.isEmpty(strDate)){
            return null;
        }
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        try {
        d = format.parse(str);
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(d);
        calendar.add(calendar.DATE, 1);
        d = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new Date(d.getTime());
        return date;
    }

    /**
     * 获取当前系统时间
     * */
    public static Date getNow() {
       long curTime=new java.util.Date().getTime();
       Date date=new Date(curTime);
       return date;
    }


    /**
     * 将json转换成list<map>
     * @param jsonStr
     * @return
     */
    public static List<HashMap> json2MapList(String jsonStr){
        if (StringUtils.isEmpty(jsonStr)){
            return null;
        }
        String var1="["+jsonStr.trim()+"]";

        List<HashMap> result= JSON.parseArray(var1,HashMap.class);

        return  result;
    }

    public static Map json2Map(String jsonStr){
        if (StringUtils.isEmpty(jsonStr)){
            return null;
        }

        Map result = (Map)JSON.parse(jsonStr);

        return result;
    }

}
