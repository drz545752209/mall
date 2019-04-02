package com.hlju.mall.dao;

import com.hlju.mall.domain.User;
import com.hlju.mall.domain.UserExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserDAO继承基类
 */
@Mapper
public interface UserDAO extends MyBatisBaseDao<User, Integer, UserExample> {
}