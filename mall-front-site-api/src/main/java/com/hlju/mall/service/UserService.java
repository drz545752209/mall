package com.hlju.mall.service;

import com.hlju.mall.domain.User;

import java.util.List;

public interface UserService {

    int insertSelective(User record);

    boolean hasPassword(User user);
    
    boolean hasUserName(User user);

    List<User> selectByExample(User user);
    
    List<User> selectUserNameByExamle(User record);

    List<User> selectUserListByIds(List<Integer> ids);

    boolean saveUserMsg(User user,String userName);
}
