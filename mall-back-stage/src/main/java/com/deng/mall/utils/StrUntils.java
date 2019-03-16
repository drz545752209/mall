package com.deng.mall.utils;

import java.util.ArrayList;
import java.util.Arrays;
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

}
