package com.deng.mall.service;

import com.deng.mall.domain.Biz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BizService {

    int insertSelective(Biz record);

    boolean hasBiz(Biz record);

    List<Biz> selectByExample(Biz record);

    boolean hasBizName(Biz record);

    List<Biz> selectBizNameByExamle(Biz record);

    boolean hasCookie(HttpServletRequest request, HttpServletResponse resp, boolean isValidate, Biz biz);

    boolean updateBizScore(Integer storeId,Integer score);

}
