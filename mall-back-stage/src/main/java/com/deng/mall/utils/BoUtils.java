package com.deng.mall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;

public class BoUtils<T> {
    final static Logger logger= LoggerFactory.getLogger(BoUtils.class);

    public Object merge2Bo(List<String> beanNameList,String boName){
        try {
            //获取bo对象字段
            HashSet<String> boFieldName=new HashSet<String>();
            Class clazz= Class.forName(boName);
            Field[] fields=clazz.getDeclaredFields();
            for (Field field:fields){
                boFieldName.add(field.getName());
            }
        } catch (ClassNotFoundException e) {
            logger.error("boName无法再系统环境中找到");
        }
        return null;
    }

    public static void main(String[] args) {
    }

}
