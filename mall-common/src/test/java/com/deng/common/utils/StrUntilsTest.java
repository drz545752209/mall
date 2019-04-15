package com.deng.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;

public class StrUntilsTest {

    public static List<HashMap> json2Map(String jsonStr){
        String var1="["+jsonStr+"]";

        List<HashMap> result=JSON.parseArray(var1,HashMap.class);

        return  result;
    }


    public static void main(String[] args) {
        String json1= "{\"product\":1,\"userId\":1},{\"product\":2,\"userId\":2}";
        List<HashMap> maps=json2Map(json1);

    }


}