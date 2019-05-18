package com.deng.common.utils;


import java.util.HashSet;
import java.util.List;

public class ListUtils<E>{
         public  HashSet<E> list2HashMap(List<E> list){
              HashSet<E> sets=new HashSet<>();
              for (E val:list){
                  sets.add(val);
              }
              return sets;
          }

}
