package com.deng.mall.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new java.sql.Date(d.getTime());
        return date;
    }


}
